package com.pending.game3;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Game3 {
    public static final String jsonDir = "/../resources/json";
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
        System.out.println(mainSplash);
//        List<File> files = Stream.of(new File(jsonDir).listFiles())
//                .filter(file -> !(file.isDirectory()) && file.getName().contains(".json"))
//                .collect(Collectors.toList());
//
//        for (int i = 0; i < files.size(); i++){
//            System.out.println("[" + (1 + i) + "]: " + files.get(i).getName());
//        }

        String fileName = "";
        //fileParser = FileParser.loadFile(fileName);
    }
}