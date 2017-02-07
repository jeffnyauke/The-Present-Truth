package com.example.jeffreynyauke.myapplication.models;



public class Music {

    private int type;
    private boolean isDownloaded;
    private String  id, title, author, image, timeStamp, url;

    public Music() {
    }

    public Music(String id, String title, String author, String image, boolean isDownloaded,
                 String timeStamp, String url, int type) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
        this.image = image;
        this.isDownloaded = isDownloaded;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getIsDownloaded() {
        return isDownloaded;
    }

    public void setIsDownloaded(boolean isDownloaded) {
        this.isDownloaded = isDownloaded;
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
