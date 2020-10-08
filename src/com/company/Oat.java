package com.company;

public class Oat extends Food{
    public int initialPrice = 200;
    public int totalQuantity = 0;
    public int buyQuantity = 0;

    public Oat(String foodType,int quantity){
        super(foodType);
        this.totalQuantity += quantity;
        this.buyQuantity = quantity;
    }
    public int checkBalance(int balance){
        return balance - initialPrice * buyQuantity;
    }

    public void printField(){
        super.printField();
        System.out.println("Quantity: " + totalQuantity);
    }

    public int getTotalQuantity(){
        return totalQuantity;
    }

    public void updateQuantity(int quantity){
        this.totalQuantity += quantity;
    }

}
