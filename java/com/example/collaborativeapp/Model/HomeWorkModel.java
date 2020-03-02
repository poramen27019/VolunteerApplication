package com.example.collaborativeapp.Model;





public class HomeWorkModel {

    private String event_id,event_member_id,eventname,event_location,event_date_close,event_type,event_org,event_detail,member_fname,member_phone,num,event_sum,logo_event_path;

    public HomeWorkModel() {
    }

    public HomeWorkModel( String event_id,String event_member_id ,String eventname,String event_location,String event_date_close,String event_type,String event_org,String event_detail,String member_fname,String member_phone,String num,String event_sum,String logo_event_path) {

        this.event_id = event_id;
        this.event_member_id = event_member_id;
        this.eventname = eventname;
        this.event_location = event_location;
        this.event_date_close = event_date_close;
        this.event_type = event_type;
        this.event_org = event_org;
        this.event_detail = event_detail;
        this.member_fname = member_fname;
        this.member_phone = member_phone;
        this.num = num;
        this.event_sum = event_sum;
        this.logo_event_path = logo_event_path;
    }
    public String getevent_id() {return event_id; }
    public String getevent_member_id() {return event_member_id; }
    public String geteventname() {return eventname; }
    public String getevent_location() {return event_location; }
    public String getevent_date_close() {return event_date_close; }
    public String getevent_type() {return event_type; }
    public String getevent_org() {return event_org; }
    public String getevent_detail() {return event_detail; }
    public String getmember_fname() {return member_fname; }
    public String getmember_phone() {return member_phone; }
    public String getnum() {return num; }
    public String getevent_sum() {return event_sum; }
    public String getlogo_event_path() {return logo_event_path; }
}

