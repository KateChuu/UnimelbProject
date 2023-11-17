/**
 * @author: Man Hua, Chu
 * @email: manhuac@student.unimelb.edu.au
 * @studentNumber: 1403798
 */
package controllers;

import exceptions.InvalidFileException;
import exceptions.UnknownEntityException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * This class handles all the operation about file, such as opening the Mars habitat file and reading the contents.
 */
public class FileController {
    private String filename;
    private String logname;
    public final static String DEFAULT_PATH = "resources/default.in";
    private final static String DEFAULT_LOG_PATH = "resources/habitability.log";
    private final static Character[] SYMBOLS = new Character[] {'.', '#', 'Z', 'X', 'H', 'J',
            'P', 'T', 'O', 'R', 'L', 'A', 'B', 'E', 'C', 'G', 'S', 'D', '@', '*'};
    /**
     * This constructor initialises the file controller.
     */
    public FileController() {
        this.filename = "";
        this.logname = DEFAULT_LOG_PATH;
    }
    /**
     * This method returns the file name of the Mars habitat.
     * @return the file name
     */
    public String getFilename() {
        return this.filename;
    }
    /**
     * This method sets the file name of the Mars habitat.
     * @param filename The file name
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
    /**
     * This method returns the log file name of the Mars habitat.
     * @return the log file name
     */
    public String getLogname() {
        return this.logname;
    }
    /**
     * This method sets the log file name of the Mars habitat.
     * @param logname The log file name
     */
    public void setLogname(String logname) {
        this.logname = logname;
    }
    /**
     * This method opens the file of the Mars habitat and reads from it, then prints the layout.
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     */
    public void displayLayout(MarsHabitatController marsController) {
        Scanner inputStream = null;
        ArrayList<char[]> marsHabitat = marsController.getMarsHabitat();

        // the Mars habitat is not initialised, so we read if from the file
        if(marsHabitat.size() == 0) {
            try {
                inputStream = new Scanner(new FileInputStream(getFilename()));
            } catch (FileNotFoundException e) {
                System.err.println("File Not Found, aborting mission.");
                System.exit(1);
            }
            // open file successfully, read and save content of the file in marsHabitat variable
            while(inputStream.hasNextLine()) {
                String str = inputStream.nextLine();
                char[] charArray = str.toCharArray();
                marsController.addMarsHabitat(charArray);
            }
            inputStream.close();
            // check whether the Mars habitat is valid
            checkInvalidFile(marsController);
            checkUnknownEntity(marsController);
        }
        // the Mars habitat is valid, thus we display it
        System.out.println("Here is a layout of Martian land.\n");
        for(char[] charArray: marsController.getMarsHabitat()) {
            System.out.println(charArray);
        }
        System.out.println("");
    }
    /**
     * This method checks every row in the Mars habitat is in the same length and the boundaries are all defined by '#'.
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     */
    public void checkInvalidFile(MarsHabitatController marsController) {
        ArrayList<char[]> marsHabitat = marsController.getMarsHabitat();
        try{
            // check every two rows of the Mars habitat has the same length
            for(int i = 0; i < marsHabitat.size()-1; ++i) {
                if(marsHabitat.get(i).length != marsHabitat.get(i+1).length) {
                    throw new InvalidFileException();
                }
            }
            int len = marsHabitat.get(0).length;
            // check the boundaries are defined with '#'
            for(int i = 0; i < marsHabitat.size(); ++i) {
                // check every character in first and last row is '#'
                if(i == 0 || i == marsHabitat.size()-1) {
                    for(char c: marsHabitat.get(i)) {
                        if(c != '#') {
                            throw new InvalidFileException();
                        }
                    }
                }
                // for other rows, check the first and last characters are '#'
                if(marsHabitat.get(i)[0] != '#' || marsHabitat.get(i)[len-1] != '#') {
                    throw new InvalidFileException();
                }
            }
        }catch (InvalidFileException e) {
            // marsHabitat is invalid, print error message and terminate program
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    /**
     * This method checks every symbol in the Mars habitat is valid
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     */
    private void checkUnknownEntity(MarsHabitatController marsController) {
        try{
            // check every character in every row only contains valid symbols
            for(char[] charArray: marsController.getMarsHabitat()) {
                for(char c: charArray) {
                    if(!Arrays.asList(SYMBOLS).contains(c)) {
                        throw new UnknownEntityException();
                    }
                }
            }
        }catch (UnknownEntityException e) {
            // marsHabitat is invalid, thus print error message and terminate program
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    /**
     * This method creates a new file to write the Mars habitat or updates the existing file
     * @param keyboard The Scanner object to read data from keyboard
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     */
    public void saveFile(Scanner keyboard, MarsHabitatController marsController) {
        System.out.println("Enter a filename for saving Martian Land Map");
        System.out.print("> ");
        String filename = keyboard.nextLine();
        PrintWriter outputStream = null;
        try{
            // open the file for writing the Mars habitat
            outputStream = new PrintWriter(new FileOutputStream(filename));
            // can't create the file if user enter invalid name
        }catch (FileNotFoundException e){
            System.out.println("Cannot create file for Martian Land Map.");
            return;
            // create or open the file successfully, but can't write
        }catch (Exception e) {
            System.out.println("Cannot write Martian Land Map to the file.");
            return;
        }
        // write the Mars habitat in the file row by row
        for(char[] charArray: marsController.getMarsHabitat()) {
            outputStream.println(charArray);
        }
        outputStream.close();
    }
    /**
     * This method creates a new log file to store habitability scores or append records in an existing log file.
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     */
    public void saveHabitability(MarsHabitatController marsController) {
        PrintWriter outputStream = null;
        try {
            // open the file for writing habitability log
            outputStream = new PrintWriter(new FileOutputStream(getLogname(), true ));
            // can't create the log file
        }catch (FileNotFoundException e){
            System.out.println("Cannot create file for Habitability Log.");
            return;
            // create or open the log file successfully, but can't write
        }catch (Exception e) {
            System.out.println("Cannot write Habitability Log to the file.");
            return;
        }
        // update the number of each entity before saving the log
        marsController.countEntities();
        HashMap<Character, Integer> quantityMap = marsController.getQuantityMap();
        outputStream.println("==START==");
        // check each entity type
        for (char c : quantityMap.keySet() ) {
            // if the quantity of that entity is greater than or equal to 1, record it in the log file
            if(quantityMap.get(c) > 0) {
                outputStream.println(MarsHabitatController.NAME_MAP.get(c) + "=" + quantityMap.get(c));
            }
        }
        outputStream.println("SCORE=" + marsController.calculateHabitability());
        outputStream.println("==END==");
        outputStream.close();
    }
    /**
     * This method opens the historical log file of the Mars habitat and prints the contents.
     */
    public void readHistoryLog() {
        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new FileInputStream(getLogname()));
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found.");
            return;
        }
        // read file successfully, then print the history log
        int programRun = 0;
        while(inputStream.hasNextLine()) {
            String line = inputStream.nextLine();
            if(line.equals("==START==")) {
                programRun++;
                System.out.println("Program Run :" + programRun);
                System.out.println("> Habitability Status");
                System.out.println("======================");
            }else if(!line.equals("==END==") && !line.equals("")) {
                line = line.toUpperCase();
                String[] words = line.split("=");
                if(words[0].equals("SCORE")) {
                    System.out.println("\nTotal Habitability Score: " + words[1]);
                }else if(MarsHabitatController.NAME_MAP.containsKey(words[0].charAt(0))
                        && MarsHabitatController.NAME_MAP.get(words[0].charAt(0)).equals(words[0])) {
                    System.out.println(words[0] + " = " + words[1]);
                }
            }
        }
        inputStream.close();
    }
}