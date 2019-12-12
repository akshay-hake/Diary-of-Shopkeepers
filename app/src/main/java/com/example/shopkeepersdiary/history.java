package com.example.shopkeepersdiary;

public class history {

    public String date;
    public int amount;
    public int remaining;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String status;
    public String time;
    public String reason;

    public history() {

    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public history(String date, int amount,String status,String time,int remaining,String reason) {
        this.date = date;
        this.amount = amount;
        this.status=status;
        this.time=time;
        this.remaining=remaining;
        this.reason=reason;
    }
}
