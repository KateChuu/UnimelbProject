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
 * This class represents the martian animal in the Mars habitat.
 */
public class MartianAnimal extends SpaceEntity{
    // name of martian animal, such as HEEBIE and JEEBIE
    private String name;
    private int health;
    private boolean died;
    private final static int DAMAGE = 2;
    /**
     * This constructor initialises the martian animal.
     */
    public MartianAnimal(int x, int y, String type, String name) {
        super(x, y, type);
        this.name = name;
        this.health = 15;
        this.died = false;
    }
    /**
     * This method returns the health of the martian animal.
     * @return the health
     */
    public int getHealth() {
        return health;
    }
    /**
     * This method increases the health of the martian animal by health parameter.
     * @param health The health to be added
     */
    public void addHealth(int health) {
        this.health += health;
    }
    /**
     * This method returns the name of the martian animal.
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * This method returns the boolean which indicates the martian animal is dead or alive.
     * @return the boolean
     */
    public boolean getDied() {
        return this.died;
    }
    /**
     * This method sets the boolean which indicates the martian animal is dead or alive.
     * @param died The boolean which indicates the martian animal is dead or alive
     */
    public void setDied(boolean died) {
        this.died = died;
    }
    /**
     * This method moves the martian animal to the new position on the Mars habitat formally if the move is valid, and vice versa.
     * @param keyboard The Scanner object to read data from keyboard
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     * @throws InvalidLocationException This exception shows when the martian animal reaches boundaries
     */
    @Override
    public void checkMoveValidity(Scanner keyboard, MarsHabitatController marsController) throws InvalidLocationException{
        // nextStep indicates what the martian animal will encounter in the next position
        String nextStep = whatsOnPosition(marsController);
        boolean validAction;
        try{
            validAction = this.performAction(keyboard, nextStep, marsController);
            // if the martian animal bumps into the boundaries
        }catch (InvalidLocationException e) {
            setX(getPreviousX());
            setY(getPreviousY());
            throw new InvalidLocationException();
        }
        // the martian animal can move
        if(validAction) {
            marsController.modifyCell(getPreviousX(), getPreviousY(), '.');
            marsController.modifyCell(getX(), getY(), getName().equals("JEEBIE")? 'J': 'H');
            // the martian animal can't move, so it goes back to the previous position
        }else if(getDied() == false){
            setX(getPreviousX());
            setY(getPreviousY());
        }else {
            // the martian animal died, remove it from the Mars habitat and space entity list
            marsController.removeSpaceEntityList(this);
            // change the position of the dead martian animal to '.'
            marsController.modifyCell(getPreviousX(), getPreviousY(), '.');
        }
    }
    /**
     * This method performs the actions that the martian animal can do, such as fighting with a dog.
     * @param keyboard The Scanner object to read data from keyboard
     * @param nextStep The string to indicate what entity the martian animal will encounter in this position
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     * @return a boolean which is true if the martian animal can move
     * @throws InvalidLocationException This exception shows when the martian animal reaches boundaries
     */
    @Override
    public boolean performAction(Scanner keyboard, String nextStep, MarsHabitatController marsController) throws InvalidLocationException {
        switch(nextStep) {
                // the martian animal reaches the boundary
            case("boundary"):
                throw new InvalidLocationException();
                // the martian animal bumps into a robot/rover/rock/mineral
            case("occupied"):
            case("mineral"):
            case("rock"):
                System.out.println("You cannot move to this location.");
                break;
                // the martian animal kills the cattle in the current position
            case("cattle"):
                System.out.println("The cattle are killed by the martian animals.");
                addHealth(2);
                return true;
                // the martian animal fights with the dog
            case("dog"):
                for(Dog dog: marsController.getDogList()) {
                    if(dog.getX() == this.getX() && dog.getY() == this.getY()) {
                        // the martian animal dies if it loses
                        setDied(fight(dog));
                        // the martian animal is still alive, which means dog is dead, so remove dog from dog list and mars habiatat
                        if(getDied() == false) {
                            marsController.removeDogList(dog);
                            marsController.modifyCell(getX(), getY(), '.');
                            return false;
                        }
                        // martian animal died, increase habitability by 7
                        setDied(true);
                        marsController.addExtraHabitability(7);
                        return false;
                    }
                }
                // the martian animal can eat the plant
            case("plant"):
                System.out.println("The plantation are eaten by the martian animals.");
                addHealth(2);
                return true;
                // the martian animal can move to an empty position
            case("empty"):
                return true;
            default:
                break;
        }
        return false;
    }
    /**
     * This method makes the martian animal and the dog fight with each other.
     * @param dog The dog which fights with the martian animal
     * @return a boolean to indicate the martian animal is dead or not
     */
    public boolean fight(Dog dog) {
        System.out.println("Martian animal and Dog has entered a fight");
        // as long as the health of martian animal > 0, it keeps fighting
        while(this.getHealth() > 0) {
            // martian animal attacks dog
            dog.addHealth(-DAMAGE);
            System.out.println("Martian Animal attacked dog. Health of dog reduced by " + DAMAGE
                    + ", Present Health: " + dog.getHealth());
            // if the health of dog < 0, fight ends immediately
            if(dog.getHealth() <= 0) {
                System.out.println("Dog died\n");
                return false;
            }
            // the dog is still alive, attacking the martian animal
            this.addHealth(-DAMAGE);
            System.out.println("Dog attacked Martian Animal. Martian Animal's health reduced by " + DAMAGE
                    + ", Present Health: " + this.getHealth());
        }
        // the martian animal is dead
        System.out.println("Martian Animal died\n");
        return true;
    }
}