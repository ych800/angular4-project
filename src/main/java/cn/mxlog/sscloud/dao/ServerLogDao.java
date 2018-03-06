package cn.mxlog.sscloud.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import cn.mxlog.sscloud.model.ServerLog;

/**
 * Repository : ServerLog.
 * @author F.Du Created on 18 十月 2017
 */
public interface ServerLogDao extends PagingAndSortingRepository<ServerLog, Integer>,JpaSpecificationExecutor<ServerLog> {

}
