package com.example.jeffreynyauke.myapplication.models;



public class Video {

    private int type;
    private boolean isDownloaded;
    private String  id, title, author, size, timeStamp, url;

    public Video() {
    }

    public Video(String id, String title, String author, String size, boolean isDownloaded,
                 String timeStamp, String url, int type) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
        this.size = size;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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
