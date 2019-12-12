package com.example.shopkeepersdiary;

public class CustomerView {

    private String id;
    private String username;
    private String imageURL;
    private String mob;

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public CustomerView(String id, String username, String mob, String imageURL)
    {
        this.id=id;
        this.username=username;
        this.imageURL=imageURL;
        this.mob=mob;
    }

    public CustomerView() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
