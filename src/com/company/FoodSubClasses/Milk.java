package com.company.FoodSubClasses;

import com.company.Food;

public class Milk extends Food {
    public int buyQuantity ;

    public Milk(String foodType,int quantity){
        super(foodType);
        initialPrice = 15;
        this.totalQuantity += quantity;
        this.buyQuantity = quantity;
    }
    public int checkBalance(int balance){
        return balance - initialPrice * buyQuantity;
    }

    public void printField(){
        super.printField();
        System.out.println("Quantity: " + Math.round(100*totalQuantity)/100 + " KG" );
    }

    public double getTotalQuantity(){
        return totalQuantity;
    }

    public void updateQuantity(double quantity){
        this.totalQuantity += quantity;
    }
    public void minusQuantity(double quantity){
        this.totalQuantity -= quantity;
    }
    public int getInitialPrice(){
        return this.initialPrice;
    }
}
