package jp.co.penguin.emperor.databaseaccess.controller;

import jp.co.penguin.emperor.databaseaccess.entity.Article;
import jp.co.penguin.emperor.databaseaccess.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/")
public class MainController {

    @Autowired
    ArticleRepository articleRepository;

    @RequestMapping(path="articles", method = RequestMethod.GET)
    public String findAll() {
        return articleRepository.findAll().toString();
    }

    @RequestMapping(path="articles/{id}", method=RequestMethod.GET)
    public String findOne(@PathVariable int id) {
        return articleRepository.findOne(id).toString();
    }

    @RequestMapping(path="articles/create", method=RequestMethod.POST)
    public String create(@RequestBody Article article) {
        return String.valueOf(articleRepository.create(article));
    }

    @RequestMapping(path="articles/{id}/delete", method=RequestMethod.POST)
    public String delete(@PathVariable int id) {
        return String.valueOf(articleRepository.delete(id));
    }

    @RequestMapping(path="articles/{id}/update", method=RequestMethod.PUT)
    public String update(@PathVariable int id) {
        return String.valueOf(articleRepository.delete(id));
    }
}
