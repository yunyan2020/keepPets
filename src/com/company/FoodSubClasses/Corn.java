package com.company.FoodSubClasses;

import com.company.Food;

public class Corn extends Food {
    public int buyQuantity ;

    public Corn(String foodType,int quantity){
        super(foodType);
        initialPrice = 150;
        this.totalQuantity += quantity;
        this.buyQuantity = quantity;
    }
    public int checkBalance(int balance){
        return balance - initialPrice * buyQuantity;
    }

    public void printField(){
        super.printField();
        System.out.println("Quantity: " + totalQuantity + " KG");
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


}
