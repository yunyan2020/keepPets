package com.company;

public abstract class Food {
    private String FoodType;
    protected double totalQuantity = 0;
    protected int initialPrice = 0;
    protected int buyQuantity = 0 ;


public Food(String FoodType){
    this.FoodType = FoodType;
}

public abstract int checkBalance(int balance);

public String getFoodType(){
    return this.FoodType;
}

public void printField(){
    System.out.print("Food type: " + FoodType + " ");
}

public double getTotalQuantity(){return this.totalQuantity;}

public void updateQuantity(double quantity){}

public void minusQuantity(double quantity){}

public int getInitialPrice(){return  this.initialPrice;  }

}
