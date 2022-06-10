package com.pending.game3;

import java.util.Locale;
import java.util.Scanner;

class InputParser {
    //create a scanner object
//    Scanner userInput = new Scanner(System.in);
    // getInput()
    boolean getInput(Scanner userInput){
        System.out.print("Enter Command\n> "); // allows input command to be on next line
        String input = userInput.nextLine().toLowerCase(); // Reads user input

        String[] inputSplit = input.split(" ", 2); // splits array in 2 after 1st space
//        System.out.println(inputSplit[0] + "," + inputSplit[1]); // everything after space is element 1
        SynonymDictionary command = null;
        try{
            command = SynonymDictionary.valueOf(inputSplit[0].toUpperCase()); //command = verb
        } catch (Exception e){
            if (SynonymDictionary.GO.synonyms.contains(inputSplit[0])) { //if a synonym of "go" in syn dict use go
                command = SynonymDictionary.GO;
            } //else if (check the rest of SynonymDict for matches)
            else {
                System.out.println("command " + input +
                        " not recognized. enter \"Info\" for a list of valid commands.");
                return false;
            }
        }
        switch (command){
//            case GO:
//                //Check inputSplit[1] against available directions, then get that destination room
//                //goToRoom(destination);
//                if (destinations.contains.key (inputSplit[1]){
//                    goToRoom(destinations);
//                }
//                break;
            case INSPECT:
                if("room".equalsIgnoreCase(inputSplit[1])){
                System.out.println(Game3.getCurrentRoom().description);
                } else if (Game3.getCurrentRoom().items.contains(inputSplit[1])) {
                        System.out.println(Game3.getItems().get(inputSplit[1]).description);
                    } else {
                        System.out.println("Item does not exist");
                    }
                break;
            case TAKE:
                    if (Game3.getCurrentRoom().items.contains(inputSplit[1])) {
                    Game3.getCurrentRoom().takeItem(inputSplit[1]);
                    }
                break;
            case QUIT:
                return true;
            default:
                System.out.println("Command not yet supported");
       }
                return false;
    }

    private boolean goToRoom(Room destination) {
//        if (destination.flags.contains("Locked")) {
//            check for matching key
//            then remove "Locked" flag, move to new room, and return true;
//        } else {
        Game3.setCurrentRoom(destination);
        return true;
//        }
//        return false;
    }

}