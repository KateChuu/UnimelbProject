import controllers.*;
import entities.*;
import exceptions.InvalidLocationException;

import java.io.File;
import java.util.*;
/**
 *   This class is the entry point of my code. This class controls the flow of control for Mission Mars
 */
public class MarsHabitatApplication{
    /**
     *   The main method sets the path of Mars habitat and log file, then directs the user to the main menu.
     *   @param args The command line arguments which may include file name and log file name.
     */
    public static void main(String[] args) {
        MarsHabitatApplication habitat = new MarsHabitatApplication();
        // display the welcome message
        habitat.displayMessage();
        Scanner keyboard = new Scanner(System.in);
        MarsHabitatController marsController = new MarsHabitatController();
        FileController fileController = new FileController();
        // user enter file name of log name or both through command line
        if(args.length > 1) {
            for(int i = 0; i < args.length-1; ++i) {
                if(args[i].equals("--f")) {
                    fileController.setFilename(args[i+1]);
                }else if(args[i].equals("--l")) {
                    fileController.setLogname(args[i+1]);
                }
            }
        }
        // if user didn't enter file name through command line, ask him/her to choose a file here
        if(fileController.getFilename().equals("")) {
            System.out.println("Please enter");
            System.out.println("[1] to load Martian map from a file");
            System.out.println("[2] to load default Martian map");
            String command = keyboard.nextLine();
            // user chooses to enter a file name manually
            if(command.equals("1")) {
                System.out.println("> Enter a file name to setup Martian Land Map");
                System.out.print("> ");
                fileController.setFilename(keyboard.nextLine());
                // user chooses to use the default file
            }else if(command.equals("2")){
                System.out.print("> ");
                fileController.setFilename(FileController.DEFAULT_PATH);
            }
        }
        fileController.displayLayout(marsController);
        // calculate the total habitability only if there are more than a robot and a rover in the mars habitat
        if(!marsController.displayHabitability()) {
            System.out.println("Total Habitability Score: " + marsController.calculateHabitability());
        }
        // generate all entities in the Mars habitat
        marsController.generateEntities();
        // display main menu
        habitat.displayMainMenu(keyboard, fileController, marsController);
    }
    /**
     *   This method prints the starting welcome message.
     */
    private void displayMessage() {
        System.out.println("         __\n" +
                " _(\\    |@@|\n" +
                "(__/\\__ \\--/ __\n" +
                "   \\___|----|  |   __\n" +
                "       \\ }{ /\\ )_ / _\\\n" +
                "       /\\__/\\ \\__O (_COMP90041\n" +
                "      (--/\\--)    \\__/\n" +
                "      _)(  )(_\n" +
                "     `---''---`");
        System.out.println(
                " /$$      /$$ /$$                    /$$                           /$$      /$$                              \n" +
                        "| $$$    /$$$|__/                   |__/                          | $$$    /$$$                              \n" +
                        "| $$$$  /$$$$ /$$  /$$$$$$$ /$$$$$$$ /$$  /$$$$$$  /$$$$$$$       | $$$$  /$$$$  /$$$$$$   /$$$$$$   /$$$$$$$\n" +
                        "| $$ $$/$$ $$| $$ /$$_____//$$_____/| $$ /$$__  $$| $$__  $$      | $$ $$/$$ $$ |____  $$ /$$__  $$ /$$_____/\n" +
                        "| $$  $$$| $$| $$|  $$$$$$|  $$$$$$ | $$| $$  \\ $$| $$  \\ $$      | $$  $$$| $$  /$$$$$$$| $$  \\__/|  $$$$$$ \n" +
                        "| $$\\  $ | $$| $$ \\____  $$\\____  $$| $$| $$  | $$| $$  | $$      | $$\\  $ | $$ /$$__  $$| $$       \\____  $$\n" +
                        "| $$ \\/  | $$| $$ /$$$$$$$//$$$$$$$/| $$|  $$$$$$/| $$  | $$      | $$ \\/  | $$|  $$$$$$$| $$       /$$$$$$$/\n" +
                        "|__/     |__/|__/|_______/|_______/ |__/ \\______/ |__/  |__/      |__/     |__/ \\_______/|__/      |_______/ ");

        System.out.println();
    }
    /**
     * This method displays main menu and directs the user to the option that was chosen by him/her.
     * @param keyboard The Scanner object to read data from keyboard
     * @param fileController The file controller to handle all the file operations
     * @param marsController The Mars habitat controller to handle all the operations on Mars habitat
     */
    private void displayMainMenu(Scanner keyboard, FileController fileController, MarsHabitatController marsController) {
        boolean keepEnter = true;
        // keep asking user to enter the next command as long as the user didn't choose 'exit'
        while(keepEnter) {
            // print 6 commands for user to choose from
            printCommands();
            System.out.print("> ");
            String command = keyboard.nextLine();
            switch(command) {
                // move the space robot
                case("1"):
                    selectEntity(marsController, "Space Robot", keyboard, fileController);
                    break;
                // move the space rover
                case("2"):
                    selectEntity(marsController, "Space Rover", keyboard, fileController);
                    break;
                // move the martian animal
                case("3"):
                    selectEntity(marsController, "Martian animal", keyboard, fileController);
                    break;
                // check the current habitability score
                case("4"):
                    // calculate the total score only if there are more than a robot and a rover in the mars habitat
                    if(!marsController.displayHabitability()) {
                        System.out.println("Total Habitability Score: " + marsController.calculateHabitability());
                    }
                    break;
                // check the historical habitability log
                case("5"):
                    // if the default habitability log file doesn't exist, create a new one
                    if(new File(fileController.getLogname()).exists()) {
                        fileController.readHistoryLog();
                    }else {
                        System.out.println("> Habitability Status");
                        System.out.println("======================");
                        System.out.println("No Record found.");
                    }
                    break;
                // exit program
                case("6"):
                    // write the current habitability to the log file
                    fileController.saveHabitability(marsController);
                    // write the Mars habitat to a file
                    fileController.saveFile(keyboard, marsController);
                    System.out.println("Terminating the mission for now. See you next time.");
                    keepEnter = false;
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
    }
    /**
     * This method prints the 6 options for the user to choose from
     */
    private void printCommands() {
        System.out.println("Please enter");
        System.out.println("[1] to move Space Robot");
        System.out.println("[2] to move Space Rover");
        System.out.println("[3] to move Martian animals");
        System.out.println("[4] to print the current habitability stats");
        System.out.println("[5] to print the old habitability stats");
        System.out.println("[6] to exit");
    }
    /**
     * This method if for the user who
     * @param marsController The Mars habitat controller to handle all the operations on the Mars habitat
     * @param type The type the user chose among space robot, space rover and martian animal
     * @param keyboard The Scanner object to read data from keyboard
     * @param fileController The file controller to handle all the file operations
     */
    private void selectEntity(MarsHabitatController marsController, String type, Scanner keyboard, FileController fileController) {
        ArrayList<SpaceEntity> listForSelect = new ArrayList<SpaceEntity>();
        int count = 0;
        // scan teh list of space entity
        for(SpaceEntity spaceEntity: marsController.getSpaceEntitiyList()) {
            // if the type of entity corresponds the type the user chose, add this entity to the selecting list
            if(spaceEntity.getType().equals(type)) {
                listForSelect.add(spaceEntity);
            }
        }
        // there is no space entity whose type corresponds the type the user chose
        if(listForSelect.size() == 0) {
            String[] splited = type.split(" ");
            splited[0] = splited[0].substring(0, 1).toLowerCase() + splited[0].substring(1, splited[0].length());
            splited[1] = splited[1].substring(0, 1).toLowerCase() + splited[1].substring(1, splited[1].length());
            System.out.println("No " + splited[0] + " " + splited[1] + " found to move.");
            return;
        }
        // print the space entities for user to choose from
        System.out.println("There are "+ listForSelect.size() + " " + type + " found. Select");
        for(SpaceEntity spaceEntity: listForSelect) {
            if(spaceEntity instanceof MartianAnimal) {
                System.out.println("[" + (++count) + "] " + "for " + ((MartianAnimal)spaceEntity).getName() + " at position ("
                        + spaceEntity.getX() + ", " + spaceEntity.getY() +")");
            }else {
                System.out.println("[" + (++count) + "] " + "for " + type + " at position ("
                        + spaceEntity.getX() + ", " + spaceEntity.getY() +")");
            }
        }
        System.out.print("> ");
        int choice = Integer.parseInt(keyboard.nextLine());
        SpaceEntity spaceEntity = listForSelect.get(choice-1);
        boolean done = false;
        // the user can keep entering commands to control the space entity
        while(!done) {
            try{
                done = (spaceEntity).move(keyboard, marsController);
                if(!done) {
                    fileController.displayLayout(marsController);
                }
            }catch (InvalidLocationException e) {
                System.out.println(e.getMessage());
                done = false;
            }
        }
    }
}