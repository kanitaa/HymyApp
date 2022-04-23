package fi.hymyapp;

public class User {

    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User() {
        name = "";
        age = 0;
    }

    public String toString(){
        return name + Integer.toString(age);
    }
}
