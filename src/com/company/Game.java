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
                while ((rounds < 5) ||  (rounds > 30)){
                   print("Please input rounds between 5 and 30");
                    while (!scanner.hasNextInt()) {
                        scanner.nextLine();
                    }
                    rounds = scanner.nextInt();
                }

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
        var countRounds = 1;
        do{
            for (int i = 0; i < countPlayers; i++) {
                //If the player has no money and has no animals then she/he is lost and exit the Game
                var exitPlayerName = "";
                if (players.get(i).name.equals(exitPlayerName)){
                    i++;
                    break;
                }
                if ((players.get(i).balance <= 0) && (players.get(i).animals.size() == 0 )){
                    print("You are lost!Because you have no money and no animals!!!");
                    exitPlayerName = players.get(i).name;
                    break;
                }
                System.out.printf("It is your turn Player%d :%s. It is the round:%d" + "\n",i+1,players.get(i).name,countRounds);
                if (players.get(i).animals.size() > 0 ){
                    //Let the animals lost their health
                    toLostHealth(players.get(i));
                    for (var animal: players.get(i).animals) {
                        animal.increaseAge();
                        //After the animals lost their health and the animals age is getting older.
                        // Judge the animal is alive or death.
                        animal.die();
                    }
                    //Some animals got sick
                    animalsGotSick(players.get(i));
                }

                //Call the method to show information to the player
                showInfo(players.get(i));

                //If there are some sick animals.Ask the player if she/he will rescue them
                //And show the information to the user again.
                Dialogs.askRescueAnimal(players.get(i));

                //Remove all the died animals
                var removeDiedAnimal = false;
                for(var j = players.get(i).animals.size()-1;j >= 0;j--){
                    var diedAnimal = players.get(i).animals.get(j);
                    if (!diedAnimal.isAlive) {
                        players.get(i).removeAnimal(diedAnimal);
                        removeDiedAnimal = true;
                    }
                }
                if (removeDiedAnimal) {showInfo(players.get(i));}

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
                        toBreedAnimals(players.get(i));
                        break;
                    case 5:
                        toSellAnimals(players.get(i));
                        break;
                    default:
                        break;
                }

            }
            countRounds++;
        } while (countRounds <= rounds);
        //Sell every players all animals and compare who has the most money
        //the player who has the most money win the game
        print("You have played all rounds");
        print("Now is time to sell all animals for everyone and print the winner");
        for (var player:players) {
            toSellAllAnimals(player);
        }
        players.sort((Player a, Player b) -> { return a.balance > b.balance ? -1 : 1; });
        var winnerName = players.get(0).name;
        var winnerBalance    = players.get(0).balance;
        System.out.printf("Congratulation!!! %s You won!!! You have money:%d \n",winnerName,winnerBalance);
       // System.out.printf("Congratulation!!! %sYou won!!! You have money:d%",players.get(0).name,players.get(0).balance);
        print("Game over");
        System.exit(0);
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
        System.out.printf("You have money: %d  \n",player.balance);
        if (player.animals.size() > 0 ){
            print("You have owned following animals");
            System.out.println("-".repeat(110));
            for(var animal: player.animals){
                var animalType  = animal.getClass().getSimpleName();
                System.out.print(animalType + " ");
                animal.printField();
                if (!animal.isLiving()){
                    System.out.printf("Your animal:%s has died!!! \n",animal.getName());
                }
            }
            System.out.println("-".repeat(110));
        }
        if (player.foods.size() > 0 ){
            print("You have owned following foods");
            System.out.println("-".repeat(110));
            for(var food: player.foods) {
                food.printField();
            }
            System.out.println("-".repeat(110));
        }

    }

    //In this method: the player can buy animals from Store.
    private void toBuyAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var buyAnimal = true;
        do{
            Animal myNewAnimal = Store.buyAnimal(player);
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
            showInfo(player);
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
            showInfo(player);
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
                print("You don't have any foods to feed");
                feedAnimal = false;
                return;
            }
            //ask the user what kind of animal does he/she want to feed
            var animalType =  Dialogs.askAnimalType();
            //ask the user what kind of food does he/she want to feed
            print("Please enter your food type to feed(only input number)");
            for(var i = 0; i <player.foods.size(); i++) {
                System.out.printf(i+1 + "."+ player.foods.get(i).getFoodType()+ " ");
            }
            System.out.println();
            var foodTypeNumber = scanner.next();
            var foodType = "";
            //find the food type according to the user´s input number
            for(var i = 0; i <player.foods.size(); i++) {
                foodType = foodTypeNumber.equals((i+1)+"")?foodType =player.foods.get(i).getFoodType():"";
                if (!foodType.equals("")){
                    break;
                }
            }
            Food foodToFeed = null;
            for(var food: player.foods) {
                if (food.getFoodType().toUpperCase().equals(foodType.toUpperCase())){
                    foodToFeed = food;
                }
            }
            if (foodToFeed == null){
                print("You don´t have such food to feed.");
                System.out.println("Would you like to continue to feed other animal(y/n)");
                feedAnimal = (scanner.next()).toUpperCase().equals("Y");
                continue;
            }
            print("How many food do you want to feed?");
            var quantity = scanner.next();
            // Convert the string to an array of chars
            var arrayOfCharsQuantity = quantity.toCharArray();
            double feedQuantity = (CheckInput.isNumeric(arrayOfCharsQuantity)? Double.parseDouble(quantity):0);

            //Check if the quantity is more than the total Quantity.
            if (foodToFeed.getTotalQuantity() < feedQuantity){
                print("You don't have so many food.");
                System.out.println("Would you like to continue to feed other animal(y/n)");
                feedAnimal = (scanner.next()).toUpperCase().equals("Y");
                continue;
            }

            if (feedQuantity <= 0){
                print("Please input the feed quantity more than 0.0");
                System.out.println("Would you like to continue to feed other animal(y/n)");
                feedAnimal = (scanner.next()).toUpperCase().equals("Y");
                continue;
            }
            //ask the user the animal´s name to feed
            print("Please enter your animal´s name to feed");
            var animalNameToFeed = scanner.next();
            //Check if the animal name not exist in the players animal list
            if  (CheckInput.isAnimalNameNotExist(player,animalType,animalNameToFeed)){
                continue;
            }

            Animal animalToFeed = null;
            for(var animal: player.animals){
                if ((animal.getName().equals(animalNameToFeed)) && (animal.getClass().getSimpleName().equals(animalType))) {
                    animalToFeed = animal;
                }
            }
            if (!isFoodSuitable(animalToFeed,animalType,foodType)){
                print("The food that you choose can not feed your animal that want to feed.");
                System.out.println("Would you like to continue to feed other animal(y/n)");
                feedAnimal = (scanner.next()).toUpperCase().equals("Y");
                continue;
            }

            //Star to eat food
            switch (animalType) {
                case "Dog" -> ((Dog) animalToFeed).eat(foodToFeed, feedQuantity);
                case "Cat" -> ((Cat) animalToFeed).eat(foodToFeed, feedQuantity);
                case "Horse" -> ((Horse) animalToFeed).eat(foodToFeed, feedQuantity);
                case "Chicken" -> ((Chicken) animalToFeed).eat(foodToFeed, feedQuantity);
                case "Rabbit" -> ((Rabbit) animalToFeed).eat(foodToFeed, feedQuantity);
                default -> {throw new IllegalStateException("Unexpected value: " + animalType);}
            }
            //After the animal eating food,minus the foods quantity
            var totalQuantity = 0.0 ;
            switch(foodType){
                case "Beef" -> {((Beef) foodToFeed).minusQuantity(feedQuantity);
                               totalQuantity = ((Beef) foodToFeed).getTotalQuantity();}
                case "Milk" -> {((Milk)foodToFeed).minusQuantity(feedQuantity);
                                 totalQuantity = ((Milk) foodToFeed).getTotalQuantity();}
                case "Grass" -> {((Grass) foodToFeed).minusQuantity(feedQuantity);
                                totalQuantity = ((Grass) foodToFeed).getTotalQuantity();}
                case "Corn" -> {((Corn) foodToFeed).minusQuantity(feedQuantity);
                                 totalQuantity = ((Corn) foodToFeed).getTotalQuantity();}
                case "Oat"  -> {((Oat) foodToFeed).minusQuantity(feedQuantity);
                                 totalQuantity = ((Oat) foodToFeed).getTotalQuantity();}
                default -> {throw new IllegalStateException("Unexpected value: " + foodType);}
            }
            //If the food quantity is 0 then remove from food list.
            if (totalQuantity == 0) {
                player.removeFood(foodToFeed);
            }
            showInfo(player);
            System.out.println("Would you like to feed other animal(y/n)");
            feedAnimal = (scanner.next()).toUpperCase().equals("Y");
        }while (feedAnimal);
    }
    private void toBreedAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var breedAnimal = true;
        do{
            if (player.animals.size() == 0 ) {
                print("You don't have any animals to feed");
                breedAnimal = false;
                return;
            }
            //Ask the user what kind of animal does she/he want to breed
            var animalType = Dialogs.askAnimalType();

            print("Please enter one of your animals name to breed");
            var animalName1 = scanner.next();
            //Check if the animal name not exist in the players animal list
            if  (CheckInput.isAnimalNameNotExist(player,animalType,animalName1)){
                continue;
            }

            print("Please enter another of your animals name to breed");
            var animalName2 = scanner.next();
            //Check if the animal name not exist in the players animal list
            if  (CheckInput.isAnimalNameNotExist(player,animalType,animalName2)){
                continue;
            }

            //Check if the animal is suitable for breed
            var canBeBreed = areAnimalSuitableToBreed(player,animalType,animalName1,animalName2);
            if (!canBeBreed){ continue;}
            //get quantity of the animal that can breed
            var breedQuantity = 0;
            for(var animal:player.animals){
                if ((animal.getName().equals(animalName1)) && (animal.getClass().getSimpleName().equals(animalType))) {
                    breedQuantity = animal.getBreedQuantity();
                }
            }
            Animal myNewAnimal = null;
            var str = breedQuantity>1?"s":"";
            System.out.printf("Congratulations！！！%d baby animal%s will be born. \n",breedQuantity,str);
            for(var k = 0; k<breedQuantity; k++){
                int rd = Math.random()>0.5?1:0;
                String gender = (rd == 1? "MALE":"FEMALE");
                switch (animalType) {
                    case "Dog" ->  myNewAnimal = Dog.breed(gender);
                    case "Cat" ->  myNewAnimal = Cat.breed(gender);
                    case "Horse" ->  myNewAnimal = Horse.breed(gender);
                    case "Chicken" -> myNewAnimal = Chicken.breed(gender);
                    case "Rabbit" -> myNewAnimal = Rabbit.breed(gender);
                }
                player.addAnimal(myNewAnimal);
            }
            showInfo(player);
            System.out.println("Would you like to breed other animal(y/n)");
            breedAnimal = (scanner.next()).toUpperCase().equals("Y");
        }while (breedAnimal);
    }

    private void toSellAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var sellAnimal = true;
        do{
            if (player.animals.size() == 0 ) {
                print("You don't have any animals to sell");
                sellAnimal = false;
                return;
            }
            //Ask the user what kind of animal does she/he want to sell
            var animalType = Dialogs.askAnimalType();

            print("Please enter one of your animals name to sell");
            var animalNameToSell = scanner.next();
            //Check if the animal name not exist in the players animal list
            if  (CheckInput.isAnimalNameNotExist(player,animalType,animalNameToSell)){
                System.out.println("Would you like to sell other animal(y/n)");
                sellAnimal = (scanner.next()).toUpperCase().equals("Y");
                return;
            }
            Animal animalToSell = null;
            for(var animal:player.animals){
                if ((animal.getName().equals(animalNameToSell)) && (animal.getClass().getSimpleName().equals(animalType))) {
                    animalToSell = animal;
                }
            }
            var sellAmount = getAmountOfSellAnimal(animalToSell,animalType);
            player.increaseBalance(sellAmount);
            player.removeAnimal(animalToSell);
            showInfo(player);

            System.out.println("Would you like to sell other animal(y/n)");
            sellAnimal = (scanner.next()).toUpperCase().equals("Y");
        }while (sellAnimal);
    }

    private void  toSellAllAnimals(Player player){
        for(var animal:player.animals){
            var sellAmount = getAmountOfSellAnimal(animal,animal.getClass().getSimpleName());
            player.increaseBalance(sellAmount);
        }
        System.out.printf("%s You have money: %d  \n",player.name,player.balance);
    }

    private void animalsGotSick(Player player){
        for(var animal:player.animals){
            var animalType = animal.getClass().getSimpleName();
            if (animal.healthStatus.equals("Health")){
                // Create random number between 1-5
                int rndNum = new Random().nextInt(5) + 1;
                var animalHealthStatus=  (rndNum == 1? "Sick":"Health");
                if (animalHealthStatus.equals("Sick")){
                   switch (animalType){
                       case "Dog" ->  ((Dog) animal).updateHealthStatus(animalHealthStatus);
                       case "Cat" ->  ((Cat) animal).updateHealthStatus(animalHealthStatus);
                       case "Horse" -> ((Horse) animal).updateHealthStatus(animalHealthStatus);
                       case "Chicken" -> ((Chicken) animal).updateHealthStatus(animalHealthStatus);
                       case "Rabbit" -> ((Rabbit) animal).updateHealthStatus(animalHealthStatus);
                   }
               }
            }
        }
    }

    /**
     * This  method check if the food suitable for animal
     * @param animal
     * @param animalClassName
     * @param foodType
     * @return
     */
    private boolean isFoodSuitable(Animal animal,String animalClassName,String foodType) {
       boolean b = switch (animalClassName) {
            case "Dog" -> Arrays.asList(((Dog) animal).editableFood).contains(foodType);
            case "Cat" -> Arrays.asList(((Cat) animal).editableFood).contains(foodType);
            case "Horse" -> Arrays.asList(((Horse) animal).editableFood).contains(foodType);
            case "Chicken" -> Arrays.asList(((Chicken) animal).editableFood).contains(foodType);
            case "Rabbit" -> Arrays.asList(((Rabbit) animal).editableFood).contains(foodType);
            default -> false;
        };
        return b;
    }

    private boolean areAnimalSuitableToBreed(Player player, String animalType,String animalName1, String animalName2){
        String animalGender1 = "";
        String animalGender2 = "";
        var canBeBreed = false;
        for (var animal : player.animals) {
            //Check if the animal is suitable for breed
            if ((animal.getName().equals(animalName1)) && (animal.getClass().getSimpleName().equals(animalType))) {
                animalGender1 = animal.getGender();
            }
            if ((animal.getName().equals(animalName2)) && (animal.getClass().getSimpleName().equals(animalType))) {
                animalGender2 = animal.getGender();
            }
        }

        if (animalGender1.equals("MALE") && animalGender2.equals("FEMALE")) {
            canBeBreed = true;
        } else if (animalGender1.equals("FEMALE") && animalGender2.equals("MALE")) {
            canBeBreed = true;
        } else {
            System.out.println("These two animals can not be breed because their gender." +
                    " Only one is Male other is Female so that they can be breed");
            canBeBreed = false;
        }
        return canBeBreed;
    }

    private int getAmountOfSellAnimal(Animal animalToSell, String animalType) {
        var sellAmount = 0;
        var price = 0;
        var healthValue = 0;
        switch (animalType) {
            case "Dog" -> {  price =  ((Dog) animalToSell).getInitialPrice();
                healthValue =  ((Dog) animalToSell).getHealthPercent();
                sellAmount = (int)Math.round(price * healthValue /100);
            }
            case "Cat" -> {  price =  ((Cat) animalToSell).getInitialPrice();
                healthValue =  ((Cat) animalToSell).getHealthPercent();
                sellAmount = (int)Math.round(price * healthValue /100);
            }
            case "Horse" -> { price =  ((Horse) animalToSell).getInitialPrice();
                healthValue =  ((Horse) animalToSell).getHealthPercent();
                sellAmount = (int)Math.round(price * healthValue /100);
            }
            case "Chicken" -> { price =  ((Chicken) animalToSell).getInitialPrice();
                healthValue =  ((Chicken) animalToSell).getHealthPercent();
                sellAmount = (int)Math.round(price * healthValue /100);
            }
            case "Rabbit" -> { price =  ((Rabbit) animalToSell).getInitialPrice();
                healthValue =  ((Rabbit) animalToSell).getHealthPercent();
                sellAmount = (int)Math.round(price * healthValue /100);
            }
        }
        return sellAmount;
    }
}
