package com.company.AnimalSubClasses;

import com.company.Animal;
import com.company.Food;

public class Rabbit extends Animal {

    public Rabbit(String name,String gender){
        super(name,gender);
        initialPrice = 300;
        healthGrowthRef = 0.4;
        editableFood = new String[]{"Grass","Corn","Oat"};
    }

    public int checkBalance(int balance){
        return balance - initialPrice;
    }

    public void printField(){
        super.printField();
        System.out.println("Health Value: " + healthPercent
                + " Lost Health last round: " + lostHealth + " Added Health by feed:" + addedHealth);
    }

    public void eat(Food food, double quantity){
        this.totalFeedQuantity += quantity;
        var lastHealthPercent = this.healthPercent;
        //If the total feed quantity more than the health growth reference quantity
        // and the health is less than 100 than update the healthPercent
        if ((totalFeedQuantity >= healthGrowthRef) && (healthGrowthRef < 100)) {
            this.addedHealth = 10;
            this.healthPercent += this.addedHealth;
            if (this.healthPercent > 100){
                this.addedHealth = 100 - lastHealthPercent;
                this.healthPercent = 100;
            }
            this.totalFeedQuantity = 0;
        }
    }

    public void lostHealth(){
        //Produce the random number between 10 - 30
        //int num = min + (int)(Math.random() * (max-min+1));
        int randomNum  = 10 + (int)(Math.random() * 21);
        lostHealth = randomNum;
        this.healthPercent = Math.max(this.healthPercent -lostHealth,0);
    }

    public void die(){

    }

    public boolean isLiving(){
        return true;
    }

    public void breed(Animal animal){

    }

}
