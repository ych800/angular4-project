package cn.mxlog.sscloud.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import cn.mxlog.sscloud.model.UserLog;

/**
 * Repository : UserLog.
 * @author F.Du Created on 18 十月 2017
 */
public interface UserLogDao extends PagingAndSortingRepository<UserLog, Integer>,JpaSpecificationExecutor<UserLog> {

}
