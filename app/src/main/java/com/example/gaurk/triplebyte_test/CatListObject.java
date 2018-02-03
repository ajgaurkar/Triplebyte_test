package com.example.gaurk.triplebyte_test;

/**
 * Created by gaurk on 2/2/2018.
 */

public class CatListObject {

    String cat_url;
    String description;
    String time;
    String title;

    public CatListObject(String cat_url, String description, String time, String title) {
        this.cat_url = cat_url;
        this.description = description;
        this.time = time;
        this.title = title;
    }

    public String getCat_url() {
        return cat_url;
    }

    public void setCat_url(String cat_url) {
        this.cat_url = cat_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
