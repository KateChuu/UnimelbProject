/**
 * @author: Man Hua, Chu
 * @email: manhuac@student.unimelb.edu.au
 * @studentNumber: 1403798
 */
package controllers;

import entities.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.Map.entry;

/**
 * This class handles all the operation about the Mars habitat, such modifying the symbol in a cell.
 */
public class MarsHabitatController {
    private ArrayList<char[]> marsHabitat;
    private HashMap<Character, Integer> quantityMap; // store the name and quantity pair of each entity
    private int extraHabitability;
    private ArrayList<entities.SpaceEntity> spaceEntitiyList;
    private ArrayList<entities.Dog> dogList;
    private final static Character[] HABITATABLE_ENTITIES = new Character[] {
            'P', '*', 'S', 'L', 'E', 'O', 'R', 'A', 'B', 'C', 'G', 'D', 'T'};
    private final static Map<Character, Integer> SCORE_MAP = Map.ofEntries(
            entry('P', 2),
            entry('T', 2),
            entry('O', 2),
            entry('R', 2),
            entry('L', 2),
            entry('A', 2),
            entry('B', 2),
            entry('E', 2),
            entry('C', 5),
            entry('G', 5),
            entry('S', 5),
            entry('D', 5),
            entry('@', 1),
            entry('*', 2)
    );
    final static Map<Character, String> NAME_MAP = Map.ofEntries(
            entry('P', "POTATO"),
            entry('T', "TOMATO"),
            entry('O', "ONION"),
            entry('R', "ROSE"),
            entry('L', "LILY"),
            entry('A', "APPLE"),
            entry('B', "BANANA"),
            entry('E', "EUCALYPTUS"),
            entry('C', "COW"),
            entry('G', "GOAT"),
            entry('S', "SHEEP"),
            entry('D', "DOG"),
            entry('@', "ROCK"),
            entry('*', "MINERAL")
    );
    /**
     * This constructor initialises the Mars habitat controller.
     */
    public MarsHabitatController() {
        this.marsHabitat = new ArrayList<char[]> ();
        this.quantityMap = new LinkedHashMap<>();
        this.spaceEntitiyList = new ArrayList<SpaceEntity> ();
        this.dogList = new ArrayList<Dog> ();
        this.extraHabitability = 0;
        // initialise the quantity of each entity to be 0 and put entities in order
        resetQuantityMap();
    }
    /**
     * This method returns the quantity of entities in the Mars habitat.
     * @return the quantity of entities in the Mars habitat
     */
    @SuppressWarnings("unchecked")
    public HashMap<Character, Integer> getQuantityMap() {
        return this.quantityMap;
    }
    /**
     * This method resets the quantity of all entities to 0 except for the mineral.
     */
    public void resetQuantityMap() {
        for(char c: HABITATABLE_ENTITIES) {
            // the quantity of mineral in the Mars habitat remains the same even if it is collected by rover
            if(c == '*' && quantityMap.containsKey('*') && quantityMap.get('*') != 0) {
                continue;
            }
            quantityMap.put(c, 0);
        }
    }
    /**
     * This method returns the list of space entities in the Mars habitat.
     * @return the list of space entities in the Mars habitat
     */
    @SuppressWarnings("unchecked")
    public ArrayList<entities.SpaceEntity> getSpaceEntitiyList() {
        ArrayList temp = new ArrayList(spaceEntitiyList);
        return temp;
    }

    /**
     * This method adds a space entity in the list of the space entities.
     * @param spaceEntity The space entity to be added
     */
    public void addSpaceEntityList(entities.SpaceEntity spaceEntity) {
        this.spaceEntitiyList.add(spaceEntity);
    }

    /**
     * This method removes a space entity in the list of the space entities.
     * @param spaceEntity The space entity to be removed
     */
    public void removeSpaceEntityList(entities.SpaceEntity spaceEntity) {
        this.spaceEntitiyList.remove(spaceEntity);
    }
    @SuppressWarnings("unchecked")
    public ArrayList<entities.Dog> getDogList() {
        ArrayList temp = new ArrayList(dogList);
        return temp;
    }
    /**
     * This method adds a dog in the list of dogs.
     * @param dog The dog to be added
     */
    public void addDogList(entities.Dog dog) {
        this.dogList.add(dog);
    }
    /**
     * This method removes a dog in the list of dogs.
     * @param dog The dog to be removed
     */
    public void removeDogList(entities.Dog dog) {
        this.dogList.remove(dog);
    }
    /**
     * This method returns the extra habitability, which is not visible in the Mars habitat, such as watering a plant or feeding a cattle.
     * @return the extra habitability
     */
    public int getExtraHabitability() {
        return this.extraHabitability;
    }
    /**
     * This method adds the extra habitability to the total habitability.
     * @param extraHabitability the extra habitability
     */
    public void addExtraHabitability(int extraHabitability) {
        this.extraHabitability += extraHabitability;
    }
    /**
     * This method modifies the cell in the Mars habitat to a specific symbol.
     * @param x The x coordinate
     * @param y The y coordinate
     * @param symbol The symbol to over-write the old symbol
     */
    public void modifyCell(int x, int y, char symbol) {
        this.marsHabitat.get(y)[x] = symbol;
    }
    /**
     * This method is used when setting up the Mars habitat, adding a row in the Mars habitat.
     * @param row The row to be added in the Mars habitat
     */
    public void addMarsHabitat(char[] row) {
        this.marsHabitat.add(row);
    }
    /**
     * This method returns the Mars habitat
     * @return the Mars habitat
     */
    @SuppressWarnings("unchecked")
    public ArrayList<char[]> getMarsHabitat() {
        ArrayList temp = new ArrayList(marsHabitat);
        return temp;
    }
    /**
     * This method generates the space entities and dogs in the Mars habitat.
     */
    public void generateEntities() {
        // go through the Mars habitat and check the symbols
        for(int y = 0; y < getMarsHabitat().size(); y++) {
            char[] row = getMarsHabitat().get(y);
            for(int x = 0; x < row.length; x++) {
                switch(row[x]) {
                    case('Z'):
                        // if the symbol is 'Z', create a space robot
                        addSpaceEntityList(new SpaceRobot(x, y, "Space Robot"));
                        break;
                    case('X'):
                        // if the symbol is 'X', create a space rover
                        addSpaceEntityList(new SpaceRover(x, y, "Space Rover"));
                        break;
                    case('H'):
                        // if the symbol is 'H', create a martian animal whose name is HEEBIE
                        addSpaceEntityList(new MartianAnimal(x, y, "Martian animal", "HEEBIE"));
                        break;
                    case('J'):
                        // if the symbol is 'J', create a martian animal whose name is JEEBIE
                        addSpaceEntityList(new MartianAnimal(x, y, "Martian animal", "JEEBIE"));
                        break;
                    case('D'):
                        // if the symbol is 'D', create a dog
                        addDogList(new Dog(x, y, "Dog"));
                        break;
                    default:
                        break;
                }
            }
        }
    }
    /**
     * This method counts the quantity of each entity in the Mars habitat, except for the mineral.
     */
    public void countEntities() {
        resetQuantityMap();
        for(char[] charArray: getMarsHabitat()) {
            for(char c: charArray) {
                // the quantity of mineral in the Mars habitat should remain the same, don't change it
                if(c == '*' && getQuantityMap().get(c) != 0) {
                    continue;
                }
                // this entity is in the Mars habitat, so quantity increase by 1
                if(getQuantityMap().containsKey(c)) {
                    quantityMap.put(c, quantityMap.get(c) + 1);
                }
            }
        }
    }
    /**
     * This method displays the habitability status.
     * @return a boolean which is true when the habitability status is not empty.
     */
    public boolean displayHabitability() {
        countEntities();
        HashMap<Character, Integer> entities = getQuantityMap();
        System.out.println("Habitability Status");
        System.out.println("======================");
        // check whether there are more than a robot and a rover in the Mars habitat
        boolean noRecord = true;
        for (char c : entities.keySet() ) {
            if(entities.get(c) > 0) {
                noRecord = false;
                break;
            }
        }
        if(noRecord) {
            System.out.println("No Record found.");
            return noRecord;
        }
        // there is at least one entity in the Mars habitat, print the names and quantities of entities
        for (char c : entities.keySet() ) {
            if(entities.get(c) > 0) {
                System.out.println(NAME_MAP.get(c) + " = " + entities.get(c));
            }
        }
        System.out.println("");
        return noRecord;
    }
    /**
     * This method calculates the total habitability score of the Mars habitat.
     * @return the total habitability score
     */
    public int calculateHabitability() {
        // the scores which are visible in the Mars habitat for users
        int visibleSum = 0;
        for(char c: quantityMap.keySet()) {
            int quantity = quantityMap.get(c);
            if(quantity > 0 && c != '*') {
                visibleSum += quantity * SCORE_MAP.get(c);
            }
        }
        // add the extra scores such as watering, feeding and collecting to the visible sum
       return visibleSum + getExtraHabitability();
    }
}