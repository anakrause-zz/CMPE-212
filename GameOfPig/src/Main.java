import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    int humanScore;
    int robotScore;
    int turnScore;

    public Main(){
        humanScore = 0;
        robotScore = 0;
        turnScore = 0;
    }

    public boolean rollCalculation(int[] rolls){
        int first = rolls[0];
        int second = rolls[1];
        System.out.println("You rolled a " + first + " and a " + second);

        if (first == 1 && second == 1){
            this.turnScore += 25;
            System.out.println("You rolled double 1s, roll again! Press enter to roll");
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            reader.nextLine(); // Scans the next token of the input as an int.

            return true;
        }
        else if (first == 1 || second == 1){
            System.out.println("You rolled a 1! Your turn score is 0.");
            this.turnScore = 0;
            return false;
        }
        else if (first == second){
            this.turnScore += first * 4;
            System.out.println("You scored doubles, roll again! Press enter to roll again.");
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            reader.nextLine(); // Scans the next token of the input as an int.
            return true;

        }
        else {
            this.turnScore += (first + second);
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("Enter 1 to roll again, enter 0 to hold.");
            int userIn = reader.nextInt(); // Scans the next token of the input as an int.

            if (userIn == 1){
                return true;
            }
            return false;
        }
    }

    public static int[] generateDiceRoll(){
        Random rand = new Random();
        int max = 6;
        int min = 1;
        int[] nums = {rand.nextInt(max) + min, rand.nextInt(max) + min};
        return nums;
    }

    public static boolean ifGameWon(int human, int robot){
        boolean gameWon = false;
        if ((human >= 100) || (robot >= 100)){
            gameWon = true;
        }
        return gameWon;
    }

    public static void main(String[] args) {
        // 0 for human, 1 for robot
        int turn = 0;
        Main Game = new Main();
        int count = 0;
        while (!ifGameWon(Game.humanScore, Game.robotScore)){
            // HUMAN TURN
            boolean isHumanTurn = true;
            while (isHumanTurn) {
                int[] nums = generateDiceRoll();
                isHumanTurn = Game.rollCalculation(nums);
            }
            break;
        }
        System.out.println("Your total turn score is: " + Game.turnScore);



    }
}
