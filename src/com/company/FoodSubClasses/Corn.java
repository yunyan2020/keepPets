package com.company.FoodSubClasses;
import com.company.Food;
import java.text.DecimalFormat;

public class Corn extends Food {
    public int buyQuantity ;

    public Corn(String foodType,int quantity){
        super(foodType);
        initialPrice = 5;
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

}
