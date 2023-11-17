/*
 * @author: Man Hua, Chu
 * @Email: manhuac@unimelb.student.edu.au
 * @StudentNo.: 1403798
 */

import java.util.Scanner;
// main program to run
public class ParkingLotEngine {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        ParkingLotEngine engine = new ParkingLotEngine();
        ParkingLot parkingLot = new ParkingLot();

        // if user uses command line to initialise parking lot
        if(args.length == 2) {
            int length = Integer.parseInt(args[0]);
            int width = Integer.parseInt(args[1]);
            // the length and width are both valid
            if(length >= ParkingLot.MIN_LENGTH && width >= ParkingLot.MIN_WIDTH) {
                parkingLot = new ParkingLot(length, width);
                engine.startProgram(args, keyboard, parkingLot);
            }else {
                System.out.println("ParkingLot size cannot be less than " + ParkingLot.MIN_LENGTH + ". Goodbye!");
            }
        }else {
            // Runs the main game loop.
            engine.startProgram(args, keyboard, parkingLot);
        }
    }

    /*
     *  Start with the main menu here.
     */
    private void startProgram(String[] args, Scanner keyboard, ParkingLot parkingLot) {

        // Print out the title text.
        displayWelcomeText();

        // the main menu for user to enter commands
        displayMenu(keyboard, parkingLot);


    }
    /*
     *  Displays the welcome text.
     */
    private void displayWelcomeText() {

        String titleText =
                " _     _  _______  ___      _______  _______  __   __  _______ \n"+
                        "| | _ | ||       ||   |    |       ||       ||  |_|  ||       |\n"+
                        "| || || ||    ___||   |    |      _||   _   ||       ||    ___|\n"+
                        "|       ||   |___ |   |    |     |  |  | |  ||       ||   |___ \n"+
                        "|       ||    ___||   |___ |     |  |  |_|  || ||_|| ||    ___|\n"+
                        "|   _   ||   |___ |       ||     |_ |       || |\\/|| ||   |___ \n"+
                        "|__| |__||_______||_______||_______||_______||_|   |_||_______|\n" +
                        "_________________________ TO JAVA PARKING _____________________";

        System.out.println(titleText);
        System.out.println();
    }

    // the main menu for user to enter commands
    private void displayMenu(Scanner keyboard, ParkingLot parkingLot) {
        // if parking lot is not initialised by command line
        // creating a new parking lot object for use to initialise
        if(parkingLot.getLength() == 0 && parkingLot.getWidth() == 0) {
            parkingLot = new ParkingLot();
        }

        // display the parking lot menu, which shows the number of empty lots and occupied lots
        parkingLot.displayParkingLotMenu();
        String command = "";
        boolean enterFlag = true;
        boolean enterHelp = false;

        while(enterFlag) {
            System.out.print("> ");
            command = keyboard.nextLine().toLowerCase();

            // the users is currently in help menu
            if(enterHelp) {
                switch(command) {
                    case("menu"):
                        enterHelp = false;
                        break;
                    case("commands"):
                    case("parkinglot"):
                    case("checkin"):
                    case("checkout"):
                    case("park"):
                    case("parkingfeelog"):
                    case("exit"):
                        break;
                    default:
                        System.out.println("Command not found!");
                        command = "help";
                        break;
                } 
            }

            switch(command) {
                case("help"):
                    System.out.println("");
                    System.out.println("Type 'commands' to list all the available commands");
                    System.out.println("Type 'menu' to return to the main menu");
                    enterHelp = true;
                    break;

                case("commands"):
                    System.out.println("");
                    System.out.println("help: shows you list of commands that you can use.");
                    System.out.println("parkinglot: initialise the space for parking lot or view the layout of parking lot.");
                    System.out.println("checkin: add your car details while entering the parking lot.");
                    System.out.println("park: park your car to one of the empty spot.");
                    System.out.println("checkout: view the parking fee while exiting the parking lot.");
                    System.out.println("parkingfeelog: view the transaction log for parking lot.");
                    System.out.println("exit: To exit the program.");
                    System.out.println("\nType 'commands' to list all the available commands");
                    System.out.println("Type 'menu' to return to the main menu");
                    break;

                case("menu"):
                    System.out.println("");
                    parkingLot.displayParkingLotMenu();
                    break;

                case("parkinglot"):
                    parkingLot.commandParkingLot(keyboard);
                    break;

                case("checkin"):
                    parkingLot.checkin(keyboard);
                    parkingLot.displayParkingLotMenu();
                    break;

                case("checkout"):
                    parkingLot.checkout(keyboard);
                    break;

                case("park"):
                    parkingLot.park(keyboard);
                    break;

                case("parkingfeelog"):
                    parkingLot.displayFeeLog();
                    break;

                case("exit"):
                    System.out.println("Good bye from the Java Parking Lot! See you next time!");
                    enterFlag = false;
                    break;

                default:
                    System.out.println("Command not found!");
                    parkingLot.displayParkingLotMenu();
                    break;
            }
        }
    }
}
