package fi.hymyapp;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    private List<Themes> themesList;
    private static final Singleton ourInstance = new Singleton();


    public static Singleton getInstance(){
        return ourInstance;
    }
    private Singleton(){
        themesList=new ArrayList<>();
        themesList.add(new Themes("Osallisuuskysely","involvementQuestions"));
        themesList.add(new Themes("Oikeudetkysely","rightsQuestions"));

    }
    public List<Themes> getThemes(){return themesList;}

}
