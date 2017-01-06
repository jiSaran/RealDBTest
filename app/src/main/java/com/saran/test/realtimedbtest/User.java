package com.saran.test.realtimedbtest;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by core I5 on 1/3/2017.
 */
@IgnoreExtraProperties
public class User {
    private int uid;
    private UserDetails ud;

    public User(){}

    public User(int user_id, UserDetails user_details){
        this.uid = user_id;
        this.ud = user_details;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public UserDetails getUd() {
        return this.ud;
    }

    public void setUd(UserDetails ud) {
        this.ud = ud;
    }
}
