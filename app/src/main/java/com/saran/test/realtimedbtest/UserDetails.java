package com.saran.test.realtimedbtest;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by core I5 on 1/4/2017.
 */
@IgnoreExtraProperties
public class UserDetails {
    private String fname,lname;

    public UserDetails(){}

    public UserDetails(String firstname,String lastname)
    {
        this.fname = firstname;
        this.lname = lastname;
    }

    public String getFname() {
        return this.fname;
    }

    public String getLname() {
        return this.lname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
