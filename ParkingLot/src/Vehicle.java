/**
 * TODO: Write a comment describing your class here.
 * @author TODO: Fill in your name, university email, and student number here.
 *
 */
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Vehicle {
    private String type;
    private String regnID;
    private String model;
    private String colour;
    private Date datetimeRecord;
    private int currentX;
    private int currentY;
    private int totalHour;
    private int totalOvernight;
    private int totalHit;
    private double totalFee;
    private int parkingFee;
    private int hitFee;
    private int overnightFee;
    public static final int LENGTH_OF_REGNID = 6;
    public static final int HOURS_PER_DAY = 24;

    public Vehicle() {
        this.regnID = "";
        this.model = "";
        this.colour = "";
        this.datetimeRecord = new Date();
        this.currentX = 1;
        this.currentY = 0;
        this.totalHour = 0;
        this.totalOvernight = 0;
        this.totalFee = 0.0;
        this.totalHit = 0;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setParkingFee(int parkingFee) {
        this.parkingFee = parkingFee;
    }
    public void setHitFee(int hitFee) {
        this.hitFee = hitFee;
    }
    public void setOvernightFee(int overnightFee) {
        this.overnightFee = overnightFee;
    }
    public void setRegnID(String regnID) {
        this.regnID = regnID;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }
    public void moveCurrentX(int move) {
        currentX += move;
    }
    public void moveCurrentY(int move) {
        currentY += move;
    }
    public void setCurrentX(int x) {
        currentX = x;
    }
    public void setCurrentY(int y) {
        currentY = y;
    }
    public void setTotalHour(int hours) {
        this.totalHour = hours;
    }
    public void setTotalOvernight(int hours) {
        this.totalOvernight = hours / HOURS_PER_DAY;
        setTotalHour(hours - getTotalOvernight() * HOURS_PER_DAY);
    }
    public void increaseHit() {
        this.totalHit++;
    }
    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }
    public String getType() {
        return this.type;
    }
    public int getParkingFee() {
        return this.parkingFee;
    }
    public int getHitFee() {
        return this.hitFee;
    }
    public int getOvernightFee() {
        return this.overnightFee;
    }
    public String getRegnID() {
        return this.regnID;
    }
    public Date getDatetimeRecord() {
        return this.datetimeRecord;
    }
    public int getCurrentX() {
        return this.currentX;
    }
    public int getCurrentY() {
        return this.currentY;
    }
    public int getTotalHour() {
        return this.totalHour;
    }
    public int getTotalOvernight() {
        return this.totalOvernight;
    }
    public double getTotalFee() {
        return this.totalFee;
    }
    public int getTotalHit() {
        return this.totalHit;
    }

    // create a copy of layout to avoid passing the original array
    public String[][] getLayout() {
        String[][] temp = new String[ParkingLot.layout[0].length][ParkingLot.layout.length]; 
        for (int i = 0; i < ParkingLot.layout[0].length; ++i) {
             System.arraycopy(ParkingLot.layout[i], 0, temp[i], 0, ParkingLot.layout[i].length);
        }
        return temp;
    }

    public void checkin(Scanner keyboard) {
        boolean keepEnter = true;

        // keep asking user to enter ID until the ID is valid
        while(keepEnter) {
            System.out.print("> Regn Id: ");
            String regnID = keyboard.nextLine();
            if(regnID.length() == LENGTH_OF_REGNID) {
                keepEnter = false;
                setRegnID(regnID);
            }else {
                System.out.println("Invalid detail, please enter detail again!");
            }
        }
        
        System.out.print("> Vehicle Model: ");
        this.setModel(keyboard.nextLine());

        System.out.print("> Vehicle Colour: ");
        this.setColour(keyboard.nextLine());

        // keep asking user to enter date until the date is valid
        String dateOfEntryStr = "";
        keepEnter = true;
        while(keepEnter) {
            System.out.print("> Date of entry: ");
            dateOfEntryStr = keyboard.nextLine();
            datetimeRecord.setEntryDate(dateOfEntryStr);
            if(datetimeRecord.getEntryDate() != null) {
                keepEnter = false;
            }
        }

        // keep asking user to enter time until the time is valid
        String timeOfEntryStr = "";
        keepEnter = true;
        while(keepEnter) {
            System.out.print("> Time of entry: ");
            timeOfEntryStr = keyboard.nextLine();
            datetimeRecord.setEntryDatetime(timeOfEntryStr);
            if(datetimeRecord.getEntryDatetime() != null) {
                keepEnter = false;
            }
        }
    }

    public void checkout(Scanner keyboard) {
        // keep asking user to enter date until the date is valid
        String dateOfExitStr = "";
        boolean keepEnter = true;
        while(keepEnter) {
            System.out.print("> Date of exit: ");
            dateOfExitStr = keyboard.nextLine();
            datetimeRecord.setExitDate(dateOfExitStr);
            if(datetimeRecord.getExitDate() != null) {
                if(datetimeRecord.compareDates()) {
                    keepEnter = false;
                }else {
                    System.out.println("Checkout datetime cannot be less than checkin datetime for the vehicle. Please re-enter.");
                }             
            }
        }

        // keep asking user to enter time until the time is valid
        String timeOfExitStr = "";
        int hours = 0;
        keepEnter = true;
        while(keepEnter) {
            System.out.print("> Time of exit: ");
            timeOfExitStr = keyboard.nextLine();
            datetimeRecord.setExitDatetime(timeOfExitStr);

            if(datetimeRecord.getExitDatetime() != null) {
                hours = datetimeRecord.getDifferenceInHours();
                if(hours >= 0) {
                    setTotalHour(hours);
                    setTotalOvernight(hours);
                    keepEnter = false;

                    // if the exit time is later than entry time
                }else {
                    System.out.println("Checkout datetime cannot be less than checkin datetime for the vehicle. Please re-enter.");
                }
            }
        }
        // all information is valis and correct, proceeding to payment part
        this.pay(keyboard);
    }

    public void calculateFee() {
        double fee = getTotalHour() * getParkingFee() + getTotalOvernight() * getOvernightFee() + getTotalHit() * getHitFee();
        setTotalFee(fee);
    }

    public void pay(Scanner keyboard) {
        calculateFee();
        System.out.println("Please verify your details.");
        System.out.println("Total number of hours: " + getTotalHour());
        if(getTotalOvernight() != 0) {
            System.out.println("Total number of overnight parking: " + getTotalOvernight());
        }   
        System.out.println("Total number of hits:" + getTotalHit());
        System.out.println("Vehicle Type: " + getType());
        System.out.println("Regn Id: " + getRegnID());
        System.out.printf("Total Parking Fee: $%.1f\n", getTotalFee());
        System.out.println("Type Y to accept the fee or menu to return to main menu");
        String accept = keyboard.nextLine().toLowerCase();

        // user can leave only when accepts the fee
        while(!accept.equals("y")) {
            System.out.println("You cannot checkout your vehicle. Please accept the fee by pressing Y or type menu to return to main menu and park your vehicle.");
            accept = keyboard.nextLine().toLowerCase();
        }
        System.out.println("> Thank you for visiting Java Parking Lot. See you next time!");
    }

    public int park(Scanner keyboard, ArrayList<Vehicle> parkedVehicles) {
        String direction = "";
        int previousX = this.getCurrentX();
        int previousY = this.getCurrentY();

        // get the direction entered by user and move
        System.out.println("Type w/s/a/d to move the vehicle to up/down/left/right or else press q to exit.");
        System.out.print("> ");
        direction = keyboard.nextLine().toLowerCase();
        int keepPark = 1;
        switch(direction) {
            case("w"):
                this.moveCurrentX(-1);
                break;
            case("s"):
                this.moveCurrentX(1);
                break;
            case("a"):
                this.moveCurrentY(-1);
                break;
            case("d"):
                this.moveCurrentY(1);
                break;
            case("q"):
                keepPark = 0;
                break;
            default:
                System.out.println("Invalid command!");
                keepPark = 2;
                return keepPark;
        }
        // check whether the movement is valid or not
        if(!this.checkMovement(parkedVehicles)) {
            setCurrentX(previousX);
            setCurrentY(previousY);
        }
        return keepPark;
    }

    // all types of vehicles must implement their own method to check whether the movement use entered is valid or not
    abstract public boolean checkMovement(ArrayList<Vehicle> parkedVehicles);
}


