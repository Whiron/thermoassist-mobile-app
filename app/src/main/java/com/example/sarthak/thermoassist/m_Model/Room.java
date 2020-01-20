package com.example.sarthak.thermoassist.m_Model;

/**
 * Created by Oclemy on 6/21/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 * 1. OUR MODEL CLASS
 */
public class Room {

    String temp,hum,flag;

    public Room() {
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String temp_Flag) {
        this.flag = temp_Flag;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }
}
