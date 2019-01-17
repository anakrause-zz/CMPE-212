import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.text.DecimalFormat;

/*
 * CMPE212 Assignment 2
 * Written by Anastasia Krause (16avk2 - 20063760)
 * submitted February 15th
 * This program reads from csv file, parses pulses in data, and reoords insights to file
 * The program implements methods, loops, conditionals and file I/O
 */

public class Assn2_16avk2 {
    // Sets all random voltage spikes that aren't continuous to 0, to smooth over data
    public static double[][] cleanMotorData(double[][] ampData){
        // Iterates over entirety of 2D array of data
        for (int i = 1; i < 999; i++){
            for (int j = 1; j < 8; j++) {
                double motorAmps = ampData[i][j];
                // Finds all instances when the motor is "on" i.e. amps > 1
                // Sets all small spikes of motor "on" data to 0 to smooth over data
                if (motorAmps > 1){
                    if  (ampData[i - 1][j] < 1 && ampData[i + 1][j] < 1)
                        ampData[i][j] = 0;
                    if (ampData[i - 1][j] < 1 && ampData[i + 1][j] > 1 && ampData[i + 2][j] < 1)
                        ampData[i][j] = 0;
                }
            }
        }
        return ampData;
    }

    // Converts file input into properly formatted 2D array
    public static double[][] fileToArray(String fileName) throws FileNotFoundException  {
        // Initialize 2d array and create instance of scanner
        double ampData[][] = new double[1000][8];
        Scanner scanner = new Scanner(new File(fileName));

        int row = 0;
        int col = 0;
        // starts iterating over entire file
        while(scanner.hasNext()) {
            // when col is 7 (at the end of the line), there is no comma and just and '\n' character
            // must adjust delimiter accordingly

            if (col == 7){ scanner.useDelimiter("\n");
            } else { scanner.useDelimiter(","); }

            // captures value, strips all characters that aren't part of decimal number
            // assigns this array to appropriate array location

            String stringVal = scanner.next();
            stringVal = stringVal.replaceAll("[^0-9.]","");
            ampData[row][col] =  Double.valueOf(stringVal);

            // adjust iterative variables accordingly
            if (col == 7){
                row++;
                col = 0;
            } else { col++; }
        }
        return ampData;
    }

    // Rounds double input to three decimal points
    public static String roundDouble(double num) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        return decimalFormat.format(num);
    }
    // Creates string of all relevant data points for a singular motor number input
    public static String parseCycleData(double[][] data, int mNum){
        String returnString = "";
        double averageAmps, motorOneStart, motorOneEnd, sum;

        int ind = 0;

        // Computes calculations for each average motor and adjusts the returnString based on data
        while (ind < 1000) {
            if (data[ind][mNum] > 1){
                sum = 0;
                motorOneStart = data[ind][0];
                while (data[ind + 1][mNum] > 1){
                    sum += data[ind][mNum];
                    ind++;
                }
                motorOneEnd = data[ind][0];
                averageAmps = sum / (motorOneEnd - motorOneStart);

                returnString += motorOneStart + "," + motorOneEnd + "," + roundDouble(averageAmps);
                if (averageAmps > 8){ // must include note if current is too high
                     returnString  += ",***Current Exceeded***";
                }
                returnString += "\n";
            }
            ind++;
        }
        return returnString;
    }

    // Writes parsed cycle data to csv file with appropriate name
    public static void writeToFile(double[][] data) throws IOException {
        // writes data to file for each motor successively
        for (int motors = 0; motors < 7; motors++){
            String textData;
            PrintWriter printWriter = new PrintWriter(new File("Motor"+ (motors + 1) + ".csv"));
            if (parseCycleData(data, motors + 1) == "\n"){ // Checks if data generated
                textData = "Not used";
            } else { // headers for file if data exists
                textData = "start (sec), finish (sec), current (amps)\r\n";
            }

            // writes to file and closes instance
            textData += parseCycleData(data, motors + 1 );
            printWriter.write(textData);
            printWriter.close();
        }
    }

    // Calls all respective methods, throwing appropriate exceptions if needed
    public static void main(String[] args) throws IOException, FileNotFoundException {
        // converts file to array and cleans data appropriately, then writes to file
        try {
            double[][] ampData = fileToArray("Logger.csv");
            double[][] cleanedData = cleanMotorData(ampData);
            writeToFile(cleanedData);
            System.out.println("Data successfully written to directory files!");
        }
        catch (Exception e) {
            System.out.println("Data analysis did not commplete.");
        }
    }
}

