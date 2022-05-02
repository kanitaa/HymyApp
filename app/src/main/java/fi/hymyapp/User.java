package fi.hymyapp;

public class User { // This class is used for user preference saving
    /**
     * @author Taru Vikström
     * @version 1
     * User class is used for storing values to save them in shared preferences
     */
    private String name;
    private int age;
    private boolean newUser;

    /**
     * User constructor that takes parameters.
     * @param name sets the users name value into the name variable.
     * @param age sets the users age value into the age variable.
     * @param newUser sets the user boolean value, if it's a new user or old with values found from preferences
     */
    public User(String name, int age, boolean newUser) {
        this.name = name;
        this.age = age;
        this.newUser = newUser;
    }

    /**
     * Another constructor for user that has set in default values and takes no parameters.
     */
    public User() {
        name = "";
        age = 0;
        newUser = true;
    }


    /**
     * toString function returns a textual representation of the user object
     * @return user objects name and age variables are returned
     */
    public String toString(){
        return name + Integer.toString(age);
    }
}
