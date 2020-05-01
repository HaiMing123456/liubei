package com.tensquare.article.dao;

import com.tensquare.article.pojo.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    /**
     * 修改文章状态
     * @param id
     */
    @Modifying
    @Query(value = "update tb_article set state='1' where id=?1",nativeQuery = true)
    public void updateState(String id);

    /**
     * 点赞
     * @param id
     * @return
     */
    @Modifying
    @Query(value = "update tb_article a set thumbup=thumbup+1 where id=?1", nativeQuery = true)
    public int updateThumbup(String id);
	
}
