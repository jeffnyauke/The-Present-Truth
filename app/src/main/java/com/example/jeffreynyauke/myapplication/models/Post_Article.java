package com.example.jeffreynyauke.myapplication.models;



public class Post_Article {

    private int type;
    private String  id, name, text, image, timeStamp, url;

    public Post_Article() {
    }

    public Post_Article(String id, String name, String image, String text,
                        String timeStamp, String url, int type) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.text = text;
        this.timeStamp = timeStamp;
        this.url = url;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}