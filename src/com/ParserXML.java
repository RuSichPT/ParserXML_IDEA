package com;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ParserXML
{
    ArrayList<Patient> patientsList;

    public ParserXML(String path)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(path));

            NodeList patients = doc.getElementsByTagName("patient");

            for (int i = 0; i < patients.getLength(); i++)
            {

                System.out.println(patients.item(i).getTextContent());
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
