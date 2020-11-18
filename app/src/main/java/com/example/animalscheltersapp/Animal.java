package com.example.animalscheltersapp;

public class Animal {
    private String Name;
    private String Age;
    private String Breed;
    private String Sex;
    private String Description;
    private String UrlPicture;
    private String id;
    private String date;

    public String getUrlPicture() {
        return UrlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        UrlPicture = urlPicture;
    }


    public Animal(String name, String age, String breed, String sex, String description, String urlPicture, String id, String date) {
        Name = name;
        Age = age;
        Breed = breed;
        Sex = sex;
        Description = description;
        UrlPicture = urlPicture;
        this.id = id;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBreed() {
        return Breed;
    }

    public void setBreed(String breed) {
        Breed = breed;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Animal()
    {}

}
