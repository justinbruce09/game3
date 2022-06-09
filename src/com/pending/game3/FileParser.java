package com.pending.game3;

import org.json.simple.JSONValue;
import  org.json.simple.parser.JSONParser;

import java.io.File;
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

        //ctor
        private FileParser(){
        }

        FileParser loadFile(Path test1){
                // created parser
                Object obj = new JSONValue().parse(new FileReader(new File(test1));

        }
}