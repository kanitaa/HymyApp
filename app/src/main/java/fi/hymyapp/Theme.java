package fi.hymyapp;

import java.util.ArrayList;
import java.util.List;

public class Theme {
    // Create an single instance from the Themes list
    private List<Themes> themesList;
    private static final Theme ourInstance = new Theme();


    public static Theme getInstance(){
        return ourInstance;
    }
    private Theme(){
        themesList=new ArrayList<>();
        themesList.add(new Themes("Osallisuuskysely","involvementQuestions"));
        themesList.add(new Themes("Oikeudetkysely","rightsQuestions"));

    }
    public List<Themes> getThemes(){return themesList;}

}
