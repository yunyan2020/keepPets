package com.company;
import com.company.AnimalSubClasses.*;
import com.company.FoodSubClasses.*;

import java.util.*;

public class Game {
    public Game(){
        startMenu();
    }

    private void startMenu(){
        print("1. Start a new game");
        print("2. Load a game from a file");
        Scanner scanner = new Scanner(System.in);
        var input = scanner.nextLine();
        var checkRange = "12";
        do{
            var choice = checkRange.indexOf(input);
            if (choice < 0) { continue; }
            choice++;
            if (choice == 1) {
               //prompt the user how many player do they want to play
                print("How many players(1-4):");
                while (!scanner.hasNextInt()) {
                    scanner.nextLine();
                }
                var countPlayers = scanner.nextInt();

                //prompt the user how many rounds do they want to play
                print("How many rounds would you want to play(5-30):");
                while (!scanner.hasNextInt()) {
                    scanner.nextLine();
                }
                var rounds = scanner.nextInt();

                //Call main Menu
                mainMenu(countPlayers,rounds);
            }
            else if (choice == 2){
                //write your file name
                print("Please enter your file name");
            }
        }while (true);
    }

    public void mainMenu(int countPlayers,int rounds ) {
        var players = new ArrayList<Player>();
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= countPlayers; i++) {
            System.out.printf("Please enter your name player%d:", i);
            var playersName = scanner.nextLine();
            players.add(new Player(playersName.toUpperCase()));
        }
        var countRounds = 5;
        do{
            for (int i = 0; i < countPlayers; i++) {
                System.out.printf("It is your turn Player%d :%s",i+1,players.get(i).name + "\n");
                //Let the animals lost their heath
                toLostHealth(players.get(i));

                //Call the method to give information to the player
                showInfo(players.get(i));

                //print the game´s main menu
                print("Please enter your choice:");
                print("-------------------------");
                print("1:Buy the animal");
                print("2:Buy the food");
                print("3:Feed the animal");
                print("4:Breed the new animal");
                print("5:Sale the animal");
                print("-------------------------");
                while (!scanner.hasNextInt()) {
                    scanner.nextLine();
                }
                var functionNumber = scanner.nextInt();

                switch (functionNumber){
                    case 1:
                        toBuyAnimals(players.get(i));
                        break;
                    case 2:
                        toBuyFoods(players.get(i));
                        break;
                    case 3:
                        toFeedAnimals(players.get(i));
                        break;
                    case 4:
                    case 5:
                    default:
                        break;
                }

            }
            countRounds++;
        } while (countRounds >= rounds);

    }

    private void print(String x){
        // print a string if it is not empty
        if(!x.equals("")){ System.out.println(x); }
    }


    //This method to let the animals lost their health.
    private void toLostHealth(Player player){
        for(var animal: player.animals) {
            var className = animal.getClass().getSimpleName();
            switch(className){
                case "Dog"      -> ((Dog) animal).lostHealth();
                case "Cat"      -> ((Cat) animal).lostHealth();
                case "Horse"    -> ((Horse) animal).lostHealth();
                case "Chicken"  -> ((Chicken) animal).lostHealth();
                case "Rabbit"   -> ((Rabbit) animal).lostHealth();
            }
        }
    }

    //This method to show players information about the player owned.
    private void showInfo(Player player){
        System.out.printf("You have money: %d ",player.balance);
        System.out.println("");
        if (player.animals.size() > 0 ){
            print("You have owned following animals");
            for(var animal: player.animals){
                System.out.print(animal.getClass().getSimpleName() + " ");
                animal.printField();
            }
        }
        if (player.foods.size() > 0 ){
            print("You have owned following foods");
            for(var food: player.foods) {
                food.printField();
            }
        }
    }

    //In this method: the player can buy animals from Store.
    private void toBuyAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var buyAnimal = true;
        do{
            Animal myNewAnimal = Store.buyAnimal();
            //Check the players rest balance if it can buy animal
            var balance = player.balance;
            var className = myNewAnimal.getClass().getSimpleName();
            int restBalance = -1 ;
            switch(className){
                case "Dog" -> restBalance=((Dog) myNewAnimal).checkBalance(balance);
                case "Cat" -> restBalance=((Cat) myNewAnimal).checkBalance(balance);
                case "Horse" -> restBalance=((Horse) myNewAnimal).checkBalance(balance);
                case "Chicken" -> restBalance=((Chicken) myNewAnimal).checkBalance(balance);
                case "Rabbit" -> restBalance=((Rabbit) myNewAnimal).checkBalance(balance);
            }
            if (restBalance >=0){
                player.addAnimal(myNewAnimal);
                player.updateBalance(restBalance);
            }
            else print("Sorry!You don't have enough money to buy this animal");
            System.out.println("Would you like to buy other animal(y/n)");
            buyAnimal = (scanner.next()).toUpperCase().equals("Y");
        }while (buyAnimal);
    }

    /**
     * In this method: the player can buy foods from store.
     * @param player
     */
    private void toBuyFoods(Player player){
        Scanner scanner = new Scanner(System.in);
        var buyFood = true;
        do{
            Food myNewFood = Store.buyFood();
            //Check the players rest balance if it can buy food
            var balance = player.balance;
            var className = myNewFood.getClass().getSimpleName();
            int restBalance = -1 ;
            switch(className){
                case "Beef" -> restBalance=((Beef) myNewFood).checkBalance(balance);
                case "Milk" -> restBalance=((Milk) myNewFood).checkBalance(balance);
                case "Grass" -> restBalance=((Grass) myNewFood).checkBalance(balance);
                case "Corn" -> restBalance=((Corn) myNewFood).checkBalance(balance);
                case "Oat"  -> restBalance=((Oat) myNewFood).checkBalance(balance);
            }
            var findSameFood = false;
            Food myRemoveFood = null;
            var foodTotalQuantity = 0.0;
            if (restBalance >=0){
                for(var food: player.foods) {
                    if (food.getFoodType().equals(className) ){
                        findSameFood = true;
                        myRemoveFood = food;
                        foodTotalQuantity = food.getTotalQuantity();
                    }
                }
                player.addFood(myNewFood);
                if (findSameFood){
                    player.removeFood(myRemoveFood);
                    for(var food: player.foods) {
                        if (food.getFoodType().equals(className) )
                        {food.updateQuantity(foodTotalQuantity);
                        }
                    }
                }
                player.updateBalance(restBalance);
            }
            else print("Sorry!You don't have enough money to buy this food");
            System.out.println("Would you like to buy other food(y/n)");
            buyFood = (scanner.next()).toUpperCase().equals("Y");
        }while (buyFood);
    }

    private void toFeedAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var feedAnimal = true;
        do{
            if (player.animals.size() == 0 ) {
                print("You don't have any animals to feed");
                feedAnimal = false;
                return;
            }

            if (player.foods.size() == 0 ) {
                print("You don't have any food to feed");
                feedAnimal = false;
                return;
            }

            print("Please enter your animals name to feed");
            var name = scanner.next();
            print("Please enter your food type to feed");
            var foodType = scanner.next();
            print("How many food do you want to feed?");
            var quantity = scanner.next();
            // Convert the string to an array of chars
            var arrayOfCharsQuantity = quantity.toCharArray();
            double feedQuantity = (CheckInput.isNumeric(arrayOfCharsQuantity)? Double.parseDouble(quantity):0);
            Food foodToFeed = null;
            for(var food: player.foods) {
               if (food.getFoodType().toUpperCase().equals(foodType.toUpperCase())){
                   foodToFeed = food;
               }
            }
            if (foodToFeed == null){
                print("There is no such food to feed");
                return;
            }
            //Check if the quantity is more than the total Quantity.
            if (foodToFeed.getTotalQuantity() < feedQuantity){
                print("You don't have som many food");
                continue;
            }

            if (feedQuantity <= 0){
                print("Please input the feed quantity more than 0");
            }
            if (foodToFeed == null){
                print("You have enter the error food!");
                continue;
            }
            for(var animal: player.animals){
                var className = animal.getClass().getSimpleName();
                //Check if the food is suitable for animal
                if (!isFoodSuitable(animal,className,foodType)){
                    print("The food that you choose can not feed the animal that you want to feed");
                    continue;
                }
                //Begin to eat food
                if (animal.getName().toUpperCase().equals(name.toUpperCase())) {
                    switch (className) {
                        case "Dog" -> ((Dog) animal).eat(foodToFeed, feedQuantity);
                        case "Cat" -> ((Cat) animal).eat(foodToFeed, feedQuantity);
                        case "Horse" -> ((Horse) animal).eat(foodToFeed, feedQuantity);
                        case "Chicken" -> ((Chicken) animal).eat(foodToFeed, feedQuantity);
                        case "Rabbit" -> ((Rabbit) animal).eat(foodToFeed, feedQuantity);
                    }
                }
                else {
                    print("You have enter the error food");
                    continue;
                    }
                }
            //After the animal eating food,minus quantity that the animal have eaten
            var className = foodToFeed.getClass().getSimpleName();
            switch(className){
                case "Beef" -> ((Beef) foodToFeed).minusQuantity(feedQuantity);
                case "Milk" -> ((Milk)foodToFeed).minusQuantity(feedQuantity);
                case "Grass" -> ((Grass) foodToFeed).minusQuantity(feedQuantity);
                case "Corn" -> ((Corn) foodToFeed).minusQuantity(feedQuantity);
                case "Oat"  -> ((Oat) foodToFeed).minusQuantity(feedQuantity);
            }

            System.out.println("Would you like to feed other animal(y/n)");
            feedAnimal = (scanner.next()).toUpperCase().equals("Y");
        }while (feedAnimal);
    }

    //
    private boolean isFoodSuitable(Animal animal,String className,String foodType) {
       boolean b = switch (className) {
            case "Dog" -> Arrays.asList(((Dog) animal).editableFood).contains(foodType);
            case "Cat" -> Arrays.asList(((Cat) animal).editableFood).contains(foodType);
            case "Horse" -> Arrays.asList(((Horse) animal).editableFood).contains(foodType);
            case "Chicken" -> Arrays.asList(((Chicken) animal).editableFood).contains(foodType);
            case "Rabbit" -> Arrays.asList(((Rabbit) animal).editableFood).contains(foodType);
            default -> false;
        };
        return b;
    }
}
