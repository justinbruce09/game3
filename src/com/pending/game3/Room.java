package com.pending.game3;

import java.util.HashMap;
import java.util.List;

class Room {
    HashMap<String, List<String>> flags;
    List<String> items;
    List<String> npcs;
    HashMap<String, String> connections;
    String description;
    String name;
    String displayName;

    void takeItem(String itemToTake) {
        if (items.contains(itemToTake)) {
            Game3.getInventory().add(itemToTake);
            items.remove(itemToTake);
        }
    }

    void look(){
    }

    void enter(){
    }

    void craft(){
    }

}