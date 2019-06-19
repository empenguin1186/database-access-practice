package jp.co.penguin.emperor.databaseaccess.entity;

import lombok.Data;

@Data
public class Article {

    private String content;

    private String author;

    public Article() {}

    public Article(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
