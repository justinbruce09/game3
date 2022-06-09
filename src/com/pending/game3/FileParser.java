package com.pending.game3;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import  org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;


class FileParser {
        String startingRoom;
        String startingInventory[];
        Room roomsAtStart[];
        Item itemsAtStart[];
        Npc npcsAtStart[];
        CraftingRecipe recipes[];
        EndCondition endCondition[];
        private static JSONObject jsonObject;

        //ctor
        private FileParser(){
        }

        static FileParser loadFile(Path test1) throws FileNotFoundException {
                // created parser
                Object obj = new JSONValue().parse(new FileReader(String.valueOf(test1)));
                jsonObject = (JSONObject) obj;
                return new FileParser();
        }
}