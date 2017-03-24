package org.cbccessence.mobihealth.model;

/**
 * Created by aangjnr on 01/03/2017.
 */

public class Attendee {
    String attendee_name;
    String meeting_uid;
    Boolean attended;

    public Attendee(){}

    public Attendee(String attendeeName ){

        this.attendee_name = attendeeName;
        //this.meeting_uid = meetingUid;



    }


    public Attendee(String attendeeName, Boolean attended){

        this.attendee_name = attendeeName;
        this.attended = attended;



    }





    public void setAttendeeName(String name){

        this.attendee_name = name;
    }


    public String getAttendeeName(){

        return attendee_name;
    }


    public void setMeetingUid(String uid){

        this.meeting_uid = uid;
    }


    public String getMeetingUid(){

        return attendee_name;
    }



    public void setAttended(Boolean attended){

        this.attended = attended;
    }


    public Boolean getAttended(){

        return attended;
    }

}

