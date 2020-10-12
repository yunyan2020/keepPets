package com.company;

public abstract class Animal {
    private String name;
    private Gender gender;
    protected int healthPercent = 100;
    protected int initialPrice = 0;
    public String[] editableFood ;
    public double healthGrowthRef;
    public double totalFeedQuantity;
    public int lostHealth;
    public int addedHealth;
    private boolean isAlive;

    public Animal(String name,String gender){
        this.name = name;
        this.gender = Gender.valueOf(gender.toUpperCase());
    }


    public abstract void eat(Food food,double quantity);

    public abstract void lostHealth();

    public abstract void die();

    public abstract boolean isLiving();

    public abstract void breed(Animal animal);

    public String getName(){
        return name;
    }

    public void printField(){
        System.out.print("Name: " + name +" Gender: " + gender + " ");
    }
}
