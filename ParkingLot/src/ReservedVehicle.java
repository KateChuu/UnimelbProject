/*
 * @author: Man Hua, Chu
 * @Email: manhuac@unimelb.student.edu.au
 * @StudentNo.: 1403798
 */
import java.util.ArrayList;

// reserved vehicle includes bike, motorbike and truck, which can only park in the reserved spots 
abstract class ReservedVehicle extends Vehicle {
     protected ArrayList<int []> reservedSpot;

     ReservedVehicle() {
        super();
        reservedSpot = new ArrayList<int []>();
        this.setReservedSpot();
     }

     public ArrayList<int []> getReservedSpot() {
        ArrayList temp = new ArrayList(this.reservedSpot);
        return temp;
    }

    public boolean checkMovement(ArrayList<Vehicle> parkedVehicles) {
        int x = getCurrentX();
        int y = getCurrentY();
        
        // if the vehicle is at the entry door in the begining and user wants to leave
        // or the user wants to leave through entry or exit door while parking
        if((x == 1 && y == -1) || getLayout()[x][y].equals("D")) {
            System.out.println("You cannot exit the parking lot without checkout.");
            return false;
        }
        // a truck/motorbike hits the wall
        if(getLayout()[x][y].equals("|") || getLayout()[x][y].equals("-")) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            increaseHit();
            return false;
        }
        // a truck/motorbike hits the pillar
        if(getLayout()[x][y].equals("P")) {
            System.out.println("You have hit the pillar, there will be a damage fee!");
            increaseHit();
            return false;
        }
        // a truck/motorbike hits another vehicle
        for(Vehicle vehicle: parkedVehicles) {
            if(vehicle != this && vehicle.getCurrentX() == x && vehicle.getCurrentY() == y) {
                System.out.println("You have hit a vehicle, there will be a damage fee!");
                increaseHit();
                return false;
            }
        }
        // a truck.motorbike is only allowed on the reserved spot for bike/motorbike
        if(getLayout()[x][y].equals(".")) {
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

    // different type of vehicles must set their own reserved spots
    abstract public void setReservedSpot();
}
