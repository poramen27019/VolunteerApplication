package com.example.collaborativeapp.Model;

public class InterestModel {
    private String event_name,join_event_id,event_location,event_date,event_type,event_org,event_detail,member_fname,member_phone,logo_event_path;

    public InterestModel() {

    }


    public InterestModel(String event_name , String join_event_id,String event_location,String event_date,String event_type,String event_org,String event_detail,String member_fname,String member_phone,String logo_event_path) {



        this.event_name = event_name;
        this.join_event_id = join_event_id;
        this.event_location = event_location;
        this.event_date = event_date;
        this.event_type = event_type;
        this.event_org = event_org;
        this.event_detail = event_detail;
        this.member_fname = member_fname;
        this.member_phone = member_phone;
        this.logo_event_path = logo_event_path;

    }
    public String getevent_name() { return event_name; }
    public String getjoin_event_id() { return join_event_id; }
    public String getevent_location() { return event_location; }
    public String getevent_date() { return event_date; }
    public String getevent_type() { return event_type; }
    public String getevent_org() { return event_org; }
    public String getevent_detail() { return event_detail; }
    public String getmember_fname() { return member_fname; }
    public String getmember_phone() { return member_phone; }
    public String getlogo_event_path() {return logo_event_path; }
//
}
