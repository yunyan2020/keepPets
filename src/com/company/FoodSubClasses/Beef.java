package com.company.FoodSubClasses;
import com.company.Food;
import java.text.DecimalFormat;

public class Beef extends Food {

    public Beef(String foodType,int quantity){
        super(foodType);
        initialPrice = 200;
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
        //System.out.println("Quantity: " + Math.round(100*totalQuantity)/100 + " KG" );
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
