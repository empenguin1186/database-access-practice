package jp.co.penguin.emperor.databaseaccess.repository;

import jp.co.penguin.emperor.databaseaccess.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class ArticleRepositoryImpl implements ArticleRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static String CONTENT = "content";
    private static String AUTHOR = "author";

    @Override
    public Article findOne(int id) {
        Article article = jdbcTemplate.queryForObject("select * from articles where id = ?", new Object[]{id}, new RowMapper<Article>() {
            @Override
            public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                Article article = new Article(rs.getString(CONTENT), rs.getString(AUTHOR));
                return article;
            }
        });
        return article;
    }

    @Override
    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();
        List<Map<String,Object>> list = jdbcTemplate.queryForList("select * from articles");
        for (Map<String, Object> stringObjectMap : list) {
            articles.add(new Article(stringObjectMap.get(CONTENT).toString(), stringObjectMap.get(AUTHOR).toString()));
        }
        return articles;
    }

    @Override
    public int create(Article article) {
        jdbcTemplate.update("INSERT INTO articles (content, author) VALUES (?, ?)",
                article.getContent(), article.getAuthor());
        return 1;
    }

    @Override
    public int update(int id, Article article) {
        jdbcTemplate.update("UPDATE articles SET (content, author) = (?, ?) WHERE id = ?",
                article.getContent(), article.getAuthor(), id);
        return 1;
    }

    @Override
    public int delete(int id) {
        jdbcTemplate.update("DELETE FROM articles WHERE id = ?", id);
        return 1;
    }
}
