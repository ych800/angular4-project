package cn.mxlog.sscloud.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import cn.mxlog.sscloud.model.Combo;

/**
 * Repository : Combo.
 * @author F.Du Created on 18 十月 2017
 */
public interface ComboDao extends PagingAndSortingRepository<Combo, Integer>,JpaSpecificationExecutor<Combo> {

}
