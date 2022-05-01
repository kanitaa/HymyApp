package fi.hymyapp;

/**
 * @author Daniel Hewugenhauser
 * @version 1
 */
public class Themes {

    private String name;
    private  String path;


    /**
     *Constructor for Themes class
     * @param name Name of the theme.
     * @param path Datapath for the database reference.
     */
    Themes(String name,String path){

        this.name=name;
        this.path=path;
    }
    /**
     *
     * @return Returns name of the theme.
     */
    public String toString(){
        return name;
    }
    /**
     *
     * @return Return the path value of the theme.
     */
    public String getDatapath(){
        return path;
    }
}
