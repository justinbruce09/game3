package com.pending.game3;

import java.util.Dictionary;
import java.util.List;

class Room {
    List<Flag> flags;
    List<String> items;
    List<String> npcs;
    Dictionary<String, String> connections;
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