package com.company;
import com.company.AnimalSubClasses.*;
import com.company.FoodSubClasses.*;
import java.nio.file.*; // Files and Paths classes
import java.util.*; // ArrayList etc

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
                //Before call main Menu Serializer the whole game
                /*
                var filePath = Paths.get("keepPetsGame.ser");
                if(!Files.exists(filePath)){
                    Serializer.serialize(filePath + "", this);
                    System.out.println("Serialized the game!");
                }
                */
                //Call main Menu
                mainMenu(countPlayers,rounds);
            }
            else if (choice == 2){
                // Deserialization
                /*
                var filePath = Paths.get("keepPetsGame.ser");
                var game = (Game) Serializer.deserialize(filePath + "");
                */
                print("Sorry!!! This function have not finished");
                return;
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
        var lostPlayers = new ArrayList<Player>();
        do{
            clear();
            for (int i = 0; i < countPlayers; i++) {
                //If the player has no money and has no animals then she/he is lost and exit the Game
                if (lostPlayers.size() == countPlayers ){
                    print("All players has no money and animals. Game Over!");
                    System.exit(0);
                }
                if (!lostPlayers.contains(players.get(i)) && (players.get(i).balance <= 0) && (players.get(i).animals.size() == 0 )){
                    print("You are lost! Because you have no money and no animals!!!");
                    lostPlayers.add(players.get(i));
                }

                if (lostPlayers.contains(players.get(i))){
                    continue;
                }

                System.out.println("*".repeat(110));
                System.out.printf("It is your turn Player%d :%s. It is the round:%d" + "\n",i+1,players.get(i).name,countRounds);
                if (players.get(i).animals.size() > 0 ){
                    for (var animal: players.get(i).animals) {
                        //Let the animals lost their health
                        animal.lostHealth();
                        animal.increaseAge();
                        //After the animals lost their health and the animals age is getting older.
                        // Judge the animal is alive or death.
                        animal.die();
                        if ((animal.healthStatus.equals("Death")) && (animal.healthPercent == 0)){
                            System.out.println(animal.getName() + "has died since its health is .");
                        }
                        else if ((animal.healthStatus.equals("Death")) && (animal.currentAge == animal.maxAge)){
                            System.out.println(animal.getName() + "has died since it is too old.");
                        }
                        //Some health animals got sick
                        if (animal.healthStatus.equals("Health")) {
                            animalsGotSick(animal);
                        }
                    }

                }
                //Remove all the died animals
                for(var j = players.get(i).animals.size()-1;j >= 0;j--){
                    var diedAnimal = players.get(i).animals.get(j);
                    if (!diedAnimal.isLiving()) {
                        players.get(i).removeAnimal(diedAnimal);
                    }
                }

                //Call the method to show information to the player
                showInfo(players.get(i));
                Dialogs.delaySeconds(5);
                clear();

                //If there are some sick animals.Ask the player if she/he will save them
                //And show the information to the user again.
                Dialogs.askSaveAnimal(players.get(i));
                //Remove all the died animals
                for(var j = players.get(i).animals.size()-1;j >= 0;j--){
                    var diedAnimal = players.get(i).animals.get(j);
                    if (!diedAnimal.isLiving()) {
                        players.get(i).removeAnimal(diedAnimal);
                    }
                }

                //After some sick animals are died. It is possible the player have no money and no animals
                if (!lostPlayers.contains(players.get(i)) && (players.get(i).balance <= 0) && (players.get(i).animals.size() == 0 )){
                    print("You are lost! Because you have no money and no animals!!!");
                    lostPlayers.add(players.get(i));
                }

                if (lostPlayers.contains(players.get(i))){
                    continue;
                }

                //print the game´s main menu
                print("Please enter your choice:");
                print("-------------------------");
                print("1:Buy the animal");
                print("2:Buy the food");
                print("3:Feed the animal");
                print("4:Mate the new animal");
                print("5:Sale the animal");
                print("-------------------------");
                while (!scanner.hasNextInt()) {
                    scanner.nextLine();
                }
                var functionNumber = scanner.nextInt();

                switch (functionNumber){
                    case 1:
                        showAnimalInfo(players.get(i));
                        toBuyAnimals(players.get(i));
                        break;
                    case 2:
                        showFoodInfo(players.get(i));
                        toBuyFoods(players.get(i));
                        break;
                    case 3:
                        showInfo(players.get(i));
                        toFeedAnimals(players.get(i));
                        break;
                    case 4:
                        showAnimalInfo(players.get(i));
                        toMateAnimals(players.get(i));
                        break;
                    case 5:
                        showAnimalInfo(players.get(i));
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
        clear();
        print("You have played all rounds");
        print("Now is time to sell all animals for everyone and print the winner");
        for (var player:players) {
            toSellAllAnimals(player);
        }
        players.sort((Player a, Player b) -> { return a.balance > b.balance ? -1 : 1; });
        var winnerName = players.get(0).name;
        var winnerBalance= players.get(0).balance;
        System.out.printf("Congratulation!!! %s You won!!! You have money:%d \n",winnerName,winnerBalance);
        print("Game over");
        System.exit(0);
    }

    private void print(String x){
        // print a string if it is not empty
        if(!x.equals("")){ System.out.println(x); }
    }

    //This method to show players information about the player owned.
    private void showInfo(Player player){
        System.out.printf("You(player:%s) have money: %d  \n",player.name,player.balance);
        if (player.animals.size() > 0 ){
            print("You have owned following animals");
            System.out.println("-".repeat(110));
            for(var animal: player.animals){
                var animalType  = animal.getClass().getSimpleName();
                System.out.print(animalType + " ");
                animal.printField();
            }
            System.out.println("-".repeat(110));
        }
        if (player.foods.size() > 0 ){
            System.out.println("");
            print("You have owned following foods");
            System.out.println("=".repeat(110));
            for(var food: player.foods) {
                food.printField();
            }
            System.out.println("=".repeat(110));
        }
    }
    //This method to show players animal information about the player owned.
    private void showAnimalInfo(Player player){
        System.out.printf("You(player:%s) have money: %d  \n",player.name,player.balance);
        if (player.animals.size() > 0 ){
            print("You have owned following animals");
            System.out.println("-".repeat(110));
            for(var animal: player.animals){
                var animalType  = animal.getClass().getSimpleName();
                System.out.print(animalType + " ");
                animal.printField();
            }
            System.out.println("-".repeat(110));
        }
    }

    //This method to show players food information about the player owned.
    private void showFoodInfo(Player player){
        System.out.printf("You(player:%s) have money: %d  \n",player.name,player.balance);
        if (player.foods.size() > 0 ){
            System.out.println("");
            print("You have owned following foods");
            System.out.println("=".repeat(110));
            for(var food: player.foods) {
                food.printField();
            }
            System.out.println("=".repeat(110));
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
            var restBalance= myNewAnimal.checkBalance(balance);
            if (restBalance >=0){
                player.addAnimal(myNewAnimal);
                player.updateBalance(restBalance);
            }
            else {
                print("Sorry!You don't have enough money to buy this animal");
                Dialogs.delaySeconds(3);
                clear();
                break;
            }
            if (balance == 0){
               print("Since you have no money.It will be another players turn");
               Dialogs.delaySeconds(3);
               clear();
                break;
            }
            System.out.println("Would you like to buy other animal(y/n)");
            buyAnimal = (scanner.next()).toUpperCase().equals("Y");
            if (!buyAnimal){
                showInfo(player);
                Dialogs.delaySeconds(3);
                clear();
            }
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
            Store.buyFood(player);
            showInfo(player);
            var balance = player.balance;
            if (balance == 0){
                print("Since you have no money.It will be another players turn");
                Dialogs.delaySeconds(3);
                clear();
                break;
            }
            System.out.println("Would you like to buy other food(y/n)");
            buyFood = (scanner.next()).toUpperCase().equals("Y");
            if (!buyFood){
                showInfo(player);
                Dialogs.delaySeconds(3);
                clear();
            }
        }while (buyFood);
    }

    private void toFeedAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var feedAnimal = true;
        do{
            if (player.animals.size() == 0 ) {
                print("You don't have any animals to feed");
                Dialogs.delaySeconds(3);
                clear();
                return;
            }
            if (player.foods.size() == 0 ) {
                print("You don't have any foods to feed");
                Dialogs.delaySeconds(3);
                clear();
                return;
            }
            //ask the user what kind of animal does he/she want to feed
            var animalType =  Dialogs.askAnimalType("feed");
            //ask the user what kind of food does he/she want to feed
            print("Please enter your food type to feed(only input number)");
            for(var i = 0; i <player.foods.size(); i++) {
                System.out.print(i+1 + "."+ player.foods.get(i).getFoodType()+ " ");
            }
            System.out.println();
            var foodTypeNumber = scanner.next();
            var foodType = "";
            //find the food type according to the user´s input number
            for(var i = 0; i <player.foods.size(); i++) {
                foodType = foodTypeNumber.equals((i+1)+"")?player.foods.get(i).getFoodType():"";
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
               if (feedAnimal = (scanner.next()).toUpperCase().equals("Y")){
                   continue;
               }
               else {
                   return;
               }

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
                print("feed quantity <= 0 or you are not input a number ");
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
            if (!Arrays.asList(animalToFeed.editableFood).contains(foodType)){
                print("The food that you choose can not feed your animal that want to feed.");
                System.out.println("Would you like to continue to feed other animal(y/n)");
                if  (feedAnimal = (scanner.next()).toUpperCase().equals("Y")){
                    continue;
                }
                else{
                    return;
                }
            }

            //Star to eat food
            animalToFeed.eat(foodToFeed, feedQuantity);

            //After the animal eating food,minus the foods quantity
            var totalQuantity = 0.0 ;
            foodToFeed.minusQuantity(feedQuantity);
            totalQuantity = foodToFeed.getTotalQuantity();

            //If the food quantity is 0 then remove from food list.
            var removeFood = false;
            if (totalQuantity == 0) {
                player.removeFood(foodToFeed);
                removeFood = true;
            }
            if (removeFood){
                showInfo(player);
            }
            System.out.println("Would you like to feed other animal(y/n)");
            feedAnimal = (scanner.next()).toUpperCase().equals("Y");
            if (!feedAnimal){
                showInfo(player);
                Dialogs.delaySeconds(3);
                clear();
            }
        }while (feedAnimal);
    }
    private void toMateAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var breedAnimal = true;
        do{
            if (player.animals.size() == 0 ) {
                print("You don't have any animals to mate");
                Dialogs.delaySeconds(3);
                clear();
                return;
            }
            //Ask the user what kind of animal does she/he want to breed
            var animalType = Dialogs.askAnimalType("mate");

            print("Please enter one of your animals name to mate");
            var animalName1 = scanner.next();
            //Check if the animal name not exist in the players animal list
            if  (CheckInput.isAnimalNameNotExist(player,animalType,animalName1)){
                continue;
            }

            print("Please enter another of your animals name to mate");
            var animalName2 = scanner.next();
            //Check if the animal name not exist in the players animal list
            if  (CheckInput.isAnimalNameNotExist(player,animalType,animalName2)){
                continue;
            }

            //Check if the animal is suitable for breed
            var canBeMate = areAnimalSuitableToMate(player,animalType,animalName1,animalName2);
            if (!canBeMate){ continue;}
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
            System.out.println("Would you like to mate other animal(y/n)");
            breedAnimal = (scanner.next()).toUpperCase().equals("Y");
            if (!breedAnimal){
                showInfo(player);
                Dialogs.delaySeconds(3);
                clear();
            }
        }while (breedAnimal);
    }

    private void toSellAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var sellAnimal = true;
        do{
            if (player.animals.size() == 0 ) {
                print("You don't have any animals to sell");
                Dialogs.delaySeconds(3);
                clear();
                return;
            }
            //Ask the user what kind of animal does she/he want to sell
            var animalType = Dialogs.askAnimalType("sell");

            print("Please enter one of your animals name to sell");
            var animalNameToSell = scanner.next();
            //Check if the animal name not exist in the players animal list
            if  (CheckInput.isAnimalNameNotExist(player,animalType,animalNameToSell)){
                System.out.println("Would you like to sell other animal(y/n)");
                sellAnimal = (scanner.next()).toUpperCase().equals("Y");
                if (sellAnimal ){
                    continue;
                }
                else {
                    return;
                }

            }
            Animal animalToSell = null;
            for(var animal:player.animals){
                if ((animal.getName().equals(animalNameToSell)) && (animal.getClass().getSimpleName().equals(animalType))) {
                    animalToSell = animal;
                }
            }
            var sellAmount = getAmountOfSellAnimal(animalToSell);
            player.increaseBalance(sellAmount);
            player.removeAnimal(animalToSell);
            showInfo(player);

            System.out.println("Would you like to sell other animal(y/n)");
            sellAnimal = (scanner.next()).toUpperCase().equals("Y");
            if (!sellAnimal){
                showInfo(player);
                Dialogs.delaySeconds(3);
                clear();
            }
        }while (sellAnimal);
    }

    private void  toSellAllAnimals(Player player){
        for(var animal:player.animals){
            var sellAmount = getAmountOfSellAnimal(animal);
            player.increaseBalance(sellAmount);
        }
        System.out.printf("%s You have money: %d  \n",player.name,player.balance);
    }

    private void animalsGotSick(Animal animal) {
        // Create random number between 1-5
        int rndNum = new Random().nextInt(5) + 1;
        var animalHealthStatus = (rndNum == 1 ? "Sick" : "Health");
        if (animalHealthStatus.equals("Sick")) {
            animal.updateHealthStatus(animalHealthStatus);
        }
    }

   private boolean areAnimalSuitableToMate(Player player, String animalType,String animalName1, String animalName2){
        String animalGender1 = "";
        String animalGender2 = "";
        int    animalCurrentAge1 = 0;
        int    animalCurrentAge2 = 0;
        var canBeMate = false;
        for (var animal : player.animals) {
            //get two animals gender
            if ((animal.getName().equals(animalName1)) && (animal.getClass().getSimpleName().equals(animalType))) {
                animalGender1 = animal.getGender();
            }
            if ((animal.getName().equals(animalName2)) && (animal.getClass().getSimpleName().equals(animalType))) {
                animalGender2 = animal.getGender();
            }
            //get two animals age
            if ((animal.getName().equals(animalName1)) && (animal.getClass().getSimpleName().equals(animalType))) {
                animalCurrentAge1 = animal.currentAge;
            }
            if ((animal.getName().equals(animalName2)) && (animal.getClass().getSimpleName().equals(animalType))) {
                animalCurrentAge2 = animal.currentAge;
            }
        }

       //Check if the animal's gender is suitable for breed
        if (animalGender1.equals("MALE") && animalGender2.equals("FEMALE")) {
            canBeMate = true;
        } else if (animalGender1.equals("FEMALE") && animalGender2.equals("MALE")) {
            canBeMate = true;
        } else {
            System.out.println("These two animals can not be breed because their gender." +
                    " Only one is Male other is Female so that they can be breed");
            canBeMate = false;
        }

       //Check if the animal's age is suitable for breed
       if ((animalCurrentAge1 >= 3) && (animalCurrentAge2 >= 3)) {
           canBeMate = true;
       }
       else {
           System.out.println("These two animals are too young to mate! Their age have to both more than 3 years old" );
           canBeMate = false;
       }
        return canBeMate;
    }

    private int getAmountOfSellAnimal(Animal animalToSell) {
        var price = animalToSell.getInitialPrice();
        //convert maxAge to double
        var maxAge  = animalToSell.maxAge +0.0;
        //If animal's age is more than 70% of whole life time
        //the price will reduce half and then multiply healthPercent
        if ((animalToSell.currentAge/maxAge) >= 0.7){
            price = Math.round(price/2);
        }
        return (Math.round(price * animalToSell.getHealthPercent() /100));
    }

    public void clear(){
        // "clear" the console by printing 60 new lines
        System.out.println("\n".repeat(60));
    }
}
