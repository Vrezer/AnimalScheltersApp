package com.example.animalscheltersapp;

public class Animal {
    private String Name;
    private String Age;
    private String Breed;
    private String Sex;
    private String Description;

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

    public Animal(String name, String age, String breed, String sex, String description) {
        Name = name;
        Age = age;
        Breed = breed;
        Sex = sex;
        Description = description;
    }
}
