package com.pending.game3;

import java.util.Scanner;

class InputParser {
    //create a scanner object
//    Scanner userInput = new Scanner(System.in);
    // getInput()
    void getInput(Scanner userInput){
        System.out.print("Enter Command\n> "); // allows input command to be on next line
        String input = userInput.nextLine(); // Reads user input

        String[] inputSplit = input.split(" ", 2);
        System.out.println(inputSplit[0] + "," + inputSplit[1]);
        SynonymDictionary command;
//        try{
//            command = SynonymDictionary.valueOf(inputSplit[0]);
//        } catch (Exception e){
//            if (SynonymDictionary.GO.synonmys.contains(inputSplit[0])) {
//                command = SynonymDictionary.GO;
//            } //else if (check the rest of SynonymDict for matches)
//            else {
//                System.out.println("command " + input +
//                        " not recognized. enter \"Help\" for a list of valid commands.");
//            }
//        }
//        switch (command){
//            case GO:
//                //Check inputSplit[1] against available directions, then get that destination room
//                //goToRoom(destination);
//                break;
//
//
//            default:
//                System.out.println("Command not yet supported");
//        }
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