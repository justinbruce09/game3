package com.pending.game3;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;


class FileParser {
        String startingRoom;
        List<String> startingInventory;
        HashMap<String, Room> roomsAtStart;
        HashMap<String, Item> itemsAtStart;
        List<Npc> npcsAtStart;
        CraftingRecipe[] recipes;
        EndCondition[] endConditions;
        private static JSONObject jsonObject;

        //ctor
        private FileParser(){
        }

        static FileParser loadFile(Path test1) throws FileNotFoundException {
                FileParser toReturn = new FileParser();
                // created parser
                Object obj = JSONValue.parse(new FileReader(String.valueOf(test1)));
                jsonObject = (JSONObject) obj;

                //Dummy logic for Iteration 1 MVP, will replace with actual JSON parsing in Iteration 2
                toReturn.startingInventory = new ArrayList<>();
                toReturn.roomsAtStart = new HashMap<>();
                Room room = new Room();
                room.description = "What appears to be an examination room with three walls, one of which " +
                                   "is curved outward and has a door in it.";
                room.items = new ArrayList<>();
                room.items.add("body-scanning device");
                room.items.add("medicine");
                room.items.add("surgical instrument trolley");
                room.items.add("key card");
                room.flags = new ArrayList<>();
                room.npcs = new ArrayList<>();
                room.name = "examination room";
                room.displayName = "examination room";
                toReturn.roomsAtStart.put(room.name, room);
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
                toReturn.npcsAtStart = new ArrayList<>();
                toReturn.recipes = new CraftingRecipe[0];
                toReturn.endConditions = new EndCondition[0];

                return toReturn;
        }
}