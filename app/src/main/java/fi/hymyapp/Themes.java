package fi.hymyapp;

/**
 * @author Daniel Hewugenhauser
 */
public class Themes {

    private String name;
    private  String path;



    Themes(String name,String path){

        this.name=name;
        this.path=path;
    }


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
