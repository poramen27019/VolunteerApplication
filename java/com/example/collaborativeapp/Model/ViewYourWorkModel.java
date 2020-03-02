package com.example.collaborativeapp.Model;

public class ViewYourWorkModel {
    private String member_id,member_fname,member_lname,member_phone;

    public ViewYourWorkModel(){
    }
    public ViewYourWorkModel(String member_id,String member_fname,String member_lname,String member_phone) {
        this.member_id = member_id;
        this.member_fname = member_fname;
        this.member_lname = member_lname;
        this.member_phone = member_phone;
}
    public String getmember_id() {return member_id; }
    public String getmember_fname() {return member_fname; }
    public String getmember_lname() {return member_lname; }
    public String getmember_phone() {return member_phone; }
}
