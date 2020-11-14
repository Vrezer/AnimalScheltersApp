package com.example.animalscheltersapp;

public class Animal {
    private String Name;
    private String Age;
    private String Breed;
    private String Sex;
    private String Description;
    private String UrlPicture;

    public String getUrlPicture() {
        return UrlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        UrlPicture = urlPicture;
    }

    public Animal(String name, String age, String breed, String sex, String description, String urlPicture) {
        Name = name;
        Age = age;
        Breed = breed;
        Sex = sex;
        Description = description;
        UrlPicture = urlPicture;
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
