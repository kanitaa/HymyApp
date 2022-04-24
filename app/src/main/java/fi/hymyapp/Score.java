package fi.hymyapp;

import android.util.Log;

public class Score {

    private int currentPoints;

    public Score(){
        this.currentPoints=0;
    }
    public Score(int points){
        this.currentPoints=points;
    }
    public void increasePoints(int points){
        this.currentPoints = this.currentPoints+points;
        System.out.println(currentPoints);
        Log.d("asd",Integer.toString(currentPoints));
    }

    public String checkResult(){
        if(currentPoints<1){
            return "LOL OOT IHAN PASKA JA SUN PERHE KANS SAIT " + currentPoints+" PISTETTÄ. OPETTELE ELÄÄ";
        }else if(currentPoints<3){
            return "TIESIT JOTAIN MUT SILTI SEMI TYMÄ SAIT " + currentPoints+" PISTETTÄ KEK";
        }else if(currentPoints<5){
            return "MENI IHAN KIVASTI SAIT " + currentPoints+ " PISTETTÄ, HYVIN VEDETTY";
        }else{
            return "SUL ON IHAN BIS PERHE JA KAIKKI HYVIN SAIT " + currentPoints+" PISTETTÄ WOO";
        }


    }
    public String toString(){
        return Integer.toString(currentPoints);
    }
}
