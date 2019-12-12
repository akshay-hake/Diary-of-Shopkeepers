package com.example.shopkeepersdiary;

public class HistoryView {

    private String hmoney;
    private String hstatus;
    private String hdate;
    private String htime;
    private String hremain;
    private String hreason;

    public HistoryView(String hmoney, String hstatus, String hdate, String htime,String hremain,String hreason) {
        this.hmoney = hmoney;
        this.hstatus = hstatus;
        this.hdate = hdate;
        this.htime = htime;
        this.hremain=hremain;
        this.hreason=hreason;
    }

    public String getHmoney() {
        return hmoney;
    }

    public String getHremain() {
        return hremain;
    }

    public String getHreason() {
        return hreason;
    }

    public void setHreason(String hreason) {
        this.hreason = hreason;
    }

    public void setHremain(String hremain) {
        this.hremain = hremain;
    }

    public void setHmoney(String hmoney) {
        this.hmoney = hmoney;
    }

    public String getHstatus() {
        return hstatus;
    }

    public void setHstatus(String hstatus) {
        this.hstatus = hstatus;
    }

    public String getHdate() {
        return hdate;
    }

    public void setHdate(String hdate) {
        this.hdate = hdate;
    }

    public String getHtime() {
        return htime;
    }

    public void setHtime(String htime) {
        this.htime = htime;
    }

    public HistoryView() {
    }

}
