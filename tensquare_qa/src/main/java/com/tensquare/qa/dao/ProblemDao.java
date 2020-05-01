package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     * 最新问答
     * @param labelid
     * @return
     */
    @Query(value = "select * from `tb_problem` pro , `tb_pl` pl  where pro.`id` = pl.`problemid` and pl.`labelid` = ? ORDER BY pro.`replytime` DESC",nativeQuery = true)
    public Page<Problem> newlist(String labelid, Pageable pageable);

    /**
     * 热门问答
     * @return
     */
    @Query(value = "select * from `tb_problem` pro , `tb_pl` pl  where pro.`id` = pl.`problemid` and pl.`labelid` = ? order by pro.`reply` DESC",nativeQuery = true)
    public Page<Problem> hotlist(String labelid, Pageable pageable);

    /**
     * 等待问答
     * @return
     */
    @Query(value = "select * from `tb_problem` pro , `tb_pl` pl  where pro.`id` = pl.`problemid` and pl.`labelid` = ? AND pro.`reply` = 0 ORDER BY pro.`createtime` DESC",nativeQuery = true)
    public Page<Problem> waitlist(String labelid, Pageable pageable);

}
