package org.cbccessence.mobihealth.model;

/**
 * Created by aangjnr on 02/03/2017.
 */

public class SubSection {

    String subSectionName;
    String activityName;

    public SubSection(){}

    public SubSection(String name, String activity_name){
        this.subSectionName = name;
        this.activityName = activity_name;

    }




    public void setActivityName(String name){

        this.activityName = name;

    }

    public void setSubSecName(String ss_name){

        this.subSectionName = ss_name;

    }


    public String getSubSectionName (){
        return subSectionName;


    }

    public String getActivityName (){
        return activityName;


    }

}
