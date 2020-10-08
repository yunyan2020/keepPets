package com.company;

public class Chicken extends Animal{

    protected int healthPercent = 100;
    public boolean isAlive;
    public String[] editableFood = {"Beef","Grass","Corn"};
    private int initialPrice = 200;
    private int reduceHealthValue;

    public Chicken(String name,String gender,int currentAge){
        super(name,gender,currentAge);
    }

    public int checkBalance(int balance){
        return balance - initialPrice;
    }

    public void printField(){
        super.printField();
        System.out.println("Health Value: " + healthPercent
                + "Reduce Health Value from last time: " + reduceHealthValue);
    }

    public void eat(Food food){

    }

    public void die(){

    }

    public boolean isLiving(){
        return true;
    }

    public void breed(Animal animal){

    }

}
