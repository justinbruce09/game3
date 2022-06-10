package com.pending.game3;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


class FileParser {
        String startingRoom;
        List<String> startingInventory;
        List<Room> roomsAtStart;
        List<Item> itemsAtStart;
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
                toReturn.roomsAtStart = new ArrayList<>();
                Room room = new Room();
                room.description = "What appears to be an examination room with three walls, one of which " +
                                   "is curved outward and has a door in it.";
                room.items = new ArrayList<>();
                room.items.add("Body-Scanning Device");
                room.items.add("Medicine");
                room.items.add("Surgical Instrument Trolley");
                room.items.add("Key Card");
                room.flags = new Flag[0];
                room.npcs = new String[0];
                room.name = "Examination Room";
                room.displayName = "Examination Room";
                toReturn.roomsAtStart.add(room);
                toReturn.startingRoom = "Examination Room";
                toReturn.itemsAtStart = new ArrayList<>();
                Item item = new Item();
                item.name = "Body-Scanning Device";
                item.flags = new Flag[0];
                item.description = "You think this devices is intended for anatomical scanning, but it looks really" +
                        " similar to a nightstick. You could probably bash some head with it.";
                toReturn.itemsAtStart.add(item);
                item = new Item();
                item.name = "Medicine";
                item.flags = new Flag[0];
                item.description = "This looks like an assortment of medicines, probably shouldn't use any until " +
                        "you find out what they do.";
                toReturn.itemsAtStart.add(item);
                item = new Item();
                item.name = "Key Card";
                item.flags = new Flag[1];
                String[] flagData = new String[1];
                flagData[0] = "Key1";
                Flag flag = new Flag("Key", flagData);
                item.flags[0] = flag;
                item.description = "This looks an awful lot like a key-card from one of them new-fangled SYE-FIE " +
                        "shows.";
                toReturn.itemsAtStart.add(item);
                item = new Item();
                item.name = "Surgical Instrument Trolley";
                item.flags = new Flag[1];
                flagData = new String[0];
                flag = new Flag("Decor", flagData);
                item.flags[0] = flag;
                item.description = "A trolley that looks like it should have an assortment of surgical tools on it.";
                toReturn.itemsAtStart.add(item);
                toReturn.npcsAtStart = new ArrayList<>();
                toReturn.recipes = new CraftingRecipe[0];
                toReturn.endConditions = new EndCondition[0];

                return toReturn;
        }
}