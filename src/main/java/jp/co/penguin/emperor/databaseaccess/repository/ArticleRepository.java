package jp.co.penguin.emperor.databaseaccess.repository;

import jp.co.penguin.emperor.databaseaccess.entity.Article;

import java.util.List;

public interface ArticleRepository {

    public abstract Article findOne(int id);

    public abstract List<Article> findAll();

    public abstract int create(Article article);

    public abstract int update(int id, Article article);

    public abstract int delete(int id);

}
