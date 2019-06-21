package jp.co.penguin.emperor.databaseaccess.mapper;

import jp.co.penguin.emperor.databaseaccess.entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Select("SELECT * FROM articles WHERE id = #{id}")
    Article findOne(int id);

    @Select("SELECT * FROM articles")
    List<Article> findAll();

    @Insert("INSERT INTO articles (content, author) VALUES (#{content}, #{author})")
    boolean insert(Article article);

    @Update("UPDATE articles SET (content, author) = (#{arg1}, #{arg2}) WHERE id = #{arg0}")
    boolean update(int arg0, String arg1, String arg2);

    @Delete("DELETE FROM articles WHERE id = #{id}")
    boolean delete(int id);

    @Select("SELECT * FROM articles WHERE content LIKE CONCAT('%', #{keyword}, '%')")
    List<Article> findByKeyWord(String keyword);
}
