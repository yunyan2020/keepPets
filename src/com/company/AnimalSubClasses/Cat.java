package com.company.AnimalSubClasses;

import com.company.Animal;
import com.company.Food;

import java.util.Scanner;

public class Cat extends Animal {
    public Cat(String name,String gender){
        super(name,gender);
        initialPrice = 1500;
        healthGrowthRef = 0.5;
        breedQuantity = 3;
        veterinaryCost = 500;
        healthStatus = "Health";
        isAlive = true;
        editableFood = new String[]{"Beef","Milk"};
        currentAge = 0;
        maxAge = 16;
    }

    public int checkBalance(int balance){
        return balance - initialPrice;
    }

    public void printField(){
        System.out.println("");
        super.printField();
        System.out.println("Health: " + healthPercent
                + " Lost Health last round: " + lostHealth
                + " Added Health by feed:" + addedHealth
                + " Health Status: " + healthStatus
        );
        System.out.println(" Current Age: " + currentAge
                + " Max Age: " + maxAge
                + " Price: " + initialPrice
                + " Breed quantity: " + breedQuantity);
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
    //This method is to let animal lost health between 10-30
    public void lostHealth(){
        //Produce the random number between 10 - 30
        //randomNum = min + (int)(Math.random() * (max-min+1));
        this.lostHealth  =  10 + (int)(Math.random() * (30-20+1));
        this.healthPercent = Math.max(this.healthPercent -this.lostHealth,0);
    }

    public void increaseAge(){
        this.currentAge++;
    }

    public void die(){
        if (this.healthPercent <= 0) {
            updateHealthStatus("Death");
            this.isAlive = false;
        }
        if (this.currentAge == this.maxAge){
            updateHealthStatus("Death");
            this.isAlive = false;
        }
    }

    public boolean isLiving(){
        //If the sick animal does not recuse,the health status will be "Death"
        if (healthStatus.equals("Death")){
            this.isAlive = false;
        }
        return this.isAlive;
    }

    public int getBreedQuantity(){
        return breedQuantity;
    }

    public int getHealthPercent(){return healthPercent;}

    public int getInitialPrice(){return initialPrice;}

    public static Animal breed(String gender){
        Scanner scanner = new Scanner(System.in);
        System.out.println("It is a new baby.What is the baby animals name:");
        var animalName = scanner.nextLine();
        return (new Cat(animalName,gender));
    }

    public void updateHealthStatus(String status){
        this.healthStatus = status;
    }

}
