package cn.mxlog.sscloud.service;

import cn.mxlog.sscloud.base.BaseService;
import cn.mxlog.sscloud.dao.UserTrafficDao;
import cn.mxlog.sscloud.model.User;
import cn.mxlog.sscloud.model.UserTraffic;
import cn.mxlog.sscloud.util.Util;
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
import java.util.Date;
import java.util.List;

/*
 * UserTrafficService 
 *
 * @author F.Du Created on 18 十月 2017
 */
@Service
public class UserTrafficService extends BaseService{
	
	
    @Autowired
    private UserTrafficDao usertrafficDao;

    @Transactional
    public int save(UserTraffic usertraffic) {
		if(usertraffic == null){
			return -1;
		}
        if (usertraffic.getId() == null) {
			usertraffic.setCreatedate(new Date());
			usertraffic.setStatus(0);
            this.usertrafficDao.save(usertraffic);
        } else {
            UserTraffic entity = this.findById(usertraffic);
            copyProperties(usertraffic, entity, true);
			entity.setModifydate(new Date());
            this.usertrafficDao.save(entity);
        }
		return 0;
    }


    public UserTraffic findById(UserTraffic usertraffic) {
        if (usertraffic == null || usertraffic == null) {
            return null;
        }

        return this.usertrafficDao.findOne(usertraffic.getId());
    }

    public Iterable<UserTraffic> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return this.usertrafficDao.findAll(sort);
    }

    public Iterable<UserTraffic> list(Pageable page) {
        Sort sort = page.getSort();
        sort.and(new Sort(Sort.Direction.DESC, "id"));
        return this.usertrafficDao.findAll(page);
    }

    public Iterable<UserTraffic> list(UserTraffic usertraffic, Pageable page) {
        if (usertraffic == null) {
            return this.list(page);
        }

        Specification<UserTraffic> spec = new Specification<UserTraffic>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery Criteria, CriteriaBuilder builder) {

                //Predicate p = builder.equal(root.get("id"), "1");

                //return p;
				return null;
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        page.getSort().and(sort);

        return this.usertrafficDao.findAll(spec, page);
    }

    public void delete(UserTraffic usertraffic) {
        this.usertrafficDao.delete(usertraffic);
    }

    public void delete(List<UserTraffic> list) {
        this.usertrafficDao.delete(list);
    }


    /**
     * 获取用户已存在的最大套餐, 不包含免费套餐 status: 10
     * @return
     */
    public UserTraffic getMaxUserTraffic(User user){
        return this.usertrafficDao.getMaxUserTraffic(user.getId());
    }

    /**
     * 获取用户当前可用套餐
     */
    public Iterable<UserTraffic> findByNow(Integer userId){
        return this.usertrafficDao.findNow(Util.getCurrentDateTime(), userId);
    }
}
