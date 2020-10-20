package com.company;

import com.company.AnimalSubClasses.*;

import java.util.*;

public class Dialogs {

    public static String askAnimalType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What kind of animal would you want to choose?");
        System.out.println("1:Dog 2:Horse 3:Cat 4:Chicken 5:Rabbit");
        var choice = scanner.nextLine();
        while (!Arrays.asList("1", "2", "3", "4", "5").contains(choice)) {
            System.out.println("Input error,please input again");
            choice = scanner.nextLine();
        }
        var animalType = "";
        switch (choice) {
            case "1" -> animalType = "Dog";
            case "2" -> animalType = "Horse";
            case "3" -> animalType = "Cat";
            case "4" -> animalType = "Chicken";
            case "5" -> animalType = "Rabbit";
            default -> animalType = "";
        }
        return animalType;
    }

    public static String askFoodType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What kind of food would you want to choose?");
        System.out.println("1:Beef 2:Milk 3:Grass 4:Corn 5:Oat");
        var choice = scanner.nextLine();
        while (!Arrays.asList("1", "2", "3", "4", "5").contains(choice)) {
            System.out.println("Input error,please input again");
            choice = scanner.nextLine();
        }
        var foodType = "";
        switch (choice) {
            case "1" -> foodType = "Beef";
            case "2" -> foodType = "Milk";
            case "3" -> foodType = "Grass";
            case "4" -> foodType = "Corn";
            case "5" -> foodType = "Oat";
            default -> foodType = "";
        }
        return foodType;
    }

    public static void askRescueAnimal(Player player){
        Scanner scanner = new Scanner(System.in);
        var haveSickAnimal = false;
        if (player.animals.size() > 0 ){
            for (var animal: player.animals) {
                var animalType = animal.getClass().getSimpleName();
                var healthStatus = animal.healthStatus;
                var veterinaryCost = animal.veterinaryCost;
                if (healthStatus.equals("Sick")){
                    haveSickAnimal = true;
                    System.out.printf("Do you want to rescue your animal: %s(y/n)? It will cost: %d \n",
                            animal.getName(),animal.veterinaryCost);
                    var rescue = (scanner.next()).toUpperCase().equals("Y");
                    //judge the play have enough money to rescue
                    if (player.balance < veterinaryCost) {
                        System.out.println("You don´t have enough money to recuse it");
                        veterinaryCost = 0;
                        healthStatus = "Death";
                    }
                    if (rescue){
                        var rd = Math.random()>0.5?1:0;
                        healthStatus = (rd == 1?"Health":"Death");
                        player.reduceBalance(veterinaryCost);
                        if  (healthStatus.equals("Health")){
                            System.out.printf("Lucky!!!Your animal %s: has rescued. \n",
                                    animal.getName());
                        }
                        else {
                            System.out.printf("Unlucky!!!Your animal %s: has not rescued. \n",
                                    animal.getName());
                        }

                    }
                    else{
                        healthStatus = "Death";
                        System.out.printf("Unlucky!!!Your animal %s: has not rescued. \n",
                                animal.getName());
                    }
                    //update the animals health status
                    switch (animalType) {
                        case "Dog" -> ((Dog) animal).updateHealthStatus(healthStatus);
                        case "Cat" -> ((Cat) animal).updateHealthStatus(healthStatus);
                        case "Horse" -> ((Horse) animal).updateHealthStatus(healthStatus);
                        case "Chicken" -> ((Chicken) animal).updateHealthStatus(healthStatus);
                        case "Rabbit" -> ((Rabbit) animal).updateHealthStatus(healthStatus);
                        default -> {throw new IllegalStateException("Unexpected value: " + animalType);}
                    }
                }
            }
        }
    }

}


