package ru.knitu.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = "ru.knitu")
public class Application {
        public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
            SpringApplication.run(Application.class);
        }
}
