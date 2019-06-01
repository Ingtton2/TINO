package org.androidtown.tino;

public class HistoryItem {

    private String Name;
    private String Hour;
    private String Minute;
    private int dHour;
    private int dMin;

    public int getdHour(){
        return dHour;
    }
    public int getdMin(){
        return dMin;
    }
    public String getName(){
        return Name;
    }
    public String getHour(){
        return Hour;
    }
    public String getMinute(){
        return Minute;
    }
    public HistoryItem(String name, String hour,String minute,int dhour,int dmin){
        this.Name=name;
        this.Hour=hour;
        this.Minute=minute;
        this.dHour=dhour;
        this.dMin=dmin;
    }
}