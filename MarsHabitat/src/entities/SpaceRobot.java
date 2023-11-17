/**
 * @author: Man Hua, Chu
 * @email: manhuac@student.unimelb.edu.au
 * @studentNumber: 1403798
 */
package entities;

import controllers.MarsHabitatController;
import exceptions.InvalidLocationException;

import java.util.Map;
import java.util.Scanner;
import static java.util.Map.entry;

/**
 * This class represents the space robot in the Mars habitat.
 */
public class SpaceRobot extends SpaceEntity {
    private final static Map<Integer, String> PLANT_OPTION_MAP = Map.ofEntries(
            entry(1, "Potato"),
            entry(2, "Tomato"),
            entry(3, "Onion"),
            entry(4, "Apple tree"),
            entry(5, "Banana tree"),
            entry(6, "Lily"),
            entry(7, "Rose"),
            entry(8, "Eucalyptus")
    );
    private final static Map<Integer, String> ANIMAL_OPTION_MAP = Map.ofEntries(
            entry(1, "Goat"),
            entry(2, "Sheep"),
            entry(3, "Cow"),
            entry(4, "Dog")
    );
    /**
     * This constructor initialises the space robot
     */
    public SpaceRobot(int x, int y, String type) {
        super(x, y, type);
    }
    /**
     * This method moves the space robot to the new position on the Mars habitat formally if the move is valid, and vice versa.
     * @param keyboard The Scanner object to read data from keyboard
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     * @throws InvalidLocationException This exception shows up when the space robot reaches boundaries
     */
    @Override
    public void checkMoveValidity(Scanner keyboard, MarsHabitatController marsController) throws InvalidLocationException{
        // nextStep indicates what the space robot will encounter in the next position
        String nextStep = whatsOnPosition(marsController);
        boolean validAction;
        try{
            validAction = this.performAction(keyboard, nextStep, marsController);
            // if the space robot bumps into the boundaries
        }catch (InvalidLocationException e) {
            setX(getPreviousX());
            setY(getPreviousY());
            throw new InvalidLocationException();
        }
        // the space robot can't move, so it goes back to the previous position
        if(!validAction) {
            setX(getPreviousX());
            setY(getPreviousY());
            // the space robot can move
        }else {
            // change the symbol in the Mars habitat
            marsController.modifyCell(getPreviousX(), getPreviousY(), '.');
            marsController.modifyCell(getX(), getY(), 'Z');
            // check the next position of this new position is empty
            setPreviousX(getX());
            setPreviousY(getY());
            setX(getX() + getMoveX());
            setY(getY() + getMoveY());
            nextStep = whatsOnPosition(marsController);
            // if the next position of this new position is empty
            if(nextStep.equals("empty")) {
                // the space robot can plant or rear something
                char added = subMenu(keyboard);
                // the space robot did add a plantation or a cattle
                if(added != '\0') {
                    marsController.modifyCell(getX(), getY(), added);
                    // if the space robot added a dog, put the new dog in the dog list
                    if(added == 'D') {
                        marsController.addDogList(new Dog(getX(), getY(), "Dog"));
                    }
                }
            }
            // after adding something or nothing, the space robot goes back to the current position
            setX(getPreviousX());
            setY(getPreviousY());
        }
    }
    /**
     * This method performs the actions that the space robot can do, such as watering a plant.
     * @param keyboard The Scanner object to read data from keyboard
     * @param nextStep The string to indicate what entity the space robot will encounter in this position
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     * @return a boolean which is true if the space robot can move
     * @throws InvalidLocationException This exception shows up when the space robot reaches boundaries
     */
    @Override
    public boolean performAction(Scanner keyboard, String nextStep, MarsHabitatController marsController) throws InvalidLocationException {
        boolean done = false;
        switch(nextStep) {
                // the space robot reaches the boundary
            case("boundary"):
                throw new InvalidLocationException();
                // the space robot bumps into a martian animal/rover/rock/mineral
            case("occupied"):
            case("mineral"):
            case("rock"):
                System.out.println("You cannot move to this location.");
                break;
                // the space robot encounters a cattle, it can choose to feed the cattle or not
            case("cattle"):
            case("dog"):
                while(!done) {
                    System.out.println("Do you want to feed the animals?Enter Y for yes, N for No");
                    System.out.print("> ");
                    String answer = keyboard.nextLine();
                    // the space robot wants to feed the cattle
                    if(answer.equals("Y")) {
                        done = true;
                        // if the cattle is a dog, increase the health of that dog by 1
                        if(nextStep.equals("dog")) {
                            for(Dog dog: marsController.getDogList()) {
                                if(dog.getX() == this.getX() && dog.getY() == this.getY()) {
                                    dog.addHealth(1);
                                    break;
                                }
                            }
                        }
                        // the habitability goes up by 1
                        System.out.println("You have fed the cattle. It will grow");
                        marsController.addExtraHabitability(1);
                        // the space robot doesn't want to feed the cattle
                    }else if(answer.equals("N")) {
                        return false;
                    }else {
                        System.out.println("Invalid Command");
                    }
                }
                break;
                // the space robot encounters a plant, it can choose to water the plant or not
            case("plant"):
                while(!done) {
                    System.out.println("Do you want to water the plant?Enter Y for yes, N for No");
                    System.out.print("> ");
                    String answer = keyboard.nextLine();
                    // the space robot wants to water the plant
                    if(answer.equals("Y")) {
                        done = true;
                        // the habitability goes up by 1
                        System.out.println("You watered a plant. It will grow");
                        marsController.addExtraHabitability(1);
                        // the space robot doesn't want to water the plant
                    }else if(answer.equals("N")) {
                        return false;
                    }else {
                        System.out.println("Invalid Command");
                    }
                }
                break;
                // the space robot can move to an empty position
            case("empty"):
                return true;
            default:
                break;
        }
        return false;
    }
    /**
     * This method plants/rears a plantation/cattle/nothing in the Mars habitat.
     * @param keyboard The Scanner object to read data from keyboard
     * @return the entity added by the space robot
     */
    public char subMenu(Scanner keyboard) {
        String command = "";
        char added = '\0';
        do {
            System.out.println("Please select\n" +
                    "[1] to plant a tree\n" +
                    "[2] to rear cattle\n" +
                    "[0] to go back to previous menu");
            System.out.print("> ");
            command = keyboard.nextLine();
            // the space robot chooses to plant something
            if(command.equals("1")) {
                System.out.println("Let's Plant something\n" +
                        "[1] to plant a potato\n" +
                        "[2] to plant a tomato\n" +
                        "[3] to plant an onion\n" +
                        "[4] to plant an apple tree\n" +
                        "[5] to plant a banana tree\n" +
                        "[6] to plant a lily\n" +
                        "[7] to plant a rose\n" +
                        "[8] to plant a eucalyptus tree\n" +
                        "[0] to go back to previous menu");
                String option = keyboard.nextLine();
                int i = Integer.parseInt(option);
                if(i == 0) {
                    return added;
                }
                // set the symbol which is going to be added in the Mars habitat to the one user chooses
                added = PLANT_OPTION_MAP.get(i).charAt(0);
                System.out.println("A " + PLANT_OPTION_MAP.get(i) + " has been planted.");
                // the space robot chooses to rear something
            }else if(command.equals("2")) {
                System.out.println("Let's add some cattle\n" +
                        "[1] to add a goat\n" +
                        "[2] to add a sheep\n" +
                        "[3] to add cow\n" +
                        "[4] to add a dog\n" +
                        "[0] to go back to previous menu");
                String option = keyboard.nextLine();
                int i = Integer.parseInt(option);
                if(i == 0) {
                    return added;
                }
                // set the symbol which is going to be added in the Mars habitat to the one user chooses
                added = ANIMAL_OPTION_MAP.get(i).charAt(0);
                System.out.println("A " + ANIMAL_OPTION_MAP.get(i) + " has been added.");
            }
        }while(!command.equals("0"));
        return added;
    }
}