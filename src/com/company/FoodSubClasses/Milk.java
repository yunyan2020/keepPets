package com.company.FoodSubClasses;
import com.company.Food;
import java.text.DecimalFormat;

public class Milk extends Food {
    public int buyQuantity ;

    public Milk(String foodType,int quantity){
        super(foodType);
        initialPrice = 15;
        this.totalQuantity = quantity;
        this.buyQuantity = quantity;
    }
    public int checkBalance(int balance){
        return balance - initialPrice * buyQuantity;
    }

    public void printField(){
        super.printField();
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("Quantity: " + df.format(totalQuantity) + " KG");
    }

    public double getTotalQuantity(){
        return totalQuantity;
    }

    public void updateQuantity(double quantity){
        this.totalQuantity = quantity;
    }
    public void minusQuantity(double quantity){
        this.totalQuantity -= quantity;
    }
    public int getInitialPrice(){
        return this.initialPrice;
    }
    public int getBuyQuantity(){
        return this.buyQuantity;
    }
}
