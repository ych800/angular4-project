package cn.mxlog.sscloud.service;

import cn.mxlog.sscloud.base.BaseService;
import cn.mxlog.sscloud.dao.ServerDao;
import cn.mxlog.sscloud.model.Server;
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
 * ServerService 
 *
 * @author F.Du Created on 18 十月 2017
 */
@Service
public class ServerService extends BaseService {


    @Autowired
    private ServerDao serverDao;

    @Transactional
    public int save(Server server) {
        if (server == null) {
            return -1;
        }
        if (server.getId() == null) {
            server.setCreatedate(new Date());
            server.setStatus(0);
            this.serverDao.save(server);
        } else {
            Server entity = this.findById(server);
            copyProperties(server, entity, true);
            entity.setModifydate(new Date());
            this.serverDao.save(entity);
        }
        return 0;
    }


    public Server findById(Server server) {
        if (server == null || server == null) {
            return null;
        }

        return this.serverDao.findOne(server.getId());
    }

    public Iterable<Server> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return this.serverDao.findAll(sort);
    }

    public Iterable<Server> list(Pageable page) {
        Sort sort = page.getSort();
        sort.and(new Sort(Sort.Direction.DESC, "id"));
        return this.serverDao.findAll(page);
    }

    public Iterable<Server> list(Server server, Pageable page) {
        if (server == null) {
            return this.list(page);
        }

        Specification<Server> spec = new Specification<Server>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery Criteria, CriteriaBuilder builder) {

                Predicate p = builder.greaterThanOrEqualTo(root.get("status"), 0);

                return p;
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        page.getSort().and(sort);

        return this.serverDao.findAll(spec, page);
    }

    public void delete(Server server) {
        this.serverDao.delete(server);
    }

    public void delete(List<Server> list) {
        this.serverDao.delete(list);
    }

    public Server findByIp(String server) {

        return this.serverDao.findByIp(server);
    }
}
