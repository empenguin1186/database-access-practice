package jp.co.penguin.emperor.databaseaccess.controller;

import jp.co.penguin.emperor.databaseaccess.entity.Article;
import jp.co.penguin.emperor.databaseaccess.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/")
public class MainController {

    @Autowired
   private ArticleMapper artlcleMapper;

    @RequestMapping(path="articles", method = RequestMethod.GET)
    public List<Article> findAll(@RequestParam(required = false) String keyword) {
        if (keyword == null) {
            return artlcleMapper.findAll();
        }
        return artlcleMapper.findByKeyWord(keyword);
    }

    @RequestMapping(path="articles/{id}", method=RequestMethod.GET)
    public Article findOne(@PathVariable int id) {
        return artlcleMapper.findOne(id);
    }

    @RequestMapping(path="articles/create", method=RequestMethod.POST)
    public boolean insert(@RequestBody Article article) {
        return artlcleMapper.insert(article);
    }

    @RequestMapping(path="articles/{id}/delete", method=RequestMethod.POST)
    public boolean delete(@PathVariable int id) {
        return artlcleMapper.delete(id);
    }

    @RequestMapping(path="articles/{id}/update", method=RequestMethod.PUT)
    public boolean update(@PathVariable int id, @RequestBody Article article) {
        return artlcleMapper.update(id, article.getContent(), article.getAuthor());
    }
}
