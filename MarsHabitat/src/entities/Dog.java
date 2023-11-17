/**
 * @author: Man Hua, Chu
 * @email: manhuac@student.unimelb.edu.au
 * @studentNumber: 1403798
 */
package entities;

/**
 * This class represents the dog in the Mars habitat.
 */
public class Dog extends Entity {
    private int health;
    /**
     * This constructor initialises the dog.
     */
    public Dog(int x, int y, String type) {
        super(x, y, type);
        this.health = 10;
    }
    /**
     * This method returns the health of the dog.
     * @return the health of the dog
     */
    public int getHealth() {
        return health;
    }
    /**
     * This method increases the health of the dog by the parameter health.
     * @param health The health to be added to the health of the dog
     */
    public void addHealth(int health) {
        this.health += health;
    }
}
