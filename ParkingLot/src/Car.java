/*
 * @author: Man Hua, Chu
 * @Email: manhuac@unimelb.student.edu.au
 * @StudentNo.: 1403798
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Car extends Vehicle {

    // default constructor
    public Car() {
        super();
        setType("Car");
        setParkingFee(4);
        setHitFee(20);
        setOvernightFee(10);
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

        // a car hits the wall
        if(ParkingLot.layout[x][y].equals("|") || ParkingLot.layout[x][y].equals("-")) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            increaseHit();
            return false;
        }

        // a car hits the pillar
        if(ParkingLot.layout[x][y].equals("P")) {
            System.out.println("You have hit the pillar, there will be a damage fee!");
            increaseHit();
            return false;
        }
        
        // a car hits another vehicle
        for(Vehicle vehicle: parkedVehicles) {
            if(vehicle != this && vehicle.getCurrentX() == x && vehicle.getCurrentY() == y) {
                System.out.println("You have hit a vehicle, there will be a damage fee!");
                increaseHit();
                return false;
            }
        }
        return true;
    }
}
