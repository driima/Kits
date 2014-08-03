package com.dragonphase.kits.util;

import java.util.ArrayList;
import java.util.List;
 
public class Time{
    
    private long milliseconds;
    
    public Time(String time) throws IllegalArgumentException{
        String[] numbers = Trim(time.split("[^0-9]"));
        String[] types = Trim(time.split("[0-9]"));
        
        milliseconds = 0;
        
        try{
	        for (int i = 0; i < types.length; i ++)
	            milliseconds += getTime(Integer.parseInt(numbers[i]), types[i]);
        }catch (Exception ex){
        	throw new IllegalArgumentException(ex);
        }
    }
    
    public long getMilliseconds(){
        return milliseconds;
    }
    
    public double getSeconds(){
        return (double)getMilliseconds()/1000;
    }
    
    public double getMinutes(){
        return getSeconds()/60;
    }
    
    public double getHours(){
        return getMinutes()/60;
    }
    
    public double getDays(){
        return getHours()/24;
    }
    
    public double getMonths(){
        return getDays()/30;
    }
    
    public double getYears(){
        return getMonths()/12;
    }
    
    //Recursive time translation to seconds
    
    private int getTime(int number, String type){
        int time = number;
        switch(type){
            case "y":
                time = getTime(time*12, "mo");
                break;
            case "mo":
                time = getTime(time*30, "d");
                break;
            case "d":
                time = getTime(time*24, "h");
                break;
            case "h":
                time = getTime(time*60, "m");
                break;
            case "m":
                time = getTime(time*60, "s");
                break;
            case "s":
                time = getTime(time*1000, "ms");
            case "ms":
            default:
                break;
        }
        return time;
    }
    
    //Helper methods
    
    private String[] Trim(String[] args){
        List<String> list = new ArrayList<String>();
        for(String arg : args) {
            if(arg != null && arg.length() > 0) {
              list.add(arg);
            }
        }
        return list.toArray(new String[list.size()]);
    }
}