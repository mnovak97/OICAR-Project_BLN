package com.example.oicar_project;

import java.util.ArrayList;
import java.util.List;

public class DefaultBoard {

    public static List<Item> getItems(){

        List<Item> items = new ArrayList<>();

        items.add(new Item("Košnja trave","Dvorište veličine 100 kvadrata","Gornji Bukovac 96",0));
        items.add(new Item("Šišanje živice","Živica","Gornji Bukovac 94",0));
        items.add(new Item("Šišanje živice","Živica","Gornji Bukovac 94",0));

        return items;
    }
}
