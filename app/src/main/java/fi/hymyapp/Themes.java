package fi.hymyapp;

/**
 * @author Daniel Hewugenhauser
 * @version 1
 */
public class Themes {

    private String name;
    private  String path;


    /**
     *
     * @param name
     * @param path
     */
    Themes(String name,String path){

        this.name=name;
        this.path=path;
    }
    /**
     *
     * @return
     */
    public String toString(){
        return name;
    }
    /**
     *
     * @return
     */
    public String getDatapath(){
        return path;
    }
}
