package ru.knitu.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.knitu.model.Image;
import ru.knitu.model.Item;
import ru.knitu.repository.ItemsRepo;

import java.io.*;
import java.net.URL;




public class ParseHtml {

    public static void parseHtml() throws IOException {
        ItemsRepo itemsRepo = new ItemsRepo();
        for(Item item : itemsRepo.findAll())
        {
            System.out.println(item.getUrl());
        }
        for (Item item : itemsRepo.findAll()) {
            File htmlFile = new File("src/main/resources/templates/file1.html");
            Image image = new Image();
            if (item.getUrl() == "none") {
                image.setUrl("none");
            } else {
                URL url1 = new URL(item.getUrl());
                BufferedWriter fileWriter1 = new BufferedWriter(new FileWriter(htmlFile));
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(url1.openStream()));
                String line = reader1.readLine();
                while (line != null) {
                    if (line != null) {
                        fileWriter1.write(line);
                        line = reader1.readLine();
                    }
                }
                reader1.close();
                fileWriter1.close();
                reader1 = new BufferedReader(new FileReader(htmlFile));
                String line1 = reader1.readLine();
                Document document = Jsoup.parse(htmlFile, "utf-8");
                Elements elements = document.getElementsByTag("meta");
                for (Element element : elements) {
                    String img = element.attr("property");
                    if (img.equals("og:image")) {
                        String imgUrl = element.attr("content");
                        image.setUrl(imgUrl);
                    }
                }
            }
            itemsRepo.saveImage(image);
        }
    }
}
