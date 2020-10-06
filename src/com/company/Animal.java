package com.company;

public abstract class Animal {
    private String name;
    private Gender gender;
    private int currentAge;
    protected int healthPercent = 100;
    private boolean isAlive;
    private int maxAge;

    public Animal(String name,String gender,int currentAge){
        this.name = name;
        this.gender = Gender.valueOf(gender.toUpperCase());
        this.currentAge = currentAge;
    }


    public abstract void eat(Food food);

    public abstract void die();

    public abstract boolean isLiving();

    public abstract void breed(Animal animal);
}
