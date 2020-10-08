package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Rabbit extends Animal{
    protected int healthPercent = 100;
    public int quantity= 0;
    public boolean isAlive;
    public String[] editableFood = {"Grass","Corn","Oat"};
    private int initialPrice = 300;

    private int reduceHealthValue;

    public Rabbit(String name,String gender,int currentAge){
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
