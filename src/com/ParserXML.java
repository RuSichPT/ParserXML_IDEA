package com;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ParserXML
{
    private ArrayList<Patient> patientsList;

    public ParserXML(String path)
    {
        patientsList = new ArrayList<Patient>();

        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(new File(path));

            Node root = doc.getDocumentElement();
            NodeList patients = root.getChildNodes();

            for (int i = 0; i < patients.getLength(); i++)
            {
                Node patientNode = patients.item(i);
                if (patientNode.getNodeType() != Node.TEXT_NODE)
                {
                    NodeList properties = patientNode.getChildNodes();
                    Patient patient = new Patient();
                    for (int j = 0; j < properties.getLength(); j++)
                    {
                        Node property = properties.item(j);
                        if (property.getNodeType() != Node.TEXT_NODE)
                        {
                            patient.fillProperty(property);
                        }
                    }
                    patientsList.add(patient);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print()
    {
        for (Patient patient: patientsList)
        {
            System.out.println(patient);
        }
    }

    public void sort(String type)
    {
        if (type.equals("name"))
        {
            patientsList.sort(new Comparator<Patient>() {
                @Override
                public int compare(Patient o1, Patient o2) {
                    return o1.getLastName().compareTo(o2.getLastName());
                }
            });
        }
        else if (type.equals("age"))
        {
            patientsList.sort(new Comparator<Patient>() {
                @Override
                public int compare(Patient o1, Patient o2) {
                    return o2.getAge() - o1.getAge();
                }
            });
        }
    }

}
