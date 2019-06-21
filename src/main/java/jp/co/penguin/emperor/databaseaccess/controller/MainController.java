package jp.co.penguin.emperor.databaseaccess.controller;

import jp.co.penguin.emperor.databaseaccess.entity.Article;
import jp.co.penguin.emperor.databaseaccess.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/")
public class MainController {

    @Autowired
    ArticleMapper mapper;

    @RequestMapping(path="articles", method = RequestMethod.GET)
    public String findAll(@RequestParam(required = false) String keyword) {
        if (keyword == null) {
            return mapper.findAll().toString();
        }
        return mapper.findByKeyWord(keyword).toString();
    }

    @RequestMapping(path="articles/{id}", method=RequestMethod.GET)
    public String findOne(@PathVariable int id) {
        return mapper.findOne(id).toString();
    }

    @RequestMapping(path="articles/create", method=RequestMethod.POST)
    public String insert(@RequestBody Article article) {
        return String.valueOf(mapper.insert(article));
    }

    @RequestMapping(path="articles/{id}/delete", method=RequestMethod.POST)
    public String delete(@PathVariable int id) {
        return String.valueOf(mapper.delete(id));
    }

    @RequestMapping(path="articles/{id}/update", method=RequestMethod.POST)
    public String update(@PathVariable int id, @RequestBody Article article) {
        return String.valueOf(mapper.update(id, article.getContent(), article.getAuthor()));
    }
}
