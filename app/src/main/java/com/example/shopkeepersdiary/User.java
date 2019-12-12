package com.example.shopkeepersdiary;

public class User {
    public String username;
    public String email1;
    public String mob;
    public String prof;
    int tc;

    public User()
    {

    }
    public User(String s1,String s2,String s3,String s4,int tc)
    {
        this.username=s1;
        this.email1=s2;
        this.mob=s3;
        this.prof=s4;
        this.tc=tc;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public void setTc(int tc) {
        this.tc=tc;
    }

    public int getTc() {
        return tc;
    }

    public String getEmail1() {
        return email1;
    }

    public String getMob() {
        return mob;
    }

    public String getUsername() {
        return username;
    }

    public String getProf() {
        return prof;
    }
}
