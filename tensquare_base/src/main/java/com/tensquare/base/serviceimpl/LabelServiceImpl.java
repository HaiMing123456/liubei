package com.tensquare.base.serviceimpl;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import util.IdWorker;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:
 */

@Service
@Transactional
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelDao mLabelDao;

    @Autowired
    private IdWorker mIdWorker;

    @Override
    public List<Label> findAll() {
        return mLabelDao.findAll();
    }

    @Override
    public Label findById(String id) {
        return mLabelDao.findById(id).get();
    }

    @Override
    public void save(Label label) {
        label.setId(mIdWorker.nextId()+"");
        mLabelDao.save(label);
    }

    @Override
    public void update(Label label) {
        mLabelDao.save(label);
    }

    @Override
    public void deleteById(String id) {
        mLabelDao.deleteById(id);
    }

    @Override
    public List<Label> findSearch(Label label) {
        return mLabelDao.findAll(new Specification<Label>() {
            /**
             * @param root 根对象,也就是把条件封装到哪个对象中,
             * @param criteriaQuery 封装的都是查询的关键字,比如 group by order by 等
             * @param cb 用来封装条件对象的
             * @return
             */
            @Nullable
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList=new ArrayList<>();
                if(label.getLabelname()!=null &&
                        !"".equals(label.getLabelname())){
                    predicateList.add(cb.like(
                            root.get("labelname").as(String.class), "%"+
                                    label.getLabelname()+"%" ) );
                }
                if(label.getState()!=null &&
                        !"".equals(label.getState())){
                    predicateList.add(cb.equal(
                            root.get("state").as(String.class), label.getState()) );
                }
                if(label.getRecommend()!=null &&
                        !"".equals(label.getRecommend())){
                    predicateList.add(cb.equal(
                            root.get("recommend").as(String.class),
                            label.getRecommend() ) );
                }
                return cb.and( predicateList.toArray( new
                        Predicate[predicateList.size()]) );
            }
        });
    }

    @Override
    public Page<Label> queryPage(Label label, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return mLabelDao.findAll(new Specification<Label>() {
            /**
             * @param root 根对象,也就是把条件封装到哪个对象中,
             * @param criteriaQuery 封装的都是查询的关键字,比如 group by order by 等
             * @param cb 用来封装条件对象的
             * @return
             */
            @Nullable
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList=new ArrayList<>();
                if(label.getLabelname()!=null &&
                        !"".equals(label.getLabelname())){
                    predicateList.add(cb.like(
                            root.get("labelname").as(String.class), "%"+
                                    label.getLabelname()+"%" ) );
                }
                if(label.getState()!=null &&
                        !"".equals(label.getState())){
                    predicateList.add(cb.equal(
                            root.get("state").as(String.class), label.getState()) );
                }
                if(label.getRecommend()!=null &&
                        !"".equals(label.getRecommend())){
                    predicateList.add(cb.equal(
                            root.get("recommend").as(String.class),
                            label.getRecommend() ) );
                }
                return cb.and( predicateList.toArray( new
                        Predicate[predicateList.size()]) );
            }
        },pageable);

    }


}
