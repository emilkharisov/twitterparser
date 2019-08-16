package ru.knitu.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.knitu.model.Item;
import ru.knitu.repository.ItemsRepo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;


public class Parse {
    static String line1 ="";
    public static void parseFile(String channel) throws IOException, SAXException, ParserConfigurationException {
        URL url = new URL("https://queryfeed.net/tw?q=%40" + channel);
        File xmlFile = new File("src/main/resources/templates/file.xml");
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(xmlFile));
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));
        while (reader.ready()) {
            line1 = reader.readLine();
            fileWriter.write(line1);
        }
        reader.close();
        fileWriter.close();
        ItemsRepo itemsRepo = new ItemsRepo();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        Element element = document.getDocumentElement();
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("item");
        String show_title = "", show_tweet = "", show_data = "", show_url="";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NodeList nodeList1 = node.getChildNodes();
            for (int j = 0; j < nodeList1.getLength(); j++) {
                Node node1 = nodeList1.item(j);
                if (node1.getNodeName() == "title") {
                    show_title = node1.getTextContent();
                }
                if (node1.getNodeName() == "pubDate") {
                    show_data = node1.getTextContent().substring(0, 26);
                }
                if (node1.getNodeName() == "description") {
                    NodeList nodeList2 = node1.getChildNodes();
                    for(int y=0; y<nodeList2.getLength();y++){
                        Node node2 = nodeList2.item(y);
                        if(node2.getNodeName()=="#cdata-section"){
                           String tweet_url = node2.getTextContent();
                           if(tweet_url.contains("a href=")){
                               int indexOf_tweetUrl = tweet_url.indexOf("a href=");
                               String tweet_url_parse = tweet_url.substring(indexOf_tweetUrl+8,indexOf_tweetUrl+31);
                               show_url = tweet_url_parse;
                           }
                           else{show_url="none";}
                        }
                    }
                    String line = node1.getTextContent();
                    while (line.contains("72x72")) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(line);
                        int indexOf_image = stringBuilder.indexOf("72x72");
                        stringBuilder.setCharAt(indexOf_image, '6');
                        stringBuilder.setCharAt(indexOf_image + 1, '2');
                        stringBuilder.setCharAt(indexOf_image + 2, 'x');
                        stringBuilder.setCharAt(indexOf_image + 3, '6');
                        stringBuilder.setCharAt(indexOf_image + 4, '2');
                        line = stringBuilder.toString();
                    }
                    show_tweet = line;
                }
            }
            itemsRepo.addItems(new Item(show_title, show_tweet, show_data,show_url));
        }
//--------------------------------------------------------------------------------------------------------------------------------------------
        ParseHtml.parseHtml();
    }
}

