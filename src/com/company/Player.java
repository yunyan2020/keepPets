package com.company;
import java.util.*;


public class Player {
    public String name;
    public ArrayList<Animal> animals = new ArrayList<>();
    public ArrayList<Food> foods = new ArrayList<>();
    public int balance  = 5000;

    public Player(String name){
        this.name = name;
    }

    public void addAnimal(Animal animal){
        animals.add(animal);
    }

    public void addFood(Food food){
        foods.add(food);
    }

    public void removeFood(Food food){
        foods.remove(food);
    }

    public void updateBalance(int balance){
        this.balance = balance;
    }

    public void increaseBalance(int balance){
        this.balance += balance;
    }

    public void reduceBalance(int balance){
        this.balance -= balance;
    }
    public void removeAnimal(Animal animal){
        animals.remove(animal);
    }

}
