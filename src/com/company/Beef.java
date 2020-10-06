package com.company;
import java.util.*;

public class Beef extends Food{
    public int price;
    public ArrayList<Animal> belongAnimals;

    public Beef(int price){
       super(price);
       this.belongAnimals = new ArrayList<Animal>();
    }
}
