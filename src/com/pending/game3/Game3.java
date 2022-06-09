package com.pending.game3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Game3 {
    public static final String jsonDir = "resources/json";
    public static final String mainSplash = "Insert Splash\nScreen Graphic\nHere!";
    private FileParser fileParser;
    private InputParser inputParser;
    private String[] inventory;
    private Room currentRoom;
    private Room[] rooms;
    private Item[] items;
    private Npc[] npcs;

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

    private void run() {
        Scanner reader = new Scanner(System.in);
        System.out.println(mainSplash);
        try (Stream<Path> stream = Files.list(Path.of(jsonDir))) {
            List<Path> files = getJsonList(stream);
            printFiles(files);
            if (promptUserForFile(reader, files)) return;
        } catch (Exception e) {
            System.out.println("Unable to locate resources\\json folder.");
            return;
        }


    }

    private boolean promptUserForFile(Scanner reader, List<Path> files) {
        while (true){
            System.out.print("Enter a number to select a json file to load: ");
            String input = reader.nextLine();
            if ("quit".equals(input.toLowerCase())) return true;
            try{
                int inputIndex = Integer.parseInt(input) - 1;
                fileParser = FileParser.loadFile(files.get(inputIndex));
                break;
            } catch (Exception e) {
                System.out.println("Invalid input, please try again.");
            }
        }
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