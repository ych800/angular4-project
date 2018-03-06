package cn.mxlog.sscloud.service;

import cn.mxlog.sscloud.base.BaseService;
import cn.mxlog.sscloud.dao.ComboDao;
import cn.mxlog.sscloud.model.Combo;
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
 * ComboService 
 *
 * @author F.Du Created on 18 十月 2017
 */
@Service
public class ComboService extends BaseService{
	
	
    @Autowired
    private ComboDao comboDao;

    @Transactional
    public int save(Combo combo) {
		if(combo == null){
			return -1;
		}
        if (combo.getId() == null) {
			combo.setCreatedate(new Date());
			combo.setStatus(0);
            this.comboDao.save(combo);
        } else {
            Combo entity = this.findById(combo);
            copyProperties(combo, entity, true);
			entity.setModifydate(new Date());
            this.comboDao.save(entity);
        }
		return 0;
    }


    public Combo findById(Combo combo) {
        if (combo == null || combo == null) {
            return null;
        }

        return this.comboDao.findOne(combo.getId());
    }

    public Iterable<Combo> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return this.comboDao.findAll(sort);
    }

    public Iterable<Combo> list(Pageable page) {
        Sort sort = page.getSort();
        sort.and(new Sort(Sort.Direction.DESC, "id"));
        return this.comboDao.findAll(page);
    }

    public Iterable<Combo> list(Combo combo, Pageable page) {
        if (combo == null) {
            return this.list(page);
        }

        Specification<Combo> spec = new Specification<Combo>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery Criteria, CriteriaBuilder builder) {

                //Predicate p = builder.equal(root.get("id"), "1");

                //return p;
				return null;
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        page.getSort().and(sort);

        return this.comboDao.findAll(spec, page);
    }

    public void delete(Combo combo) {
        this.comboDao.delete(combo);
    }

    public void delete(List<Combo> list) {
        this.comboDao.delete(list);
    }
}
