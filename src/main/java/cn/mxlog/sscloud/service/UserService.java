package cn.mxlog.sscloud.service;

import cn.mxlog.sscloud.base.BaseResult;
import cn.mxlog.sscloud.base.BaseService;
import cn.mxlog.sscloud.base.Pager;
import cn.mxlog.sscloud.dao.UserDao;
import cn.mxlog.sscloud.model.Server;
import cn.mxlog.sscloud.model.User;
import cn.mxlog.sscloud.model.UserTraffic;
import cn.mxlog.sscloud.util.Shadowsocket;
import cn.mxlog.sscloud.util.Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/*
 * UserService 
 *
 * @author F.Du Created on 17 十月 2017
 */
@Service
public class UserService extends BaseService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private UserTrafficService userTrafficService;

    @Value("${config.port.start}")
    private Integer startPort;

    @Value("${config.port.start}")
    private Integer endPort;

    @Transactional
    public int save(User user) {
        if (user == null) {
            return -1;
        }
        if (user.getId() == null) {
            user.setCreatedate(new Date());
            user.setStatus(0);

            if (!StringUtils.isEmpty(user.getPassword())) {
                user.setPortPwd(user.getPassword());
                user.setPassword(Util.getMD5Code(user.getPassword()));
            }

            this.userDao.save(user);
        } else {
            User entity = this.findById(user);
            copyProperties(user, entity, true);
            entity.setModifydate(new Date());
            entity.setUserTraffic(user.getUserTraffic());
            this.userDao.save(entity);
        }
        return 0;
    }


    public User findById(User user) {
        if (user == null || user == null) {
            return null;
        }

        return this.userDao.findOne(user.getId());
    }

    public Iterable<User> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return this.userDao.findAll(sort);
    }

    public Iterable<User> list(Pageable page) {
        Sort sort = page.getSort();
        sort.and(new Sort(Sort.Direction.DESC, "id"));
        return this.userDao.findAll(page);
    }

    public Iterable<User> list(User user, Pageable page) {
        if (user == null) {
            return this.list(page);
        }

        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery Criteria, CriteriaBuilder builder) {

                Predicate p = builder.greaterThanOrEqualTo(root.get("status"), 0);

                if (!StringUtils.isBlank(user.getUsername())) {
                    Predicate p1 = builder.equal(root.get("username"), user.getUsername());
                    p1 = builder.or(p1, builder.equal(root.get("email"), user.getUsername()));
                    p1 = builder.or(p1, builder.equal(root.get("phone"), user.getUsername()));
                    p1 = builder.or(p1, builder.equal(root.get("name"), user.getUsername()));
                    p = builder.and(p, p1);
                }

                return p;

            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        page.getSort().and(sort);

        return this.userDao.findAll(spec, page);
    }

    public void delete(User user) {
        this.userDao.delete(user);
    }

    public void delete(List<User> list) {
        this.userDao.delete(list);
    }


    public BaseResult login(User user) {
        Iterable<User> list = this.list(user, new Pager());
        BaseResult result = new BaseResult();

        if (list != null && list.iterator().hasNext()) {
            User u = list.iterator().next();

            if (u != null && u.getPassword() != null &&
                    u.getPassword().equalsIgnoreCase(Util.getMD5Code(user.getPassword()))) {

                copyProperties(u, user, false);
                user.setPortPwd(null);
                user.setPassword(null);
                result.setData(user);

                return result;
            }
        }

        logger.error("用户登录失败, 用户名或密码错误. user:" + user);
        result.setStatus(-1);
        result.setMessage("Username or password is error!");
        return result;
    }

    /**
     * 获取服务器可用端口
     *
     * @param user
     * @return
     */
    public Integer getPort(User user) {
        List<Integer> ports = this.userDao.getPorts(user.getServer());
        int port = startPort;

        if (ports != null && ports.size() > 0) {
            for (int i = 0; i < ports.size() - 1; i++) {
                if (ports.get(i + 1) - ports.get(i) > 1) {
                    port = ports.get(i);
                    break;
                } else {
                    port = ports.get(i + 1);
                }
            }
        }

        user.setPort("" + (port + 1));
        return port + 1;
    }

    /**
     * 根据服务器ip查找用户列表
     *
     * @param ip
     * @return
     */
    public Iterable<User> findByServer(String ip) {
        return this.userDao.findByServer(ip);
    }

    /**
     * 保存用户流量包,使用信息
     *
     * @param user
     */
    public void saveTraffic(Server server, User user, UserTraffic oldUt) {
        // 保存用户流量
        if (user == null) {
            return;
        }

        // 修改原流量包状态
        if (oldUt != null) {
            this.userTrafficService.save(oldUt);
        }

        UserTraffic ut = user.getUserTraffic();
        // 用户流量包已用完
        if (ut == null) {
            // 用户有新流量包, 则重新开发端口
            Iterable<UserTraffic> nows = this.userTrafficService.findByNow(user.getId());
            if (nows != null && nows.iterator().hasNext()) {
                ut = nows.iterator().next();
                user.setUserTraffic(ut);
                Shadowsocket.add(server, user);
            }
        }


        // 修改用户流量包信息
        this.save(user);
    }
}
