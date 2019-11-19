package com.dawei.dao;

import com.dawei.model.Article;

import java.util.List;

public interface ArticleDao {

    int insertBatch(List<Article> articles);

    int insert(Article article);

}
