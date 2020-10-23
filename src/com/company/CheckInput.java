package com.company;

import java.util.ArrayList;
import java.util.List;

public class CheckInput {
    //to check if the input string is number
    public static boolean isNumeric(char[] str) {
        if(str.length == 0){
            return false;
        }
        String string = String.valueOf(str);
        return string.matches("[\\+\\-]?\\d*(\\.\\d+)?([eE][\\+\\-]?\\d+)?");
    }

    public static boolean isAnimalNameExist(Player player,String animalType,String animalName){
        List<String> animalNameList = new ArrayList<>();
        for(var animal:player.animals){
            if (animal.getClass().getSimpleName().equals(animalType)){
                animalNameList.add(animal.getName());
            }
        }
        if (animalNameList.contains(animalName)){
            System.out.printf("The name:%s has existed \n",animalName );
            return true;
        }
       return false;
    }

    public static boolean isAnimalNameNotExist(Player player,String animalType,String animalName){
        List<String> animalNameList = new ArrayList<>();
        for(var animal:player.animals){
            if (animal.getClass().getSimpleName().equals(animalType)){
                animalNameList.add(animal.getName());
            }
        }

        if (!animalNameList.contains(animalName)){
            System.out.printf("The name:%s does not exist \n",animalName );
            return true;
        }
        return false;
    }
}
