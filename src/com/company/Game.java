package com.company;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


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
            //input = scanner.nextLine();
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
            System.out.printf("What is your name player%d:", i);
            var playersName = scanner.nextLine();
            players.add(new Player(playersName.toUpperCase()));
        }
        var countRounds = 5;
        do{
            for (int i = 1; i <= countPlayers; i++) {
                System.out.printf("It is your turn Player%d :%s",i,players.get(i-1).name);
                System.out.println("");
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
                        var buyFlag = true;
                        do{
                            Animal myNewAnimal = Store.buyAnimal();
                            //Check the players rest balance if it can buy animal
                            var balance = players.get(i-1).balance;
                            var className = myNewAnimal.getClass().getSimpleName();
                            int restBalance = -1 ;
                            switch(className){
                                case "Dog" -> restBalance=((Dog) myNewAnimal).checkBalance(balance);
                                case "Cat" -> restBalance=((Cat) myNewAnimal).checkBalance(balance);
                                case "Horse" -> restBalance=((Horse) myNewAnimal).checkBalance(balance);
                            }
                            if (restBalance >=0){
                                players.get(i-1).addAnimal(myNewAnimal);
                                players.get(i-1).updateBalance(restBalance);
                            }
                            else print("Sorry!You don't have enough money to buy this animal");
                            System.out.println("Would you like to buy other animal(y/n)");
                            buyFlag = (scanner.next()).toUpperCase().equals("Y");
                        }while (buyFlag);
                        //test print
                        for(var player: players){
                            System.out.println("Name: " + player.name + " Balance " + player.balance);
                        }
                        break;
                    case 2:
                    case 3:
                    case 4:
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

}
