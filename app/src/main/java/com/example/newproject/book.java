package com.example.newproject;

import android.net.Uri;
import android.widget.TextView;

public class book {
    private String author,title,description;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public book(String author, String title, String description, String url) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
    }
}
