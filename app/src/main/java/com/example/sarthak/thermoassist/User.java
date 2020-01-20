package com.example.sarthak.thermoassist;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class User {

    public String f_name;
    public String l_name;
    public String contact_no;
    public String gender;
    public String pi_no;
    public String tot_rooms;



    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String f_name, String l_name, String contact_no, String gender, String tot_rooms) {
        this.f_name = f_name;
        this.l_name = l_name;
        this.contact_no = contact_no;
        this.gender = gender;
        this.pi_no = "1";
        this.tot_rooms=tot_rooms;
    }

    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDlno() {
        return dlno;
    }

    public void setDlno(String dlno) {
        this.dlno = dlno;
    }

    public String getNp() {
        return np;
    }

    public void setNp(String np) {
        this.np = np;
    }*/
}