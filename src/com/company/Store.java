package com.company;
import com.company.AnimalSubClasses.*;
import com.company.FoodSubClasses.*;

import java.util.*;

public class Store {

    static Scanner scanner = new Scanner(System.in);

    public static Animal buyAnimal(Player player){
        //Ask the user input Animal Type
        var animalType = Dialogs.askAnimalType();
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

    public static Food buyFood(){
        Scanner scanner = new Scanner(System.in);
        //Ask the user input Animal Type
        var foodType = Dialogs.askFoodType();
        System.out.println("How many food would you want to buy(unit:KG):");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        var buyQuantity = scanner.nextInt();
        return switch (foodType){
                case "Beef"    -> (new Beef("Beef",buyQuantity));
                case "Milk"    -> (new Milk("Milk",buyQuantity));
                case "Grass"    -> (new Grass("Grass",buyQuantity));
                case "Corn"    -> (new Corn("Corn",buyQuantity));
                case "Oat"    -> (new Oat("Oat",buyQuantity));
                default     -> null;
            };
    }


}