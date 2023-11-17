/*
 * @author: Man Hua, Chu
 * @Email: manhuac@unimelb.student.edu.au
 * @StudentNo.: 1403798
 */
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class ParkingLot {
    static String[][] layout;
    private ArrayList<Vehicle> parkedVehicles;
    private ArrayList<Vehicle> leftVehicles;
    private int length;
    private int width;
    private int emptyLot;
    private int numOfReservedSpot;
    private Vehicle vehicle = null;
    public static final int MIN_LENGTH = 7;
    public static final int MIN_WIDTH = 7;
    public static final int LENGTH_OF_REGNID = 6;

    // constructor for the case user uses menu to initialise parking lot
    public ParkingLot() {
        this.length = 0;
        this.width = 0;
        this.parkedVehicles = new ArrayList<Vehicle>();
        this.leftVehicles = new ArrayList<Vehicle>();
        this.layout = new String[getLength()][getWidth()];
        setEmptyLot(getLength(), getWidth());
    }

    // default constructor for the case user uses command line to initialise parking lot
    public ParkingLot(int length, int width) {
        setLength(length);
        setWidth(width);
        this.parkedVehicles = new ArrayList<Vehicle>();
        this.leftVehicles = new ArrayList<Vehicle>();
        setLayout();
        setEmptyLot(length, width);
    }

    public int getLength() {
        return this.length;
    }
    public int getWidth() {
        return this.width;
    }
    public int getEmptyLot() {
        return emptyLot - getParkedVehicles().size();
    }
    public int getNumOfReservedSpot() {
        return this.numOfReservedSpot;
    }
    public ArrayList<Vehicle> getParkedVehicles() {
        ArrayList temp = new ArrayList(this.parkedVehicles);
        return temp;
    }
    public ArrayList<Vehicle> getLeftVehicles() {
        ArrayList temp = new ArrayList(this.leftVehicles);
        return temp;
    }
    public String[][] getLayout() {
        String[][] temp = new String[getWidth()][getLength()]; 
        for (int i = 0; i < getWidth(); ++i) {
             System.arraycopy(this.layout[i], 0, temp[i], 0, this.layout[i].length);
        }
        return temp;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setLayout() {
        layout = new String[getWidth()][getLength()];
        setNumOfReservedSpot();
        // set wall and doors
        for(int i = 0; i < getWidth(); ++i) {
            if(i == 0 || i == getWidth()-1) {
                for(int j = 1; j < getLength() - 1; ++j) {
                    layout[i][j] = "-";
                }
            }
            layout[i][0] = "|";
            layout[i][getLength()-1] = "|";

            if(i == 1) {
                layout[i][0] = "D";
            }else if(i == getWidth()-2) {
                layout[i][getLength() - 1] = "D";
            }
        }
        // set parkinglane, parking space and pillar
        for(int i = 1; i < getWidth()-1; ++i) {
            for(int j = 1; j < getLength()-1; ++j) {
                if(i == 1 || i == getWidth()-2) {
                    layout[i][j] = "~";
                }else if(i == 2 || i == getWidth()-3) {
                    if(j % 2 == 1) {
                        layout[i][j] = "P";
                    }else {
                        layout[i][j] = "~";
                    }
                }else {
                    if(j % 2 == 1) {
                        layout[i][j] = ".";
                    }else {
                        layout[i][j] = "~";
                    }
                }
            }
        }
    }
    public void setEmptyLot(int length, int width) {
        emptyLot = (getLength() - 2 - (getLength()-2) /2) * (getWidth() - 4) - (getLength() - 2 - (getLength()-2) /2)*2;
    }
    public void setNumOfReservedSpot() {
        this.numOfReservedSpot = getLength() - 2*3;
    }
    public void setParkedVehicle(Vehicle vehicle) {
        ArrayList temp = getParkedVehicles();
        temp.add(vehicle);
        this.parkedVehicles = temp;
    }
    public void setLeftVehicle(Vehicle vehicle) {
        ArrayList temp = getLeftVehicles();
        temp.add(vehicle);
        this.leftVehicles = temp;
    }
    public void cleanLeftVehicle() {
        this.leftVehicles = new ArrayList<Vehicle>();
    }
    public void removeParkedVehicle(Vehicle vehicle) {
        ArrayList temp = getParkedVehicles();
        temp.remove(vehicle);
        this.parkedVehicles = temp;
    }

    public void commandParkingLot(Scanner keyboard) {
        // after entering "parkinglot", user comes to this menu
        String anyKey = "";
        boolean enterFlag = true;
        while(enterFlag) {
            System.out.println("Type 'init' to initialise the parking space");
            System.out.println("Type 'view' to view the layout of the parking space");
            System.out.println("Type 'menu' to return to the main menu");
            System.out.print("> ");
            String command = keyboard.nextLine().toLowerCase();

            switch (command) {
                case ("init"):
                    // parking lot isn't empty, so user can't re-initialise
                    if(getParkedVehicles().size() != 0) {
                        System.out.println("There are vehicles in the Parking Lot, you cannot change the space of the parking lot at the moment.");
                        break;
                    }
                    // initialise parkinglot
                    initParkingLot(keyboard);
                    System.out.println("Press any key to return to parkinglot menu");
                    anyKey = keyboard.nextLine();
                    anyKey = keyboard.nextLine();
                    break;

                case ("view"):
                    // parking lot is initialised, thus system displays parking lot layout
                    if (getLength() != 0 && getWidth() != 0) {
                        displayLayout();
                        System.out.println("Press any key to return to parkinglot menu");
                        anyKey = keyboard.nextLine();

                        // parking lot itn't initialised, so user is asked to run "init" first
                    } else {
                        System.out.println("The parking lot is not initialised. Please run init!");
                        System.out.println("Press any key to return to parkinglot menu");
                        anyKey = keyboard.nextLine();
                    }
                    break;

                case ("menu"):
                    displayParkingLotMenu();
                    enterFlag = false;
                    break;

                default:
                    System.out.println("Command not found!");
                    break;
            }
        }
    }
    public void displayParkingLotMenu() {
        // parking lot is not initialised, so empty and occupied lots are both none
        if(getLength() == 0 && getWidth() == 0) {
            System.out.println("Empty Lots: [None] | Occupied: [None]");

            // parking lot is initialised, print the number of empty and occupied lots
        }else if(getEmptyLot() != 0){
            System.out.println("Empty Lots: " + getEmptyLot() + " | " + "Occupied: " + getParkedVehicles().size());
        }else {
            System.out.println("Empty Lots: [None] | " + "Occupied: " + getParkedVehicles().size());
        }
        System.out.println("Please enter a command to continue.");
        System.out.println("Type 'help' to learn how to get started.");
    }
    
    // use the length and width provided by user to initialise parking lot
    public void initParkingLot(Scanner keyboard) {
        cleanLeftVehicle();

        // check the length is larger than 7
        System.out.println("Please enter the length of the parking lot.");
        length = keyboard.nextInt();
        while(length < MIN_LENGTH) {
            System.out.println("> The length of the parking lot cannot be less than " + MIN_LENGTH + ". Please re-enter.");
            length = keyboard.nextInt();
        }
        // check the width is larger than 7
        System.out.println("> Please enter the width of the parking lot.");
        width = keyboard.nextInt();
        while(width < MIN_WIDTH) {
            System.out.println("> The width of the parking lot cannot be less than " + MIN_WIDTH + ". Please re-enter.");
            width = keyboard.nextInt();
        }
        // set up parking lot, which includes pillar location, wall and door
        System.out.println("> Parking Lot Space is setup. Here is the layout -");
        this.setLength(length);
        this.setWidth(width);
        this.setEmptyLot(length, width);
        this.setLayout();
        this.displayLayout();
    }

    // display the layout of parking lot
    public void displayLayout() {
        for (int i = 0; i < getWidth(); ++i) {
            for (int j = 0; j < getLength(); ++j) {
                boolean isPrinted = false;
                // check whether a vehicle is in a particular parking space
                for(Vehicle vehicle: getParkedVehicles()) {
                    // if a vehicle is still at the front door, we don't print it
                    if(vehicle.getCurrentX() == 1 && vehicle.getCurrentY() == 0) {
                        continue;
                    }
                    if(vehicle.getCurrentX() == i && vehicle.getCurrentY() == j) {
                        if(vehicle.getType().equals("Car")) {
                            System.out.print("C");
                            isPrinted = true;
                            break;
                        }else if(vehicle.getType().equals("Bike")) {
                            System.out.print("B");
                            isPrinted = true;
                            break;
                        }else if(vehicle.getType().equals("Truck")) {
                            System.out.print("T");
                            isPrinted = true;
                            break;
                        }else {
                            System.out.print("M");
                            isPrinted = true;
                            break;
                        }
                    }
                }
                // print "." when there is no vehicle in the parking space
                if(!isPrinted) {
                    System.out.print(layout[i][j]);
                }
            }
            System.out.println("");
        }
    }

    public void displayFeeLog() {
        System.out.println("============ Here are the Transaction logs for the Java Parking Lot =============");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("| Vehicle Type | Registration Id | Entry DateTime | Exit DateTime | Parking Fee |");
        System.out.println("---------------------------------------------------------------------------------");

        // if there is no checking out records 
        if(getLeftVehicles().size() == 0){
            System.out.println("No records found!");
        }else {
            for(Vehicle vehicle: getLeftVehicles()) {
                String entryDatetime = String.valueOf(vehicle.getDatetimeRecord().getEntryDatetime());
                entryDatetime = entryDatetime.substring(0, 10) + " " + entryDatetime.substring(11, entryDatetime.length());

                String exitDatetime = String.valueOf(vehicle.getDatetimeRecord().getExitDatetime());
                exitDatetime = exitDatetime.substring(0, 10) + " " + exitDatetime.substring(11, exitDatetime.length());

                System.out.printf("|%14s", vehicle.getType());
                System.out.printf("|%17s", vehicle.getRegnID());
                System.out.printf("|%16s", entryDatetime);
                System.out.printf("|%15s", exitDatetime);
                System.out.printf("|%13.1f|", vehicle.getTotalFee());
                System.out.println("");
            }
        }
        displayParkingLotMenu();
    }

    public void checkin(Scanner keyboard) {
        // parkint lot is full
        if(getParkedVehicles().size() == (getLength() - 2 - (getLength()-2) /2) * (getWidth() - 4) - (getLength() - 2 - (getLength()-2) /2)*2) {
            System.out.println("The parking is full. Please come back later. Taking you back to main menu.");
            return;
        }
        // parkinglot isn't initialised
        if(getLength() == 0 && getWidth() == 0) {
            System.out.println("The parking lot hasn't been initialised. Please set up a space for the parking lot. Taking you back to main menu.");
            return;
        }
        
        boolean keepEnterCheckinType = true;
        String type = "";
        System.out.println("Please enter the vehicle details");
        while(keepEnterCheckinType) {
            System.out.print("> Vehicle Type: ");
            type = keyboard.nextLine().toLowerCase();
            // according to the type of vehicle user enters to create new vehicle
            switch(type){
                case("car"):
                    vehicle = new Car();
                    keepEnterCheckinType = false;
                    break;

                case("bike"):
                case("motorbike"):
                    int num = 0;
                    for(Vehicle vehicle: getParkedVehicles()) {
                        if(vehicle.getType().equals("Bike") || vehicle.getType().equals("Motorbike")) {
                            num++;
                        }
                    }
                    if(getNumOfReservedSpot() == num) {
                        System.out.println("Parking full for " + type + ". Please come back later. Taking you back to main menu.");
                        return;
                    }
                    vehicle = type.equals("bike")? new Bike(): new Motorbike();
                    keepEnterCheckinType = false;
                    break;

                case("truck"):
                    int numOfTruck = 0;
                    for(Vehicle vehicle: getParkedVehicles()) {
                        if(vehicle.getType().equals("Truck")) {
                            numOfTruck++;
                        }
                    }
                    if(getNumOfReservedSpot() == numOfTruck) {
                        System.out.println("Parking full for truck. Please come back later. Taking you back to main menu.");
                        return;
                    }
                    vehicle = new Truck();
                    keepEnterCheckinType = false;
                    break;

                default:
                    System.out.println("Invalid detail, please enter detail again!");
                    break;
            }
        }
        // checkin the new vehicle
        vehicle.checkin(keyboard);
        setParkedVehicle(vehicle);
    }

    public void checkout(Scanner keyboard) {
        // if the parking lot is not set up, user can't check-out
        if(getLength() == 0 && getWidth() == 0) {
            System.out.println("Invalid command! The parking is empty. Taking you back to main menu.");
            displayParkingLotMenu();
            return;
        }
        // if there is no vehicle in the parking lot, user can't check-out
        if(getParkedVehicles().size() == 0) {
            System.out.println("Invalid command! The parking is empty. Taking you back to main menu.");
            displayParkingLotMenu();
            return;
        }

        System.out.println("Please enter your vehicle details");
        boolean keepEnter = true;
        String regnID = "";
        while(keepEnter) {
            System.out.print("> Regn Id: ");
            regnID = keyboard.nextLine();
            // if user enters invalid ID, ask him/her to enter again
            if(regnID.length() != LENGTH_OF_REGNID) {
                System.out.println("Invalid detail, please enter detail again!");
            }else {
                keepEnter = false;
            }
        }

        for(Vehicle vehicle: parkedVehicles) {
            if(vehicle.getRegnID().equals(regnID)) {
                // if the vehicle user wants to check out is at the exit door
                if(vehicle.getCurrentX() == getWidth()-2 && vehicle.getCurrentY() == getLength()-2) {
                    vehicle.checkout(keyboard);

                    // calculate the total fee
                    vehicle.calculateFee();

                    // add that vehicle in the history record
                    setLeftVehicle(vehicle);

                    // remove that vehicle from current parked vehicle list
                    removeParkedVehicle(vehicle);
                    displayParkingLotMenu();
                }else {
                    System.out.println("The selected vehicle type is not at the checkout door. Please proceed to checkout door. Taking you back to main menu.");
                    displayParkingLotMenu();
                }
                return;
            }
        }
        // the vehicle ID user entered is not found           
        System.out.println("The selected vehicle type is not present in the parking lot. Taking you back to main menu");
        displayParkingLotMenu();
    }

    public void park(Scanner keyboard) {
        System.out.println("To park a vehicle provide the details.");
        // the parking lot isn't initialised, user can't park the vehicle
        if(getParkedVehicles().size() == 0) {
            System.out.println("No vehicle checked in the parking lot, taking you back to main menu");
            displayParkingLotMenu();
            return;
        }
        
        boolean keepEnter = true;
        while(keepEnter) {
            System.out.print("> Regn Id: ");
            String regnID = keyboard.nextLine();

            // if user enters invalid ID, ask him/her to enter again
            if(regnID.length() != 6) {
                System.out.println("Invalid detail, please enter detail again!");
                continue;
            }
            for(Vehicle vehicle: parkedVehicles) {
                if(vehicle.getRegnID().equals(regnID)) { 
                    // keepPark = 2 means user entered invalid action but wanted to keep parking
                    // keepPark = 1 means user entered valid action and wanted to keep parking
                    // keepPark = 0 means user wanted to quit
                    int keepPark = 1;
                    displayLayout();
                    while(keepPark > 0) {
                        int x = vehicle.getCurrentX();
                        int y = vehicle.getCurrentY();

                        // park vehicle according to its typr
                        if(vehicle.getType().equals("Car")) {
                            keepPark = vehicle.park(keyboard, getParkedVehicles());
                        }else if(vehicle.getType().equals("Truck")) {
                            keepPark = vehicle.park(keyboard, getParkedVehicles());
                        }else {
                            keepPark = vehicle.park(keyboard, getParkedVehicles());
                        }
                        if(keepPark == 1) {
                            displayLayout();
                        }                   
                    }
                    displayParkingLotMenu();
                    return;
                }
            }
            // the vehicle ID user entered is not found           
            System.out.println("The vehicle mentioned is not parked in the parking lot.");
        }        
    }
}

