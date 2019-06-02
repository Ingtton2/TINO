package org.androidtown.tino;

public class ScheduleData {
    String txt01, txt02;

    public ScheduleData() {

    }

    public ScheduleData(String txt01, String txt02) {
        setTxt01(txt01);
        setTxt02(txt02);
    }

    public void setTxt01(String txt01) {
        this.txt01 = txt01;
    }

    public void setTxt02(String txt02) {
        this.txt02 = txt02;
    }

    public String getTxt01(){
        return this.txt01;
    }

    public String getTxt02(){
        return this.txt02;
    }
}
