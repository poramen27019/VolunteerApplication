package com.example.collaborativeapp.Constructor;


public class WorkProfile {

    private String event_org;
    private String event_name;
    private String event_detail;
    private String event_date_close;
    private String event_location;
    private String event_type;



    public WorkProfile(String event_org, String event_name,String event_detail , String event_date_close,String event_location, String event_type) {

        this.event_org = event_org;
        this.event_name = event_name;
        this.event_detail = event_detail;
        this.event_date_close = event_date_close;
        this.event_location = event_location ;
        this.event_type = event_type;

//        this.memberID = memberID;


    }

    public String getevent_org() {
        return event_org;
    }

    public String getevent_name() {
        return event_name;
    }
    public String getevent_detail() { return event_detail; }

    public String getevent_date_close() {
        return event_date_close;
    }
    public String getevent_location() {
        return event_location;
    }

    public String getevent_type() {
        return event_type;
    }


}

//                event_org
//               event_name
//               event_detail
//               event_date_close
//               event_location
//               event_type