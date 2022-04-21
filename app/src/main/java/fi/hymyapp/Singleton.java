package fi.hymyapp;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    private List<Teemat> teematList;
    private static final Singleton ourInstance = new Singleton();


    public static Singleton getInstance(){
        return ourInstance;
    }
    private Singleton(){
        teematList=new ArrayList<>();
        teematList.add(new Teemat("Teema1"));

    }
    public List<Teemat> getTeemat(){return teematList;}

}
