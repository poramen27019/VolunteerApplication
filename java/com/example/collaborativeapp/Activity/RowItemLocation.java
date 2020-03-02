package com.example.collaborativeapp.Activity;

public class RowItemLocation {
    public Double event_lat, event_lng;
    public String event_id,event_name,event_org;

    public RowItemLocation(Double event_lat, Double event_lng,String event_id,String event_name ,String event_org ){
        this.event_lat = event_lat;
        this.event_lng = event_lng;
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_org = event_org;
    }

    public Double getevent_lat() {
        return event_lat;
    }

    public Double getevent_lng() {
        return event_lng;
    }

    public String getevent_id() {
        return event_id;
    }

    public String getevent_name() {
        return event_name;
    }
    public String getevent_org() {
        return event_org;
    }




}

