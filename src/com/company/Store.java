package com.company;
import com.company.AnimalSubClasses.*;
import com.company.FoodSubClasses.*;
import java.util.*;

public class Store {

    static Scanner scanner = new Scanner(System.in);

    public static Animal buyAnimal(Player player){
        //Ask the user input Animal Type
        var animalType = Dialogs.askAnimalType("buy");
        System.out.println("What is your animals name:");
        var animalName = scanner.nextLine();
        //Check if the animal name exist in the players animal list
        var nameExist = CheckInput.isAnimalNameExist(player,animalType,animalName);
        while (nameExist){
            System.out.println("What is your animals name:");
            animalName = scanner.nextLine();
            nameExist = CheckInput.isAnimalNameExist(player,animalType,animalName);

        }

        System.out.println("What is your animals gender(M/F):");
        var animalGender = scanner.nextLine();
        if (animalGender.toUpperCase().equals("M")){
            animalGender = "MALE";
        }
        else if (animalGender.toUpperCase().equals("F")){
            animalGender = "FEMALE";
        }
        else {
            animalGender = "NONBINARY";
        }
        return switch (animalType){
            case "Dog"      -> (new Dog(animalName,animalGender));
            case "Horse"    -> (new Horse(animalName,animalGender));
            case "Cat"      -> (new Cat(animalName,animalGender));
            case "Chicken"  -> (new Chicken(animalName,animalGender));
            case "Rabbit"   -> (new Rabbit(animalName,animalGender));
            default     -> null;
        };
    }

    public static void buyFood(Player player){
        Scanner scanner = new Scanner(System.in);
        //Ask the user input Animal Type
        var foodType = Dialogs.askFoodType();
        System.out.println("How many food would you want to buy(unit:KG):");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        var buyQuantity = scanner.nextInt();
        Food myNewFood = null;
        switch (foodType){
                case "Beef"    -> myNewFood =(new Beef("Beef",buyQuantity));
                case "Milk"    -> myNewFood =(new Milk("Milk",buyQuantity));
                case "Grass"   -> myNewFood =(new Grass("Grass",buyQuantity));
                case "Corn"    -> myNewFood = (new Corn("Corn",buyQuantity));
                case "Oat"     -> myNewFood =(new Oat("Oat",buyQuantity));
            }
        //Check the players rest balance if it can buy food
        var balance = player.balance;
        if (balance == 0){
            System.out.println("You have no money!You can not buy any food.");
            return;
        }
        int restBalance = -1 ;
        //check players balance if he/she can buy food
        restBalance = myNewFood.checkBalance(balance);
        var findSameFood = false;
        Food sameFood = null;
        var foodTotalQuantity = 0.0;
        if (restBalance >=0){
            for(var food: player.foods) {
                if (food.getFoodType().equals(foodType) ){
                    findSameFood = true;
                    sameFood = food;
                    foodTotalQuantity = food.getTotalQuantity();
                }
            }

            if (findSameFood){
                foodTotalQuantity += buyQuantity;
                sameFood.updateQuantity(foodTotalQuantity);
            }
            else{
                player.addFood(myNewFood);
            }
            player.updateBalance(restBalance);
        }
        else {
            System.out.println("Sorry!You don't have enough money to buy this food");
            System.out.printf("You only have money: %d kr. These food need money: %d kr \n",balance,myNewFood.getInitialPrice() * buyQuantity);
            Dialogs.delaySeconds(3);
        }
    }
}