/**
 * @author: Man Hua, Chu
 * @email: manhuac@student.unimelb.edu.au
 * @studentNumber: 1403798
 */
package entities;

import java.util.Scanner;
import controllers.MarsHabitatController;
import exceptions.InvalidLocationException;

/**
 * This class represents the space entity in the Mars habitat.
 */
public abstract class SpaceEntity extends Entity{
    private int previousX; // x coordinate before moving
    private int previousY; // y coordinate before moving
    private int moveX; // x offsets
    private int moveY; // y offsets
    /**
     * This constructor initialises the space entity.
     */
    public SpaceEntity(int x, int y, String type) {
        super(x, y, type);
        this.previousX = 0;
        this.previousY = 0;
        this.moveX = 0;
        this.moveY = 0;
    }
    /**
     * This method returns the previous x coordinate of the space entity.
     * @return the x coordinate
     */
    public int getPreviousX() {
        return this.previousX;
    }
    /**
     * This method returns the previous y coordinate of the space entity.
     * @return the y coordinate
     */
    public int getPreviousY() {
        return this.previousY;
    }
    /**
     * This method sets the previous x coordinate of the space entity.
     * @param x the previous x coordinate
     */
    public void setPreviousX(int x) {
        this.previousX = x;
    }
    /**
     * This method sets the previous y coordinate of the space entity.
     * @param y the previous y coordinate
     */
    public void setPreviousY(int y) {
        this.previousY = y;
    }
    /**
     * This method returns the horizontal movement of the space entity.
     * @return the horizontal movement of the space entity
     */
    public int getMoveX() {
        return this.moveX;
    }
    /**
     * This method returns the vertical movement of the space entity.
     * @return the vertical movement of the space entity
     */
    public int getMoveY() {
        return this.moveY;
    }
    /**
     * This method sets the horizontal movement of the space entity.
     * @param moveX the horizontal movement of the space entity
     */
    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }
    /**
     * This method sets the horizontal movement of the space entity.
     * @param moveY the horizontal movement of the space entity
     */
    public void setMoveY(int moveY) {
        this.moveY = moveY;
    }
    /**
     * This method moves the space entity according to the choice of the user, and calls another method to check the movement is valid.
     * @param keyboard The Scanner object to read data from keyboard
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     * @return a boolean which is false if the space entity hasn't done moving
     * @throws InvalidLocationException This exception shows up when the space entity reaches boundaries
     */
    public boolean move(Scanner keyboard, controllers.MarsHabitatController marsController) throws InvalidLocationException {
        // set the current position to be the previous position
        setPreviousX(getX());
        setPreviousY(getY());
        setMoveX(0);
        setMoveY(0);
        if(this instanceof MartianAnimal) {
            System.out.println("MartianAnimal can move in following directions");
        }else {
            System.out.println((this instanceof SpaceRobot? "SpaceRobot": "SpaceRover") + " can move in following directions");
        }
        // print the 8 directions for user to choose from
        printCommands();
        System.out.print("> ");
        String command = keyboard.nextLine();
        // space entity move based on user's choice
        switch(command) {
            case("1"):
                setMoveY(-1);
                break;
            case("2"):
                setMoveX(-1);
                break;
            case("3"):
                setMoveX(1);
                break;
            case("4"):
                setMoveY(1);
                break;
            case("5"):
                setMoveX(-1);
                setMoveY(-1);
                break;
            case("6"):
                setMoveX(-1);
                setMoveY(1);
                break;
            case("7"):
                setMoveX(1);
                setMoveY(-1);
                break;
            case("8"):
                setMoveX(1);
                setMoveY(1);
                break;
            case("0"):
                return true;
            default:
                return false;
        }
        setX(getX() + getMoveX());
        setY(getY() + getMoveY());
        // check the movement is valid or not
        try{
            this.checkMoveValidity(keyboard, marsController);
            // if the space entity reaches boundary, throws exception
        }catch (InvalidLocationException e) {
            throw new InvalidLocationException();
        }
        return false;
    }
    /**
     * This method prints the 8 directions for the user to choose from.
     */
    public void printCommands() {
        System.out.println("[1] to move north.");
        System.out.println("[2] to move west.");
        System.out.println("[3] to move east.");
        System.out.println("[4] to move south.");
        System.out.println("[5] to move north-west.");
        System.out.println("[6] to move south-west.");
        System.out.println("[7] to move north-east.");
        System.out.println("[8] to move south-east.");
        System.out.println("[0] to go back to main menu");
        System.out.println("Please enter a direction.");
    }
    /**
     * This method checks what is on the current position of space entity.
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     * @return the name of the entity on the current position
     */
    public String whatsOnPosition(controllers.MarsHabitatController marsController){
        char currentSymbol = marsController.getMarsHabitat().get(getY())[getX()];
        switch(currentSymbol) {
            case('#'):
                return "boundary";
            case('C'):
            case('G'):
            case('S'):
                return "cattle";
            case('D'):
                return "dog";
            case('P'):
            case('T'):
            case('O'):
            case('R'):
            case('L'):
            case('A'):
            case('B'):
            case('E'):
                return "plant";
            case('.'):
                return "empty";
            case('@'):
                return "rock";
            case('*'):
                return "mineral";
            default:
                return "occupied";
        }
    }
    /**
     * Please refer to the concrete method in the derived class
     */
    abstract boolean performAction(Scanner keyboard, String nextStep, MarsHabitatController marsController) throws InvalidLocationException;
    /**
     * Please refer to the concrete method in the derived class
     */
    abstract void checkMoveValidity(Scanner keyboard, MarsHabitatController marsController) throws InvalidLocationException;
}