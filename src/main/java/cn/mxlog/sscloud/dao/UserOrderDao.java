package cn.mxlog.sscloud.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import cn.mxlog.sscloud.model.UserOrder;

/**
 * Repository : UserOrder.
 * @author F.Du Created on 18 十月 2017
 */
public interface UserOrderDao extends PagingAndSortingRepository<UserOrder, Integer>,JpaSpecificationExecutor<UserOrder> {

}
