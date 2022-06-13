package com.pending.game3;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class FileParser {
        String startingRoom;
        List<String> startingInventory;
        HashMap<String, Room> roomsAtStart;
        HashMap<String, Item> itemsAtStart;
        HashMap<String, Npc> npcsAtStart;
        List<CraftingRecipe> recipes;
        List<EndCondition> endConditions;
        private static JSONObject jsonObject;

        //ctor
        private FileParser(){
        }

        static FileParser loadFile(Path test1) throws FileNotFoundException {
                FileParser toReturn = new FileParser();
                // created parser
                Object obj = JSONValue.parse(new FileReader(String.valueOf(test1)));
                if (JSONObject.class == obj.getClass()) {
                        jsonObject = (JSONObject) obj;
                        if (toReturn.parseRooms()) return null;
                }

                //Dummy logic for Iteration 1 MVP, will replace with actual JSON parsing in Iteration 2
                toReturn.startingInventory = new ArrayList<>();
//                toReturn.roomsAtStart = new HashMap<>();
//                Room room = new Room();
//                room.description = "What appears to be an examination room with three walls, one of which " +
//                                   "is curved outward and has a door in it.";
//                room.items = new ArrayList<>();
//                room.items.add("body-scanning device");
//                room.items.add("medicine");
//                room.items.add("surgical instrument trolley");
//                room.items.add("key card");
//                room.flags = new ArrayList<>();
//                room.npcs = new ArrayList<>();
//                room.name = "examination room";
//                room.displayName = "examination room";
//                toReturn.roomsAtStart.put(room.name, room);
                toReturn.startingRoom = "examination room";
                toReturn.itemsAtStart = new HashMap<>();
                Item item = new Item();
                item.name = "body-scanning device";
                item.flags = new ArrayList<>();
                item.description = "You think this devices is intended for anatomical scanning, but it looks really" +
                        " similar to a nightstick. You could probably bash some head with it.";
                toReturn.itemsAtStart.put(item.name, item);
                item = new Item();
                item.name = "medicine";
                item.flags = new ArrayList<>();
                item.description = "This looks like an assortment of medicines, probably shouldn't use any until " +
                        "you find out what they do.";
                toReturn.itemsAtStart.put(item.name, item);
                item = new Item();
                item.name = "key card";
                item.flags = new ArrayList<>();
                List<String> flagData = new ArrayList<>();
                flagData.add("Key1") ;
                Flag flag = new Flag("Key", flagData);
                item.flags.add(flag);
                item.description = "This looks an awful lot like a key-card from one of them new-fangled SYE-FIE " +
                        "shows.";
                toReturn.itemsAtStart.put(item.name, item);
                item = new Item();
                item.name = "surgical instrument trolley";
                item.flags = new ArrayList<>();
                flagData = new ArrayList<>();
                flag = new Flag("Decor", flagData);
                item.flags.add(flag);
                item.description = "A trolley that looks like it should have an assortment of surgical tools on it.";
                toReturn.itemsAtStart.put(item.name, item);
                toReturn.npcsAtStart = new HashMap<>();
                toReturn.recipes = new ArrayList<>();
                toReturn.endConditions = new ArrayList<>();

                return toReturn;
        }

        private boolean parseRooms() {
                roomsAtStart = new HashMap<>();
                Object obj = jsonObject.get("Rooms");
                if (JSONArray.class == obj.getClass()) {
                        JSONArray jsonRooms = (JSONArray) obj;
                        for (Object roomObj : jsonRooms) {
                                //programmer.tryNotToCry();
                                //programmer.cryALot();
                                JSONObject roomJsonObj = (JSONObject) roomObj;
                                Room room = new Room();

                                room.name = parseString(roomJsonObj.get("Name"));
                                if (room.name == null) {
                                        System.out.println("Room Name.");
                                        return true;
                                }

                                if (roomJsonObj.keySet().contains("Effect Tags")) {
                                        obj = roomJsonObj.get("Effect Tags");
                                        if (JSONArray.class.equals(obj.getClass())) {
                                                JSONArray flagsJson = (JSONArray) obj;
                                                room.flags = parseFlags(flagsJson);
                                                if (room.flags == null) {
                                                        System.out.println(room.name + " Effect Tags.");
                                                        return true;
                                                }
                                        } else return true;
                                } else room.flags = new ArrayList<>();

                                if (roomJsonObj.keySet().contains("Items")) {
                                        room.items = parseStringList(roomJsonObj.get("Items"));
                                        if (room.items == null) {
                                                System.out.println("Room " + room.name + " Items.");
                                                return true;
                                        }
                                } else room.items = new ArrayList<>();

                                //parse connected rooms
                                obj = roomJsonObj.get("Connected Rooms");
                                room.connections = new HashMap<>();
                                if(JSONArray.class.equals(obj.getClass())){
                                        JSONArray connectionArray = (JSONArray)obj;
                                        for (Object derp : connectionArray) {
                                                if (JSONObject.class.equals(derp.getClass())){
                                                        JSONObject connection = (JSONObject)derp;
                                                        if(connection.keySet().size() == 1){
                                                                obj = connection.keySet().toArray()[0];
                                                                String direction = parseString(obj);
                                                                if (direction == null){
                                                                        System.out.println("Room " + room.name +
                                                                                " Connection Direction.");
                                                                        return true;
                                                                }
                                                                obj = connection.get(direction);
                                                                String destination = parseString(obj);
                                                                if (destination == null) {
                                                                        System.out.println("Room " + room.name +
                                                                                " Connection Destination.");
                                                                return true;
                                                        }
                                                                room.connections.put(direction, destination);
                                                        } else {
                                                                System.out.println("Invalid Room " + room.name +
                                                                        " Connection Object " +
                                                                        "Size, each direction:destination pair must " +
                                                                        "be a separate object and cannot be empty.");
                                                                return true;
                                                        }
                                                } else {
                                                        System.out.println("Invalid Data Type when Object was " +
                                                                "expected at Room " + room.name + " Connections");
                                                        return true;
                                                }
                                        }
                                }

                                room.description = parseString(roomJsonObj.get("Description"));
                                if (room.description == null) {
                                        System.out.println("Room " + room.name + " Description.");
                                        return true;
                                }

                                roomsAtStart.put(room.name, room);

                        }
                }
                return false;
        }

        private String parseString(Object obj) {

                if (obj == null) {
                        System.out.print("Null when String was expected at: ");
                        return null;
                }
                if (String.class.equals(obj.getClass())){
                        return (String)obj;
                } else {
                        System.out.print("Invalid Data Type when String was expected at: ");
                        return null;
                }
        }

        private List<Flag> parseFlags(JSONArray flagsJson) {
                List<Flag> toReturn = new ArrayList<>();
                if (flagsJson == null) {
                        System.out.print("Null when Flags were expected at: ");
                        return null;
                }
                for (Object loopObj : flagsJson) {
                        if (JSONObject.class == loopObj.getClass()){
                                JSONObject flagJson = (JSONObject) loopObj;
                                String name = parseString(flagJson.get("Tag Name"));
                                if (name == null) {
                                        System.out.print("Tag Name in: ");
                                        return null;
                                }
                                List<String> dataList = parseStringList(flagJson.get("Tag Data"));
                                if(dataList == null) {
                                        System.out.print("Tag Data in: ");
                                        return null;
                                }

                        } else return null;
                }
                return toReturn;
        }

        private List<String> parseStringList(Object input) {
                List<String> toReturn = new ArrayList<>();
                if (input == null) {
                        System.out.print("Null when Array of String was expected at: ");
                        return null;
                }
                if(input.getClass().equals(JSONArray.class)) {
                        JSONArray data = (JSONArray) input;
                        for(Object obj : data) {
                                String string = parseString(obj);
                                if (string == null) {
                                        System.out.print("Array of String in: ");
                                        return null;
                                }
                                else toReturn.add(string);
                        }
                }else {
                        System.out.print("Incorrect Data Type when Array of String was expected at: ");
                        return null;
                }
                return toReturn;
        }
}