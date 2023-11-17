/*
 * @author: Man Hua, Chu
 * @Email: manhuac@unimelb.student.edu.au
 * @StudentNo.: 1403798
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Motorbike extends ReservedVehicle {

    public Motorbike() {
        super();
        setType("Motorbike");
        setParkingFee(3);
        setHitFee(10);
        setOvernightFee(5);
    }

    // set reserved spot for bike/motorbike
    public void setReservedSpot() {
        for(int i = 0; i < getLayout().length; ++i) {
            for(int j = 0; j < getLayout()[0].length; ++j) {
                if(getLayout()[i][j].equals(".")) {
                    if(j % 2 == 1 && j == getLayout().length-2) {
                        reservedSpot.add(new int[] {i, j});
                    }else if(j == getLayout().length-3) {
                        reservedSpot.add(new int[] {i, j});
                    } 
                }
            }
        }
    }
}
