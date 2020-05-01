package com.tensquare.base.service;

import com.tensquare.base.pojo.Label;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:
 */

public interface LabelService {

    public List<Label> findAll();

    public Label findById(String id);

    public void save(Label label);

    public void update(Label label);

    public void deleteById(String id);

    List<Label> findSearch(Label label);

    Page<Label> queryPage(Label label, int page, int size);
}
