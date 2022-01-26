package com.company;

import com.company.models.Group;
import com.company.models.Student;
import com.company.models.Subject;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomParser {
    public static Group read(String path) throws ParserConfigurationException, IOException, SAXException {
        Group group = null;

        File file = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;
        doc = dbf.newDocumentBuilder().parse(file);

        NodeList students = doc.getDocumentElement().getElementsByTagName("student");
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < students.getLength(); i++) {
            Node studentNode = students.item(i);
            NamedNodeMap attributes = studentNode.getAttributes();
            Student student = new Student();
            student.setFirstName(attributes.getNamedItem("firstname").getNodeValue());
            student.setSecondName(attributes.getNamedItem("lastname").getNodeValue());
            student.setGroupNumber(Integer.parseInt(attributes.getNamedItem("groupnumber").getNodeValue()));

            NodeList subjectsNode = studentNode.getChildNodes();
            List<Subject> subjects = new ArrayList<>();
            for (int j = 0; j < subjectsNode.getLength(); j++) {
                if (subjectsNode.item(j).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                switch (subjectsNode.item(j).getNodeName()) {
                    case "subject": {
                        Subject subject = new Subject();
                        NamedNodeMap attributesSubject = subjectsNode.item(j).getAttributes();
                        subject.setTitle(attributesSubject.getNamedItem("title").getNodeValue());
                        subject.setMark(Integer.parseInt(attributesSubject.getNamedItem("mark").getNodeValue()));
                        subjects.add(subject);
                        break;
                    }
                    case "average": {
                        student.setAverage(Double.parseDouble(subjectsNode.item(j).getTextContent()));
                        break;
                    }
                }
            }

            student.setSubjects(subjects);
            studentList.add(student);
        }
        group = new Group();
        group.setStudents(studentList);
        return group;
    }

    public static void write(Group group, String path) throws TransformerException, FileNotFoundException {
        File file = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            document = dbf.newDocumentBuilder().parse(file);
        } catch (Exception e) {
            System.out.println("Parsing error" + e.toString());
            return;
        }

        NodeList students = document.getElementsByTagName("student");
        for (int i = 0; i < students.getLength(); i++) {
            Node student = students.item(i);

            if (student.getNodeType() == Node.ELEMENT_NODE) {
                Element eStudent = (Element) student;

                eStudent.setAttribute("lastname", group.getStudents().get(i).getSecondName());
                eStudent.setAttribute("groupnumber", String.valueOf(group.getStudents().get(i).getGroupNumber()));
                eStudent.setAttribute("firstname", group.getStudents().get(i).getFirstName());

                NodeList subjects = student.getChildNodes();

                for (int j = 0; j < subjects.getLength(); j++) {
                    Node subject = subjects.item(j);

                    if (subject.getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }

                    if ("average".equals(subject.getNodeName())) {
                        String average = String.format("%.1f" ,group.getStudents().get(i).getAverage());

                        subject.getChildNodes().item(0).setTextContent(average.replace(',','.'));
                    }
                }
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "group.dtd");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new FileOutputStream(file));
        transformer.transform(source, result);
    }
}
