package com.example.collaborativeapp.Model;

public class JoinEventModel {
    private String event_id,member_id,event_name,join_event_id,event_location,event_date_close,event_type,event_org,event_detail,member_fname,member_phone;

    public JoinEventModel() {

    }


    public JoinEventModel(String event_name , String join_event_id,String event_location,String event_date_close,String event_type,String event_org,String event_detail,String member_fname,String member_phone) {


//        this.member_id = member_id;
        this.event_name = event_name;
        this.join_event_id = join_event_id;
        this.event_location = event_location;
        this.event_date_close = event_date_close;
        this.event_type = event_type;
        this.event_org = event_org;
        this.event_detail = event_detail;
        this.member_fname = member_fname;
        this.member_phone = member_phone;

//        this.event_join_id = event_join_id;
    }
    public String getevent_name() { return event_name; }
    public String getjoin_event_id() { return join_event_id; }
    public String getevent_location() { return event_location; }
    public String getevent_date_close() { return event_date_close; }
    public String getevent_type() { return event_type; }
    public String getevent_org() { return event_org; }
    public String getevent_detail() { return event_detail; }
    public String getmember_fname() { return member_fname; }
    public String getmember_phone() { return member_phone; }
//    public String getevent_join_id() { return event_join_id; }
//    public String getmember_id() { return member_id; }

}
