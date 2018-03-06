package cn.mxlog.sscloud.service;

import cn.mxlog.sscloud.base.BaseService;
import cn.mxlog.sscloud.dao.UserLogDao;
import cn.mxlog.sscloud.model.UserLog;
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
 * UserLogService 
 *
 * @author F.Du Created on 18 十月 2017
 */
@Service
public class UserLogService extends BaseService{
	
	
    @Autowired
    private UserLogDao userlogDao;

    @Transactional
    public int save(UserLog userlog) {
		if(userlog == null){
			return -1;
		}
        if (userlog.getId() == null) {
			userlog.setCreatedate(new Date());
			userlog.setStatus(0);
            this.userlogDao.save(userlog);
        } else {
            UserLog entity = this.findById(userlog);
            copyProperties(userlog, entity, true);
			entity.setModifydate(new Date());
            this.userlogDao.save(entity);
        }
		return 0;
    }


    public UserLog findById(UserLog userlog) {
        if (userlog == null || userlog == null) {
            return null;
        }

        return this.userlogDao.findOne(userlog.getId());
    }

    public Iterable<UserLog> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return this.userlogDao.findAll(sort);
    }

    public Iterable<UserLog> list(Pageable page) {
        Sort sort = page.getSort();
        sort.and(new Sort(Sort.Direction.DESC, "id"));
        return this.userlogDao.findAll(page);
    }

    public Iterable<UserLog> list(UserLog userlog, Pageable page) {
        if (userlog == null) {
            return this.list(page);
        }

        Specification<UserLog> spec = new Specification<UserLog>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery Criteria, CriteriaBuilder builder) {

                //Predicate p = builder.equal(root.get("id"), "1");

                //return p;
				return null;
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        page.getSort().and(sort);

        return this.userlogDao.findAll(spec, page);
    }

    public void delete(UserLog userlog) {
        this.userlogDao.delete(userlog);
    }

    public void delete(List<UserLog> list) {
        this.userlogDao.delete(list);
    }


    @Transactional
    public void saveLogs(List<UserLog> list){
        if(list == null || list.size() == 0){
            return ;
        }

        list.forEach(log ->{
            this.save(log);
        });
    }
}
