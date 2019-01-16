/*
* CMPE212 Assignment 1
* Written by Anastasia Krause (16avk2 - 20063760)
* submitted February 1st
* This program is a simulation of a game called 'Pig'
* The user rolls two dice and plays against a computer player
* The program implements methods, loops, conditionals and console I/O
*/

import java.util.Random;
import java.util.Scanner;

public class Assn1_16avk2 {
    int humanScore;
    int robotScore;
    int turnScore;
    int robotTurnScore;

    // Constructor for class
    public Assn1_16avk2(){
        humanScore = 0;
        robotScore = 0;
        turnScore = 0;
        robotTurnScore = 0;
    }
    // Returns true if there is a next turn, false if not
    // Modifies humanTurnScore based on dice roll
    public boolean rollHumanCalculation(int[] rolls){
        int first = rolls[0];
        int second = rolls[1];

        System.out.println("A " + numbersToWords(first) + " and " + numbersToWords(second) + " were rolled.");
        if (first == 1 && second == 1) {
            this.turnScore += 25;
            System.out.println("Your turn score is now: " + this.turnScore);
            System.out.println("You rolled double 1s, roll again! Press enter to roll");
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            reader.nextLine(); // Scans the next token of the input as an int.
            return true;
        }
        else if (first == 1 || second == 1) {
            System.out.println("You rolled a 1! Your turn score is 0 and your turn is over.");
            this.turnScore = 0;
            return false;
        }
        else if (first == second) {
            this.turnScore += first * 4;
            System.out.println("Your turn score is now: " + this.turnScore);

            System.out.println("You scored doubles, roll again! Press enter to roll again.");
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            reader.nextLine(); // Scans the next token of the input as an int.
            return true;
        }
        else {
            this.turnScore += (first + second);
            System.out.println("Your turn score is now: " + this.turnScore);

            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("Enter 1 to roll again, enter 0 to hold.");
            int userIn = reader.nextInt(); // Scans the next token of the input as an int.

            if (userIn == 1) {
                return true;
            }
            return false;
        }
    }
    // Returns true if there is a next turn, false if not
    // Modifies robotTurnScore based on dice roll
    // Robot's strategy is to hold as soon as turn score exceeds 25
    public boolean rollRobotCalculation(int[] rolls) {
        int first = rolls[0];
        int second = rolls[1];
        System.out.println("\nA " + numbersToWords(first) + " and " + numbersToWords(second) + " were rolled.");
        if (first == 1 && second == 1) {
            this.robotTurnScore += 25;
            System.out.println("Your opponent rolled double 1s.");
            return true;
        } else if (first == 1 || second == 1) {
            System.out.println("Your opponent rolled a 1. Their turn score defaulted to 0");
            this.robotTurnScore = 0;
            return false;
        } else if (first == second) {
            this.robotTurnScore += first * 4;
            System.out.println("Your opponent scored doubles, and is rolling again!");
            return true;
        } else {
            this.robotTurnScore += (first + second);
            if (this.robotTurnScore < 25) {
                System.out.println("Opponent is rolling again!");
                return true;
            }
            return false;
        }
    }
    // Generates 2 element array as dice roll
    public static int[] generateDiceRoll(){
        Random rand = new Random();
        int max = 6;
        int min = 1;
        int[] nums = {rand.nextInt(max) + min, rand.nextInt(max) + min};
        return nums;
    }
    // Checks whether some player won
    // Returns boolean true if game is won, and vice versa
    public static boolean gameWon(int human, int robot){
        boolean gameWon = false;
        if ((human >= 100) || (robot >= 100)){
            gameWon = true;
        }
        return gameWon;
    }
    // Reports user and robot score
    public void printScores() {
        System.out.println("YOUR SCORE: " + this.humanScore);
        System.out.println("OPPONENTS SCORE: " + this.robotScore + "\n");
    }

    // converts integer input to string
    public String numbersToWords(int num){
        switch (num){
            case 1:
                return "one";
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
            case 5:
                return "five";
            case 6:
                return "six";
            default:
                return "Not correct number";
        }
    }

    public static void main(String[] args) {
        Assn1_16avk2 Game = new Assn1_16avk2();
        // Welcome messages
        System.out.println("***************************************");
        System.out.println("WELCOME TO THE GAME OF PIG!");
        System.out.println("Try to beat your opponent to 100 points!");
        System.out.println("***************************************");

        // Continuously play until game won
        while (!gameWon(Game.humanScore, Game.robotScore)){
            boolean isHumanTurn = true;
            boolean isRobotTurn = true;

            System.out.println("Press enter to begin your turn!");
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            reader.nextLine(); // Scans the next token of the input as an int.

            // HUMAN turn, generates roll and evaluates dice roll with rollHumanCalculation()
            while (isHumanTurn) {
                int[] nums = generateDiceRoll();
                isHumanTurn = Game.rollHumanCalculation(nums);
            }

            System.out.println("Your total turn score is: " + Game.turnScore);
            System.out.println("Your turn is over!\n");

            // Adds turn score to total and resets to 0 after turn
            Game.humanScore += Game.turnScore;
            Game.turnScore = 0;

            if (gameWon(Game.humanScore, Game.robotScore)){ break; }

            System.out.println("It's your opponents turn!");

            // ROBOT turn, generates roll and evaluates dice rollw ith rollRobotCalculation()
            while (isRobotTurn) {
                int[] nums = generateDiceRoll();
                isRobotTurn = Game.rollRobotCalculation(nums);
            }
            // Adds turn score to cumulative score adn resets turn score to 0 after turn
            Game.robotScore += Game.robotTurnScore;
            System.out.println("Your opponent's total turn score is: " + Game.robotTurnScore);
            Game.robotTurnScore = 0;

            System.out.println("Your opponent's turn is over.\n");

            Game.printScores();
        }
        // Finishes game messages

        System.out.println("********************************" );
        System.out.println("\nThanks for playing! Final score is: ");
        Game.printScores();
    }
}
