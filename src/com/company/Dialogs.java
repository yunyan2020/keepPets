package com.company;

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
        ;
        return animalType;
    }
}


