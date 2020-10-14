package com.company.AnimalSubClasses;
import com.company.Animal;
import com.company.Food;

import java.util.*;

public class Horse extends Animal {

    public Horse(String name,String gender){
        super(name,gender);
        initialPrice = 15000;
        healthGrowthRef = 5;
        breedQuantity = 1;
        isAlive = true;
        editableFood = new String[]{"Grass","Milk"};
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
        //randomNum = min + (int)(Math.random() * (max-min+1));
        lostHealth =  10 + (int)(Math.random() * (30-20+1));
        this.healthPercent = Math.max(this.healthPercent -lostHealth,0);
    }
    public void die(){
        if (this.healthPercent <= 0) {
            this.isAlive = false;
        }
    }

    public boolean isLiving(){
        return this.isAlive;
    }

    public int getBreedQuantity(){
        return breedQuantity;
    }

    public static Animal breed(String gender){
        Scanner scanner = new Scanner(System.in);
        System.out.println("It is a new baby.What is the baby animals name:");
        var animalName = scanner.nextLine();
        return (new Dog(animalName,gender));
    }

}
