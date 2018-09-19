package com.example.sunidhi.inclass10;

/**
 * Created by Sunidhi on 02-Apr-18.
 */

public class SignUpClass {
    String user_fname, user_lname, user_email, user_id, token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;

    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    @Override
    public String toString() {
        return "SignUpClass{" +
                "user_fname='" + user_fname + '\'' +
                ", user_lname='" + user_lname + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_id='" + user_id + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
