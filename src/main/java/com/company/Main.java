package com.company;

import com.company.models.Group;
import com.company.models.Student;
import org.xml.sax.SAXException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Main {

    public static void main(String[] args) {
        Group group = null;
        try {
            group = DomParser.read("group.xml");
            Student.checkAverage(group);
            DomParser.write(group, "group.xml");
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
