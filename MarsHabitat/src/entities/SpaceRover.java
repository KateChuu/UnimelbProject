/**
 * @author: Man Hua, Chu
 * @email: manhuac@student.unimelb.edu.au
 * @studentNumber: 1403798
 */
package entities;

import controllers.MarsHabitatController;
import exceptions.InvalidLocationException;

import java.util.Scanner;

/**
 * This class represents the space rover in the Mars habitat.
 */
public class SpaceRover extends SpaceEntity {
    /**
     * This constructor initialises the space rover
     */
    public SpaceRover(int x, int y, String type) {
        super(x, y, type);
    }
    /**
     * This method moves the space rover to the new position on the Mars habitat formally if the move is valid, and vise versa.
     * @param keyboard The Scanner object to read data from keyboard
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     * @throws InvalidLocationException This exception shows when the space rover reaches boundaries
     */
    @Override
    public void checkMoveValidity(Scanner keyboard, MarsHabitatController marsController) throws InvalidLocationException {
        // nextStep indicates what the space rover will encounter in the next position
        String nextStep = whatsOnPosition(marsController);
        // the space rover can move after performing specific actions
        boolean validAction;
        try{
            validAction = this.performAction(keyboard, nextStep, marsController);
            // if the space rover bumps into the boundaries
        }catch (InvalidLocationException e) {
            setX(getPreviousX());
            setY(getPreviousY());
            throw new InvalidLocationException();
        }
        // the space robot can move
        if(validAction) {
            marsController.modifyCell(getPreviousX(), getPreviousY(), '.');
            marsController.modifyCell(getX(), getY(), 'X');
            // the space rover can't move, so it goes back to the previous position
        }else {
            setX(getPreviousX());
            setY(getPreviousY());
        }
    }
    /**
     * This method performs the actions that the space rover can do, such as collecting mineral.
     * @param keyboard The Scanner object to read data from keyboard
     * @param nextStep The string to indicate what entity the space rover will encounter in this position
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     * @return a boolean which is true if the space rover can move
     * @throws InvalidLocationException This exception shows when the space rover reaches boundaries
     */
    @Override
    public boolean performAction(Scanner keyboard, String nextStep, MarsHabitatController marsController) throws InvalidLocationException {
        switch(nextStep) {
                // the space rover reaches the boundary
            case("boundary"):
                throw new InvalidLocationException();
                // the space rover bumps into a martian animal/robot/cattle/plant
            case("occupied"):
            case("plant"):
            case("cattle"):
            case("dog"):
                System.out.println("You cannot move to this location.");
                break;
                // the space rover encounters a mineral, it collects the mineral
            case("mineral"):
                System.out.println("We found a mineral, Rover will collect it now.");
                // the habitability goes up by 2
                marsController.addExtraHabitability(2);
                return true;
                // the space rover encounters a rock, it destroys the rock
            case("rock"):
                System.out.println("We found a plain rock, Rover will destroy it now.");
                // the habitability goes up by 1
                marsController.addExtraHabitability(1);
                return true;
                // the space rover can move to an empty position
            case("empty"):
                return true;
            default:
                break;
        }
        return false;
    }
}