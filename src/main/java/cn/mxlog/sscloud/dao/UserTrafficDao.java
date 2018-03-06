package cn.mxlog.sscloud.dao;

import cn.mxlog.sscloud.model.UserTraffic;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * Repository : UserTraffic.
 *
 * @author F.Du Created on 18 十月 2017
 */
public interface UserTrafficDao extends PagingAndSortingRepository<UserTraffic, Integer>, JpaSpecificationExecutor<UserTraffic> {

    @Query(
            value = "select * from user_traffic where user_id=?1 order by end_date desc limit 0,1",
            nativeQuery = true
    )
    UserTraffic getMaxUserTraffic(Integer id);

    @Query(
            value="select ut from UserTraffic ut where ut.userId = ?2 and ut.startDate < ?1 and ut.endDate>?1 and status = 0"
    )
    Iterable<UserTraffic> findNow(Date now, Integer userId);
}
