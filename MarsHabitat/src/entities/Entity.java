/**
 * @author: Man Hua, Chu
 * @email: manhuac@student.unimelb.edu.au
 * @studentNumber: 1403798
 */
package entities;

/**
 * This class represents the entity in the Mars habitat.
 */
public abstract class Entity {
    private int x;
    private int y;
    private String type;
    /**
     * This constructor initialises the entity.
     */
    public Entity(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    /**
     * This method returns the x coordinate of this entity.
     * @return the x coordinate
     */
    public int getX() {
        return this.x;
    }
    /**
     * This method returns the y coordinate of this entity.
     * @return the y coordinate
     */
    public int getY() {
        return this.y;
    }
    /**
     * This method sets the x coordinate of this entity.
     * @param x the x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * This method sets the y coordinate of this entity.
     * @param y the y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * This method returns the type of this entity
     * @return the type
     */
    public String getType() {
        return this.type;
    }
}
