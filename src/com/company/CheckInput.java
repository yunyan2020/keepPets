package com.company;

import java.util.ArrayList;
import java.util.List;

public class CheckInput {
    public static boolean isNumeric(char[] str) {
        if(str.length == 0){
            return false;
        }
        String string = String.valueOf(str);
        return string.matches("[\\+\\-]?\\d*(\\.\\d+)?([eE][\\+\\-]?\\d+)?");
    }

    public static boolean isAnimalNameExist(Player player,String animalName){
        List<String> animalNameList = new ArrayList<>();
        for(var animal:player.animals){
            animalNameList.add(animal.getName());
        }
        if (!animalNameList.contains(animalName)){
            System.out.printf("The name:%s does not exist \n",animalName );
            return false;
        }
       return true;
    }
}
