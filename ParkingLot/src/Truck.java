/*
 * @author: Man Hua, Chu
 * @Email: manhuac@unimelb.student.edu.au
 * @StudentNo.: 1403798
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Truck extends ReservedVehicle {

    // default constructor
    public Truck() {
        super();
        setType("Truck");
        setParkingFee(10);
        setHitFee(50);
        setOvernightFee(20);
    }

    // set reserved spot for truck
    public void setReservedSpot() {
        for(int i = 0; i < getLayout().length; ++i) {
            for(int j = 0; j < getLayout()[0].length; ++j) {
                if(getLayout()[i][j].equals(".") && j == 1) {
                    reservedSpot.add(new int[] {i, j});
                }
            }
        }
    }
}
