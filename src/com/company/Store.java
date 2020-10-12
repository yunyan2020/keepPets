package com.company;
import com.company.AnimalSubClasses.*;
import com.company.FoodSubClasses.*;

import java.util.*;

public class Store {

    static Scanner scanner = new Scanner(System.in);

    public static Animal buyAnimal(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What kind of animal would you want to buy?");
        System.out.println("1:Dog 2:Horse 3:Cat 4:Chicken 5:Rabbit");
        var choice = scanner.nextLine();
        while(!Arrays.asList("1","2","3","4","5").contains(choice)){
            System.out.println("Input error,please input again");
            choice = scanner.nextLine();
        }
        System.out.println("What is your animals name:");
        var animalName = scanner.nextLine();
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
        return switch (choice){
            case "1"    -> (new Dog(animalName,animalGender));
            case "2"    -> (new Horse(animalName,animalGender));
            case "3"    -> (new Cat(animalName,animalGender));
            case "4"    -> (new Chicken(animalName,animalGender));
            case "5"    -> (new Rabbit(animalName,animalGender));
            default     -> null;
        };
    }

    public static Food buyFood(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What kind of food would you want to buy?");
        System.out.println("1:Beef 2:Milk 3:Grass 4:Corn 5:Oat");
        var choice = scanner.nextLine();
        System.out.println("How many food would you want to buy(unit:KG):");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        var buyQuantity = scanner.nextInt();
        return switch (choice){
                case "1"    -> (new Beef("Beef",buyQuantity));
                case "2"    -> (new Milk("Milk",buyQuantity));
                case "3"    -> (new Grass("Grass",buyQuantity));
                case "4"    -> (new Corn("Corn",buyQuantity));
                case "5"    -> (new Oat("Oat",buyQuantity));
                default     -> null;
            };
        }

    }