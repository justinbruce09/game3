package com.pending.game3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.spec.ECField;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Game3 {
    public static final String jsonDir = "resources/json";
    public static final String mainSplash = "Insert Splash\nScreen Graphic\nHere!";
    private FileParser fileParser;
    private InputParser inputParser;
    private List<String> inventory;
    private Room currentRoom;
    private HashMap<String, Room> rooms;
    private HashMap<String, Item> items;
    private List<Npc> npcs;
    private Scanner reader;

    //singleton
    private static Game3 instance;
    public static void runProgram() {
        if (instance == null) {
            instance = new Game3();
        }
        instance.run();
    }

    private Game3(){}
    //end singleton

    //accessors
    static void setInventory(List<String> newInventory){
        instance.inventory = newInventory;
    }

    static void setCurrentRoom(Room newCurrentRoom){
        instance.currentRoom = newCurrentRoom;
    }

    static void setRooms(HashMap<String, Room> newRooms){
        instance.rooms = newRooms;
    }

    static void setItems(HashMap<String, Item> newItems){
        instance.items = newItems;
    }

    static void setNpcs(List<Npc> newNpcs){
        instance.npcs = newNpcs;
    }

    static List<String> getInventory(){
        return instance.inventory;
    }

    static Room getCurrentRoom(){
        return instance.currentRoom;
    }

    static HashMap<String, Room> getRooms(){
        return instance.rooms;
    }

    static HashMap<String, Item> getItems(){
        return instance.items;
    }

    static List<Npc> getNpcs(){
        return instance.npcs;
    }




    private void run() {
        inputParser = new InputParser();
        reader = new Scanner(System.in);
        System.out.println(mainSplash);
        try (Stream<Path> stream = Files.list(Path.of(jsonDir))) {
            List<Path> files = getJsonList(stream);
//            printFiles(files);
            if (promptUserForFile(reader, files)) return;
        } catch (Exception e) {
            System.out.println("Unable to locate resources\\json folder.");
            return;
        }
        inventory = fileParser.startingInventory;
        rooms = fileParser.roomsAtStart;
        items = fileParser.itemsAtStart;
        currentRoom = rooms.get(fileParser.startingRoom);
        npcs = fileParser.npcsAtStart;
        mainLoop();
    }

    private void mainLoop() {
        while (true) {
            displayRoom();
            if(inputParser.getInput(reader)) {
                break;
            }
        }
    }

    private void displayRoom() {
        System.out.println(getCurrentRoom().description);
        System.out.println("Items: " + getCurrentRoom().items);
        System.out.println("Inventory: " + getInventory());
    }

    private boolean promptUserForFile(Scanner reader, List<Path> files) {

        // Dummy code for Iteration 1, delete when ready to uncomment real code below
        try {
            fileParser = FileParser.loadFile(Path.of(jsonDir));
        } catch (Exception e) {
            System.out.println("FileParser error");
        }

        //Real code for files, uncomment when ready
//        while (true){
//            System.out.print("Enter a number to select a json file to load: ");
//            String input = reader.nextLine();
//            if ("quit".equals(input.toLowerCase())) return true;
//            try{
//                int inputIndex = Integer.parseInt(input) - 1;
//                fileParser = FileParser.loadFile(files.get(inputIndex));
//                break;
//            } catch (Exception e) {
//                System.out.println("Invalid input, please try again.");
//            }
//        }
        return false;
    }

    private void printFiles(List<Path> files) {
        for (int i = 0; i < files.size(); i++){
            System.out.println("[" + (1 + i) + "]: " + files.get(i).getFileName());
        }
    }

    private List<Path> getJsonList(Stream<Path> stream) {
        return stream.filter(file -> (!Files.isDirectory(file))
                && file.getFileName().toString().contains(".json"))
                .collect(Collectors.toList());
    }
}