package com.company;

public abstract class Food {
    private String FoodType;
    protected double totalQuantity = 0;
    protected int initialPrice = 0;

public Food(String FoodType){
    this.FoodType = FoodType;
}

public String getFoodType(){
    return this.FoodType;
}
public void printField(){
    System.out.print("Food type: " + FoodType + " ");
}
public double getTotalQuantity(){return totalQuantity;}
public void updateQuantity(double quantity){}
public void minusQuantity(double quantity){}

}
