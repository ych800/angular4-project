package cn.mxlog.sscloud.service;

import cn.mxlog.sscloud.base.BaseResult;
import cn.mxlog.sscloud.base.BaseService;
import cn.mxlog.sscloud.controller.params.UserOrderCom;
import cn.mxlog.sscloud.dao.UserOrderDao;
import cn.mxlog.sscloud.model.*;
import cn.mxlog.sscloud.util.Shadowsocket;
import cn.mxlog.sscloud.util.Util;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * UserOrderService 
 *
 * @author F.Du Created on 18 十月 2017
 */
@Service
public class UserOrderService extends BaseService {


    @Autowired
    private UserOrderDao userorderDao;

    @Autowired
    private  UserService userService;

    @Autowired
    private UserTrafficService userTrafficService;

    @Autowired
    private ServerService serverService;
    @Autowired
    private ComboService comboService;

    @Transactional
    public int save(UserOrder userorder) {
        if (userorder == null) {
            return -1;
        }
        if (userorder.getId() == null) {
            userorder.setCreatedate(new Date());
            userorder.setStatus(0);
            this.userorderDao.save(userorder);
        } else {
            UserOrder entity = this.findById(userorder);
            copyProperties(userorder, entity, true);
            entity.setModifydate(new Date());
            this.userorderDao.save(entity);
        }
        return 0;
    }


    public UserOrder findById(UserOrder userorder) {
        if (userorder == null || userorder == null) {
            return null;
        }

        return this.userorderDao.findOne(userorder.getId());
    }

    public Iterable<UserOrder> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return this.userorderDao.findAll(sort);
    }

    public Iterable<UserOrder> list(Pageable page) {
        Sort sort = page.getSort();
        sort.and(new Sort(Sort.Direction.DESC, "id"));
        return this.userorderDao.findAll(page);
    }

    public Iterable<UserOrder> list(UserOrder userorder, Pageable page) {
        if (userorder == null) {
            return this.list(page);
        }

        Specification<UserOrder> spec = new Specification<UserOrder>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery Criteria, CriteriaBuilder builder) {

                //Predicate p = builder.equal(root.get("id"), "1");

                //return p;
                return null;
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        page.getSort().and(sort);

        return this.userorderDao.findAll(spec, page);
    }

    public void delete(UserOrder userorder) {
        this.userorderDao.delete(userorder);
    }

    public void delete(List<UserOrder> list) {
        this.userorderDao.delete(list);
    }

    /**
     * 新订单
     * @param com
     * @return
     */
    @Transactional
    public BaseResult newOrder(UserOrderCom com) {
        UserOrder order = com.getUserOrder();
        User user = order.getUser();
        Server server = com.getServer();
        Combo combo = com.getCombo();

        BaseResult result = new BaseResult();

        if (order == null || user == null || user.getId() == null || server == null && server.getId() != null
                || combo == null && combo.getId() != null) {
            result.setStatus(-1);
            result.setMessage("参数错误");
            return result;
        }

        server = this.serverService.findById(server);
        combo = this.comboService.findById(combo);
        user = this.userService.findById(user);

        user.setServer(server.getIp());
        user.setServerName(server.getName());

        //  获取服务器可用端口
        if(StringUtils.isEmpty(user.getPort())) {
            this.userService.getPort(user);
        }

        if(StringUtils.isEmpty(order.getOrderNo())) {
            order.setOrderNo(Util.genOrderNo());
        }

        UserTraffic traffic = user.getUserTraffic();
        // 如果结算周期为0, 表示当前为流量套餐, 流量套餐结束日期为: 当前周期结束时间或自然月结束
        if(combo.getPeriod() == 0){
            // 用户当前没有套餐或为免费套餐, 结束日期为自然月结束
            if(traffic == null || traffic.getTraffic() == null || traffic.getStatus() == 10) {
                traffic = new UserTraffic();
                traffic.setTraffic(combo.getTraffic() * order.getQuantity());
                traffic.setStartDate(Util.getCurrentDateTime());
                Date endDate = DateUtils.addMonths(Util.getCurrentDate(), 1);
                endDate = DateUtils.setDays(endDate, 1);
                traffic.setEndDate(endDate);
            }else{
                // 原套餐,增加流量. 流量有效期不变
                traffic.setTraffic(traffic.getTraffic() + combo.getTraffic() * order.getQuantity());
            }
            traffic.setModifydate(Util.getCurrentDateTime());
            traffic.setUserId(user.getId());
            // 保存用户流量表
            this.userTrafficService.save(traffic);
        }else{
            // 普通套餐, 直接添加用户流量表

            // 获取最大流量有效日期
            UserTraffic ut = this.userTrafficService.getMaxUserTraffic(user);
            Date endDate = null;
            for(int i=1;i<=order.getQuantity();i++) {
                traffic = new UserTraffic();
                traffic.setTraffic(combo.getTraffic());

                Date startDate = null;
                if(endDate == null) {
                    // 计算开始,结束日期
                    if (ut == null) {
                        startDate = Util.getCurrentDateTime();
                        traffic.setStartDate(startDate);
                    } else {
                        startDate = ut.getEndDate();
                    }
                }else{
                    startDate = endDate;
                }

                endDate = DateUtils.addDays(startDate, combo.getPeriod());
                endDate = DateUtils.truncate(endDate, Calendar.DAY_OF_MONTH);
                traffic.setStartDate(startDate);
                traffic.setEndDate(endDate);
                traffic.setCreatedate(Util.getCurrentDateTime());
                traffic.setModifydate(Util.getCurrentDateTime());
                traffic.setUserId(user.getId());
                // 保存用户流量表
                this.userTrafficService.save(traffic);
            }
        }

        if(user.getUserTraffic() == null){
            user.setUserTraffic(traffic);
        }

        this.userService.save(user);

        order.setCombo(combo);
        order.setUser(user);
        order.setTraffic(combo.getTraffic());
        order.setOrderDate(Util.getCurrentDateTime());
        this.userorderDao.save(order);
        // 开启服务器代理端口
        //TODO 下单时, 是否开启端口?
        boolean flag = Shadowsocket.add(server, user);

        logger.info("订单生成成功, 开启服务器端口: "+ user);

        return null;
    }
}
