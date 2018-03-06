package cn.mxlog.sscloud.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import cn.mxlog.sscloud.model.Server;

/**
 * Repository : Server.
 * @author F.Du Created on 18 十月 2017
 */
public interface ServerDao extends PagingAndSortingRepository<Server, Integer>,JpaSpecificationExecutor<Server> {

    Server findByIp(String server);
}
