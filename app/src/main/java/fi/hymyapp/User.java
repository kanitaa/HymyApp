package fi.hymyapp;

public class User {

    private String name;
    private int age;
    private boolean newUser;

    public User(String name, int age, boolean newUser) {
        this.name = name;
        this.age = age;
        this.newUser = newUser;
    }

    public User() {
        name = "";
        age = 0;
        newUser = true;
    }

    public String toString(){
        return name + Integer.toString(age);
    }
}
