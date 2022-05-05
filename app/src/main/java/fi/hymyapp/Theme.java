package fi.hymyapp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Heugenhauser
 * @version 1
 *
 * Singleton class for different types question themes.
 */

public class Theme {

    // Create an single instance from the Themes list
    private List<Themes> themesList;
    /**
     * Creates a single instance that can be accessed globally.
     */
    private static final Theme ourInstance = new Theme();

    /**
     * Gets the corresponding instance from the user input.
     * @return  Single instance from the Theme class.
     */
    public static Theme getInstance(){
        return ourInstance;
    }

    /**
     * Initialize the Singleton constructor.
     * Arraylist for the different types of question themes in the application
     */
    private Theme(){
        themesList=new ArrayList<>();
        themesList.add(new Themes("Osallisuuskysely","involvementQuestions"));
        themesList.add(new Themes("Oikeudetkysely","rightsQuestions"));

    }

    /**
     * This method is called when accessing themes
     * @return returns list of themes
     */
    public List<Themes> getThemes(){return themesList;}

}
