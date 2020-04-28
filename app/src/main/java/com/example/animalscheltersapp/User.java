package com.example.animalscheltersapp;

public class User {
    private String Email,Password,Name,Surname;
    private String Age;
    private String Number;
    private String Sex;
   private boolean Admin = false;


    public User()
    {

    }

    public User(String email, String password, String name, String surname, String age, String number, String sex, boolean admin) {
        Email = email;
        Password = password;
        Name = name;
        Surname = surname;
        Age = age;
        Number = number;
        Sex = sex;
        Admin = admin;
    }

    public void setAdmin(boolean admin) {
        Admin = admin;
    }

    public boolean isAdmin() {
        return Admin;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }
}
