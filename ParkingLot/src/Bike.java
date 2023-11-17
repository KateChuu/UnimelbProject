/*
 * @author: Man Hua, Chu
 * @Email: manhuac@unimelb.student.edu.au
 * @StudentNo.: 1403798
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Bike extends Motorbike {
   
    // default constructor
    public Bike() {
        super();
        setType("Bike");
        setParkingFee(2);
        setHitFee(0);
        setOvernightFee(5);
    }

    public boolean checkMovement(ArrayList<Vehicle> parkedVehicles) {
        int x = getCurrentX();
        int y = getCurrentY();

        // if the car is at the entry door in the begining and user wants to leave
        // or the user wants to leave through entry or exit door while parking
        if((x == 1 && y == -1) || ParkingLot.layout[x][y].equals("D")) {
            System.out.println("You cannot exit the parking lot without checkout.");
            return false;
        }

        // a bike hits the wall
        if(ParkingLot.layout[x][y].equals("|") || ParkingLot.layout[x][y].equals("-")) {
            System.out.println("You have hit the wall!");
            return false;
        }

        // a bike hits the pillar
        if(ParkingLot.layout[x][y].equals("P")) {
            System.out.println("You have hit the pillar!");
            return false;
        }

        // a bike hits another vehicle
        for(Vehicle vehicle: parkedVehicles) {
            if(vehicle != this && vehicle.getCurrentX() == x && vehicle.getCurrentY() == y) {
                System.out.println("You have hit a vehicle!");
                return false;
            }
        }
        
        // a bike is only allowed on the reserved spot for bike/motorbike
        if(ParkingLot.layout[x][y].equals(".")) {
            for(int[] spot: getReservedSpot()) {
                if(spot[0] == x && spot[1] == y) {
                    return true;
                }
            }
            System.out.println("You cannot park a " + getType() + " in the parking lot anywhere except the parking spots near the entry.");
            return false;
        }
        return true;
    }

}
