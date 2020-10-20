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
    public int breedQuantity;
    public int veterinaryCost;
    public String healthStatus; //Health Status can be "Sick","Health","Death"
    public boolean isAlive;
    public int currentAge;
    public int maxAge;

    public Animal(String name,String gender){
        this.name = name;
        this.gender = Gender.valueOf(gender.toUpperCase());
    }


    public abstract void eat(Food food,double quantity);

    public abstract void lostHealth();

    public abstract void die();

    public abstract boolean isLiving();

    public abstract void updateHealthStatus(String status);

    public abstract void increaseAge();

    public static Animal breed(String gender) {
        return null;
    }

    public String getName(){
        return name;
    }

    public String getGender(){
        return String.valueOf(gender);
    }

    public int getBreedQuantity(){
        return breedQuantity;
    }

    public int getHealthPercent(){return healthPercent;}

    public int getInitialPrice(){return initialPrice;}


    public void printField(){
        System.out.print("Name: " + name +" Gender: " + gender + " ");
    }
}
