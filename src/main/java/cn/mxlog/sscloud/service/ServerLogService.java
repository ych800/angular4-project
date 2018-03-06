package cn.mxlog.sscloud.service;

import cn.mxlog.sscloud.base.BaseService;
import cn.mxlog.sscloud.dao.ServerLogDao;
import cn.mxlog.sscloud.model.ServerLog;
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
 * ServerLogService 
 *
 * @author F.Du Created on 18 十月 2017
 */
@Service
public class ServerLogService extends BaseService{
	
	
    @Autowired
    private ServerLogDao serverlogDao;

    @Transactional
    public int save(ServerLog serverlog) {
		if(serverlog == null){
			return -1;
		}
        if (serverlog.getId() == null) {
			serverlog.setCreatedate(new Date());
			serverlog.setStatus(0);
            this.serverlogDao.save(serverlog);
        } else {
            ServerLog entity = this.findById(serverlog);
            copyProperties(serverlog, entity, true);
			entity.setModifydate(new Date());
            this.serverlogDao.save(entity);
        }
		return 0;
    }


    public ServerLog findById(ServerLog serverlog) {
        if (serverlog == null || serverlog == null) {
            return null;
        }

        return this.serverlogDao.findOne(serverlog.getId());
    }

    public Iterable<ServerLog> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return this.serverlogDao.findAll(sort);
    }

    public Iterable<ServerLog> list(Pageable page) {
        Sort sort = page.getSort();
        sort.and(new Sort(Sort.Direction.DESC, "id"));
        return this.serverlogDao.findAll(page);
    }

    public Iterable<ServerLog> list(ServerLog serverlog, Pageable page) {
        if (serverlog == null) {
            return this.list(page);
        }

        Specification<ServerLog> spec = new Specification<ServerLog>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery Criteria, CriteriaBuilder builder) {

                Predicate p = builder.greaterThanOrEqualTo(root.get("status"), "0");

                if(serverlog.getCreatedate() != null){
                    p = builder.greaterThanOrEqualTo(root.get("createdate"), serverlog.getCreatedate());
                }

                return p;
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        page.getSort().and(sort);

        return this.serverlogDao.findAll(spec, page);
    }

    public void delete(ServerLog serverlog) {
        this.serverlogDao.delete(serverlog);
    }

    public void delete(List<ServerLog> list) {
        this.serverlogDao.delete(list);
    }

    @Transactional
    public void saveLogs(List<ServerLog> list){
        if(list == null || list.size() == 0){
            return ;
        }

        list.forEach(log ->{
            this.save(log);
        });
    }
}
