package ru.knitu.repository;

import org.springframework.stereotype.Component;
import ru.knitu.model.Image;
import ru.knitu.model.Item;

import java.util.ArrayList;
import java.util.List;


@Component
public class ItemsRepo {
    static List<Item> items = new ArrayList<>();
    static List<Image> images = new ArrayList<>();
    public void addItems(Item item) {
        this.items.add(item);
    }
    public void deleteItems (){items.clear();}
    public  List<Item> findAll(){return items;}
    public void saveImage(Image image){images.add(image);}
    public  List<Image> findAllImage(){return images;}
    public void replaceUrl(){
        for(int i=0;i<items.size();i++){
            Item item = new Item(items.get(i).getTitle(),items.get(i).getText(),items.get(i).getData(),images.get(i).getUrl());
            items.set(i,item);
        }
    }
}
