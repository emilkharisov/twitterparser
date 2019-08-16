package ru.knitu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;
import ru.knitu.repository.ItemsRepo;
import ru.knitu.services.Parse;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Controller
public class ItemController {
    @Autowired
    ItemsRepo itemsRepo;

    @GetMapping("/item")
    public String getTestItem(ModelMap model)  {
        model.addAttribute("itemsFromServer",itemsRepo.findAll());
        return  "test";
    }
    @PostMapping("/item")
    public String parseTweeterChannel(ModelMap model, @RequestParam String channel) throws ParserConfigurationException, SAXException, IOException {
        itemsRepo.deleteItems();
        Parse.parseFile(channel);
        itemsRepo.replaceUrl();
        model.addAttribute("itemsFromServer",itemsRepo.findAll());
        return  "test";
    }

}
