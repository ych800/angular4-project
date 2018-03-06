package cn.mxlog.sscloud.service;

import cn.mxlog.sscloud.base.BaseService;
import cn.mxlog.sscloud.model.*;
import cn.mxlog.sscloud.util.Shadowsocket;
import cn.mxlog.sscloud.util.Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by F.Du on 2017/10/20.
 * <p>
 * 定时任务
 */
@Component
@Configurable
@EnableScheduling
public class TaskService extends BaseService {

    @Autowired
    private ServerService serverService;

    @Autowired
    private ServerLogService serverLogService;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTrafficService userTrafficService;

    /**
     * 定时任务, 服务器状态及流量
     *
     * @desc 每5分钟获取一次服务器流量
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void serverStatusTask() {
        logger.info("任务开启-统计服务器/用户流量");
        Iterable<Server> list = this.serverService.list();

        list.forEach(server -> {
            List<Map<String, Object>> status = Shadowsocket.ping(server);

            Iterable<User> users = this.userService.findByServer(server.getIp());

            Long[] traffic = {0L};

            Iterator<User> iUsers = users.iterator();
            while (iUsers.hasNext()) {
                User user = iUsers.next();
                // 用户没有流量包, 关闭端口
                if (user.getUserTraffic() == null) {
                    Shadowsocket.remove(server, user);
                    continue;
                }

                // 根据端口, 查找流量状态信息
                Map<String, Object> state = null;
                if (status != null && status.size() > 0) {
                    for (Map<String, Object> s : status) {
                        if (s != null && s.get("port").equals(user.getPort())) {
                            state = s;
                            break;
                        }
                    }
                }
                if (state == null) {
                    if (user.getUserTraffic() != null && user != null
                            && user.getUserTraffic().getTrafficUsed() < user.getUserTraffic().getTraffic()
                            && user.getUserTraffic().getEndDate().after(Util.getCurrentDateTime())
                            && !StringUtils.isEmpty(user.getPort()) && !StringUtils.isEmpty(user.getPortPwd())) {
                        Shadowsocket.add(server, user);
                    }

                    continue;
                }
                UserLog userLog = new UserLog();
                userLog.setUserId(user.getId());
                userLog.setPort(state.get("port").toString());
                userLog.setTraffic(Long.parseLong(state.get("traffic").toString()));
                userLog.setServer(server.getIp());
                userLog.setCreatedate(Util.getCurrentDateTime());

                // 如果端口流量为空,则跳过记录日志
                if (userLog.getTraffic() <= 0) {
                    continue;
                }

                // 设置用户流量
                UserTraffic ut = null;
                if (user != null) {
                    ut = user.getUserTraffic();
                    if (ut.getTrafficUsed() == null) {
                        ut.setTrafficUsed(userLog.getTraffic());
                    } else {
                        ut.setTrafficUsed(ut.getTrafficUsed() + userLog.getTraffic());
                    }

                    user.setUserTraffic(ut);
                }

                // 统计服务器流量
                traffic[0] = traffic[0] + userLog.getTraffic();

                // 判断流量状态
                if (user != null && ut != null && ut.getTrafficUsed() < ut.getTraffic()) {
                    // 重新开放端口, 流量统计清零
                    Shadowsocket.add(server, user);
                } else {
                    // 流量已用完, 关闭端口
                    user.setUserTraffic(null);
                    ut.setStatus(20);
                    Shadowsocket.remove(server, user);
                }

                // 保存用户流量包状态
                this.userService.saveTraffic(server, user, ut);
                // 保存用户流量日志
                this.userLogService.save(userLog);
            }
            ServerLog obj = new ServerLog();
            obj.setServer(server.getIp());
            obj.setTraffic(traffic[0]);

            // 保存服务器日志
            this.serverLogService.save(obj);
        });

        logger.info("任务结束-统计服务器/用户流量");
    }


    /**
     * 用户流量包使用情况
     *
     * @desc 每日凌晨 00:01分开始执行
     */
    @Scheduled(cron = "0 1 0 * * ?")
    public void userTrafficTask() {
        Iterable<User> users = this.userService.list();

        Map<String, Server> map = new HashMap<>();

        for (User user : users) {
            UserTraffic ut = user.getUserTraffic();

            boolean modifyFlag = false;
            if (ut != null) {
                // 用户有流量包, 判断用户流量包是否已用完或流量包过期
                if (ut.getTrafficUsed() < ut.getTraffic()
                        || ut.getEndDate().before(Util.getCurrentDateTime())) {
                    Server server = map.get(user.getServer());
                    if (server == null || !server.getIp().equals(user.getServer())) {
                        server = this.serverService.findByIp(user.getServer());
                        map.put(user.getServer(), server);
                    }

                    // 判断用户是否有新的流量包
                    Iterable<UserTraffic> nows = this.userTrafficService.findByNow(user.getId());
                    if (nows != null && nows.iterator().hasNext()) {
                        ut = nows.iterator().next();
                        user.setUserTraffic(ut);
                    } else {
                        user.setUserTraffic(null);
                        Shadowsocket.remove(server, user);
                    }
                    modifyFlag = true;
                }
            } else {
                // 判断用户是否有新的流量包
                Iterable<UserTraffic> nows = this.userTrafficService.findByNow(user.getId());
                if (nows != null && nows.iterator().hasNext()) {
                    ut = nows.iterator().next();
                    user.setUserTraffic(ut);
                    modifyFlag = true;
                }
            }

            if (modifyFlag) {
                this.userService.save(user);
            }
        }
    }

}
