package com.example.shopkeepersdiary;

public class Customer {


        public String username;
        public String address;
        public String mob;
        public String prof;
        public int total;
        public int historytotal;

        public Customer()
        {

        }
        public Customer(String s1,String s2,String s3,String s4,int t,int h)
        {
            this.username=s1;
            this.address=s2;
            this.mob=s3;
            this.prof=s4;
            this.total=t;
            this.historytotal=h;
        }

    public int getHistorytotal() {
        return historytotal;
    }

    public void setHistorytotal(int historytotal) {
        this.historytotal = historytotal;
    }

    public void setAddress(String email1) {
            this.address = email1;
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

    public void setTotal(int t) {
        this.total = t;
    }

        public String getAddress() {
            return address;
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

    public int getTotal() {
        return total;
    }
}
