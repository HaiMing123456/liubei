package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

import util.IdWorker;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:    吐槽
 */

@Service
public class SpitService {

    @Autowired
    private SpitDao mSpitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询所有
     * @return
     */
    public List<Spit> findAll(){
        return mSpitDao.findAll();
    }

    public Spit findById(String _id){
        return mSpitDao.findById(_id).get();
    }

    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setThumbup(0);
        spit.setComment(0);
        spit.setShare(0);
        spit.setVisits(0);

        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"split");
        }

        mSpitDao.save(spit);
    }

    public void update(Spit spit){
        mSpitDao.save(spit);
    }

    public void deleteById(String id){
        mSpitDao.deleteById(id);
    }


    public Page<Spit> findByParentid(String parentid,int page,int size){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        return mSpitDao.findByParentid(parentid,pageRequest);
    }

    public void updateThumbup(String spitId) {
        // 方式一：效率有问题,相当于执行了两次数据库操作
        //Spit spit = mSpitDao.findById(spitId).get();
        //spit.setThumbup(spit.getThumbup() == null ? 0 : spit.getThumbup()+1);
        //mSpitDao.save(spit);

        // 方式二：采用原生mongodb方式
        Query query = new  Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update=new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
