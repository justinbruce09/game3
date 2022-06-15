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
                        if (toReturn.parseItems()) return null;
                        if (toReturn.parseNpcs()) return null;
                        if (toReturn.parseCraftingRecipes()) return null;
                        if (toReturn.parseEndCondition()) return null;
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
                toReturn.startingRoom = "Examination Room";
//                toReturn.itemsAtStart = new HashMap<>();
//                Item item = new Item();
//                item.name = "body-scanning device";
//                item.flags = new HashMap<>();
//                item.description = "You think this devices is intended for anatomical scanning, but it looks really" +
//                        " similar to a nightstick. You could probably bash some head with it.";
//                toReturn.itemsAtStart.put(item.name, item);
//                item = new Item();
//                item.name = "medicine";
//                item.flags = new HashMap<>();
//                item.description = "This looks like an assortment of medicines, probably shouldn't use any until " +
//                        "you find out what they do.";
//                toReturn.itemsAtStart.put(item.name, item);
//                item = new Item();
//                item.name = "key card";
//                item.flags = new HashMap<>();
//                List<String> flagData = new ArrayList<>();
//                flagData.add("Key1") ;
//                item.flags.put("Key", flagData);
//                item.description = "This looks an awful lot like a key-card from one of them new-fangled SYE-FIE " +
//                        "shows.";
//                toReturn.itemsAtStart.put(item.name, item);
//                item = new Item();
//                item.name = "surgical instrument trolley";
//                item.flags = new HashMap<>();
//                flagData = new ArrayList<>();
//                item.flags.put("Decor", flagData);
//                item.description = "A trolley that looks like it should have an assortment of surgical tools on it.";
//                toReturn.itemsAtStart.put(item.name, item);
//                toReturn.npcsAtStart = new HashMap<>();
//                toReturn.recipes = new ArrayList<>();
//                toReturn.endConditions = new ArrayList<>();

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
                                String displayName = parseString(roomJsonObj.get("Display Name"));

                                String name = parseString(roomJsonObj.get("Name"));
                                if (name == null) {
                                        System.out.println("Room Name.");
                                        return true;
                                }
                                HashMap<String, List<String>> flags;
                                if (roomJsonObj.keySet().contains("Effect Tags")) {
                                        obj = roomJsonObj.get("Effect Tags");
                                        if (JSONArray.class.equals(obj.getClass())) {
                                                JSONArray flagsJson = (JSONArray) obj;
                                                flags = parseFlags(flagsJson);
                                                if (flags == null) {
                                                        System.out.println(name + " Effect Tags.");
                                                        return true;
                                                }
                                        } else return true;
                                } else flags = new HashMap<>();
                                List<String> items;
                                if (roomJsonObj.keySet().contains("Items")) {
                                        items = parseStringList(roomJsonObj.get("Items"));
                                        if (items == null) {
                                                System.out.println("Room " + name + " Items.");
                                                return true;
                                        }
                                } else items = new ArrayList<>();

                                HashMap<String, String> connections;
                                //parse connected rooms
                                obj = roomJsonObj.get("Connected Rooms");
                                connections = new HashMap<>();
                                if(JSONArray.class.equals(obj.getClass())){
                                        JSONArray connectionArray = (JSONArray)obj;
                                        for (Object derp : connectionArray) {
                                                if (JSONObject.class.equals(derp.getClass())){
                                                        JSONObject connection = (JSONObject)derp;
                                                        if(connection.keySet().size() == 1){
                                                                obj = connection.keySet().toArray()[0];
                                                                String direction = parseString(obj);
                                                                if (direction == null){
                                                                        System.out.println("Room " + name +
                                                                                " Connection Direction.");
                                                                        return true;
                                                                }
                                                                obj = connection.get(direction);
                                                                String destination = parseString(obj);
                                                                if (destination == null) {
                                                                        System.out.println("Room " + name +
                                                                                " Connection Destination.");
                                                                return true;
                                                                }
                                                                connections.put(direction.toLowerCase(),
                                                                        destination);
                                                        } else {
                                                                System.out.println("Invalid Room " + name +
                                                                        " Connection Object " +
                                                                        "Size, each direction:destination pair must " +
                                                                        "be a separate object and cannot be empty.");
                                                                return true;
                                                        }
                                                } else {
                                                        System.out.println("Invalid Data Type when Object was " +
                                                                "expected at Room " + name + " Connections");
                                                        return true;
                                                }
                                        }
                                }

                                String description = parseString(roomJsonObj.get("Description"));
                                if (description == null) {
                                        System.out.println("Room " + name + " Description.");
                                        return true;
                                }
                                List<String> npcs ;
                                if(roomJsonObj.containsKey("Npcs")) {
                                        npcs = parseStringList(roomJsonObj.get("Npcs"));
                                        if (npcs == null) {
                                                System.out.println("Room " + name + " Npcs.");
                                                return true;
                                        }
                                }else npcs = new ArrayList<>();

                                Room room = new Room(flags, items, npcs, connections, description, name, displayName);
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

        private HashMap<String, List<String>> parseFlags(JSONArray flagsJson) {
                HashMap<String, List<String>> toReturn = new HashMap<>();
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
                                toReturn.put(name, dataList);

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
        private boolean parseItems(){
                itemsAtStart = new HashMap<>();
                Object obj= jsonObject.get("Items");
                if(JSONArray.class.equals(obj.getClass())) { //if the item in the array = obj
                        JSONArray jsonItems = (JSONArray) obj;  // downcast the item to a JSON simple obj
                        for (Object itemObj : jsonItems) { // for each item in jsonItems (JSON simple obj)
                                JSONObject itemJsonObj = (JSONObject) itemObj; //set itemJsonObj = JSON simple Obj

                                HashMap<String, List<String>> flags = new HashMap<>();
                                String name = parseString(itemJsonObj.get("Name"));// item.name equals the parsed JSON simple item name
                                if (name == null) {    // if the item name is null print
                                        System.out.println("Item Name."); // print item name
                                        return true; //exits parsing method
                                }
                                if (itemJsonObj.keySet().contains("Effect Tags")) { //if itemsJson object keys (from map) contain effect tags
                                        obj = itemJsonObj.get("Effect Tags"); // set obj variable = to the effect tag
                                        if (JSONArray.class.equals(obj.getClass())) { // if JSON simple class obj = obj class
                                                JSONArray flagsJson = (JSONArray) obj; //set flagsjson = obj
                                                flags = parseFlags(flagsJson); // set item.flags equal to parsed version of flags
                                                if (flags == null) {  // if the item flag is null
                                                        System.out.println(name + " Effect Tags."); // print out item name concat effect tag
                                                        return true; // exits parsing
                                                }
                                        } else return true;
                                }
                                String description = parseString(itemJsonObj.get("Description")); // set item.description to parsed JSON simple object
                                Item item = new Item(name, description, flags); // this item can now be made a new item in Items
                                if (item.description == null) {   // if item description is null
                                        System.out.println("Item " + item.name + " Description.");
                                return true;
                                }
                                itemsAtStart.put(item.name, item);
                        }
                }
                return false;
        }
        private boolean parseNpcs() {
                npcsAtStart = new HashMap<>();
                Object obj = jsonObject.get("NPCs");
                if (JSONArray.class.equals(obj.getClass())) {
                        JSONArray jsonNpcs = (JSONArray) obj;
                        for (Object npcObj : jsonNpcs) {
                                JSONObject npcJsonObj = (JSONObject) npcObj;

                                String name = parseString(npcJsonObj.get("Name"));
                                if (name == null) {
                                        System.out.println("NPC Name");
                                        return true;
                                }
                                HashMap<String, List<String>> flags;
                                if (npcJsonObj.keySet().contains("Effect Tags")) { //if npcsJson object keys (from map) contain effect tags
                                        obj = npcJsonObj.get("Effect Tags"); // set obj variable = to the effect tag
                                        if (JSONArray.class.equals(obj.getClass())) { // if JSON simple class obj = obj class
                                                JSONArray flagsJson = (JSONArray) obj; //set flagsjson = obj
                                                flags = parseFlags(flagsJson); // set item.flags equal to parsed version of flags
                                                if (flags == null) {  // if the item flag is null
                                                        System.out.println(name + " Effect Tags."); // print out item name concat effect tag
                                                        return true; // exits parsing
                                                }
                                        } else return true;
                                } else flags = new HashMap<>();
                                String dialogue = parseString(npcJsonObj.get("Dialogue")); // set npc.dialogue to parsed JSON simple object
                                if (dialogue == null) {   // if npc dialogue is null
                                        System.out.println("NPC " + name + " Dialogue.");
                                        return true;
                                }

                                List<String> alternativeDialogue =
                                        parseStringList(npcJsonObj.get("Alternate Dialogue")); // set to parsed JSON simple object
                                if (alternativeDialogue == null) {   // if npc  alt. dialogue is null
                                        System.out.println("NPC " + name + " Alternate Dialogue.");
                                        return true;
                                }
                        Npc npc = new Npc(name, dialogue, flags, alternativeDialogue);
                        npcsAtStart.put(npc.name, npc);
                        }
                }
                return false;
        }
        private boolean parseCraftingRecipes(){
                recipes = new ArrayList<>();
                Object obj = jsonObject.get("Crafting Recipes");
                if(JSONArray.class.equals(obj.getClass())){
                        JSONArray jsonCraftingRecipes = (JSONArray) obj;
                        for(Object craftingRecipeObj : jsonCraftingRecipes){
                                JSONObject craftingRecipeJsonObj = (JSONObject) craftingRecipeObj;
                                CraftingRecipe craftingRecipe = new CraftingRecipe();

                                craftingRecipe.result = parseString(craftingRecipeJsonObj.get("Result"));
                                if (craftingRecipe.result == null) {
                                        System.out.println("Crafting Recipe Result.");
                                        return true;
                                }
                                craftingRecipe.ingredients = parseStringList(craftingRecipeJsonObj.get("Ingredients"));
                                if(craftingRecipe.ingredients == null) {
                                        System.out.println("Crafting Recipe Ingredients");
                                }
                        }
                }return false;
        }
        private boolean parseEndCondition(){
                endConditions = new ArrayList<>();
                Object obj = jsonObject.get("End Conditions");
                if(JSONArray.class.equals(obj.getClass())){
                        JSONArray jsonEndConditions = (JSONArray) obj;
                        for(Object endConditionsObj : jsonEndConditions){
                                JSONObject endConditionsJsonObj = (JSONObject) endConditionsObj;
                                EndCondition endCondition = new EndCondition();
                                if(endConditionsJsonObj.containsKey("Room Requirement")) {

                                        endCondition.roomReq = parseString(endConditionsJsonObj.get("Room Requirement"));
                                        if (endCondition.roomReq == null) {
                                                System.out.println("End Condition Room Requirement.");
                                                return true;
                                        }
                                }
                                if(endConditionsJsonObj.containsKey("Item Requirements")) {
                                        endCondition.itemReq = parseStringList(endConditionsJsonObj.get("Item Requirements"));
                                        if (endCondition.itemReq == null) {
                                                System.out.println("End Condition Item Requirements");
                                        }
                                }
                                if(endConditionsJsonObj.containsKey("NPC Status Requirements")){
                                        endCondition.npcReq = new ArrayList<>();
                                        obj = endConditionsJsonObj.get("NPC Status Requirements");
                                        if(obj.getClass().equals(JSONArray.class)) {
                                                JSONArray jsonNpcs = (JSONArray) obj;
                                                for (Object npcObj : jsonNpcs) {
                                                        JSONObject npcJsonObj = (JSONObject) npcObj;

                                                        String name = parseString(npcJsonObj.get("Name"));
                                                        if (name == null) {
                                                                System.out.println("NPC Name");
                                                                return true;
                                                        }
                                                        HashMap<String, List<String>> flags;
                                                        if (npcJsonObj.keySet().contains("Effect Tags")) { //if npcsJson object keys (from map) contain effect tags
                                                                obj = npcJsonObj.get("Effect Tags"); // set obj variable = to the effect tag
                                                                if (JSONArray.class.equals(obj.getClass())) { // if JSON simple class obj = obj class
                                                                        JSONArray flagsJson = (JSONArray) obj; //set flagsjson = obj
                                                                        flags = parseFlags(flagsJson); // set item.flags equal to parsed version of flags
                                                                        if (flags == null) {  // if the item flag is null
                                                                                System.out.println(name + " Effect Tags."); // print out item name concat effect tag
                                                                                return true; // exits parsing
                                                                        }
                                                                } else return true;
                                                        } else flags = new HashMap<>();
                                                        String dialogue = parseString(npcJsonObj.get("Dialogue")); // set npc.dialogue to parsed JSON simple object
                                                        if (dialogue == null) {   // if npc dialogue is null
                                                                System.out.println("NPC " + name + " Dialogue.");
                                                                return true;
                                                        }

                                                        List<String> alternativeDialogue =
                                                                parseStringList(npcJsonObj.get("Alternate Dialogue")); // set to parsed JSON simple object
                                                        if (alternativeDialogue == null) {   // if npc  alt. dialogue is null
                                                                System.out.println("NPC " + name + " Alternate Dialogue.");
                                                                return true;
                                                        }
                                                        Npc npc = new Npc(name, dialogue, flags, alternativeDialogue);
                                                        endCondition.npcReq.add(npc);
                                                }
                                        }
                                }
                        endConditions.add(endCondition);
                        }
                }return false;
        }
}