package com.example.collaborativeapp.Model;

public class YourWorkModel {
    private String event_id,eventname,event_date,event_type,event_org,event_detail,event_location,member_fname,member_phone,num2,event_sum;

    public YourWorkModel(){

    }


    public YourWorkModel(String event_id,
                         String eventname,
                         String event_date,
                         String event_type,
                         String event_org,
                         String event_detail,
                         String event_location,
                         String member_fname,
                         String member_phone,
                         String num2,
                         String event_sum) {

        this.event_id = event_id;
        this.eventname = eventname;
        this.event_date = event_date;
        this.event_type = event_type;
        this.event_org = event_org;
        this.event_detail = event_detail;
        this.event_location = event_location;
        this.member_fname = member_fname;
        this.member_phone = member_phone;
        this.num2 = num2;
        this.event_sum = event_sum;
    }

//    public String getPerson() {
//        return person;
//    }
    public String getevent_id() { return event_id; }
    public String geteventname() { return eventname; }
    public String getevent_date() { return event_date; }
    public String getevent_type() { return event_type; }
    public String getevent_org() { return event_org; }
    public String getevent_detail() { return event_detail; }
    public String getevent_location() { return event_location; }
    public String getmember_fname() { return member_fname; }
    public String getmember_phone() { return member_phone; }
    public String getnum2() { return num2; }
    public String getevent_sum() { return event_sum; }
}