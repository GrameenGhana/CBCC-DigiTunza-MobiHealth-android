package org.cbccessence.mobihealth.model;

/**
 * Created by aangjnr on 28/02/2017.
 */

public class Content {

    String name;
    String location;





    public Content(){

    }


    public Content(String name, String loc){
        this.name = name;
        this.location  = loc;
    }

    public void setDocName(String _name){
        this.name = _name;

    }

    public String getDocName(){
        return name;

    }

    public void setDocLoc(String _loc){
        this.location = _loc;

    }

    public String getDocLoc(){
        return location;

    }

}
