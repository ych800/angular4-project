package cn.mxlog.sscloud.dao;

import cn.mxlog.sscloud.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Repository : User.
 * @author F.Du Created on 18 十月 2017
 */
public interface UserDao extends PagingAndSortingRepository<User, Integer>,JpaSpecificationExecutor<User> {

    @Query(value="select port from user where server=?1 and port is not null order by port asc ", nativeQuery = true)
    List<Integer> getPorts(String server);

    Iterable<User> findByServer(String server);
}
