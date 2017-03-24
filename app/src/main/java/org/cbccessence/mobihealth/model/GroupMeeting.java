package org.cbccessence.mobihealth.model;

/**
 * Created by aangjnr on 01/03/2017.
 */

public class GroupMeeting {
    String meeting_topic;
    String meeting_location;
    String meeting_startDateTime;
    String meeting_endDateTime;




    public GroupMeeting(){


    }



    public GroupMeeting (String topic, String sDateTime, String eDateTime, String loc){
        this.meeting_topic = topic;
        this.meeting_location  = loc;
        this.meeting_startDateTime = sDateTime;
        this.meeting_endDateTime = eDateTime;
    }

    public void setMeetingTopic(String _topic){
        this.meeting_topic = _topic;

    }

    public String getMeetingTopic(){
        return meeting_topic;

    }


    public void setMeetingLoc(String _loc){
        this.meeting_location = _loc;

    }

    public String getMeetingLoc(){
        return meeting_location;

    }


    public void setMeetingStartDateTime(String sdt){
        this.meeting_startDateTime = sdt;

    }

    public String getMeetingStartDateTime(){
        return meeting_startDateTime;

    }


    public void setMeetingEndDateTime(String edt){
        this.meeting_endDateTime = edt;

    }

    public String getMeetingEndDateTime(){
        return meeting_endDateTime;

    }














}
