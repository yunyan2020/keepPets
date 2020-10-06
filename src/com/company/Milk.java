package com.company;
import java.util.*;

public class Milk extends Food{
    public int price;
    public ArrayList<Animal> belongAnimals;

    public Milk(int price){
        super(price);
        this.belongAnimals = new ArrayList<Animal>();
    }
}
