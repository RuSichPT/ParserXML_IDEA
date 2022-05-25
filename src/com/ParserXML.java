package com;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ParserXML
{
    private ArrayList<Patient> patientsList;

    public ParserXML(String path)
    {
        patientsList = new ArrayList<>();

        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
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
                            fillPropertyPatient(patient,property);
                        }
                    }
                    patientsList.add(patient);
                }
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void fillPropertyPatient(Patient patient, Node property)
    {
        String content = property.getTextContent();
        if (property.getNodeName() == "first_name")
        {
            patient.firstName = content;
        }
        else if (property.getNodeName() == "middle_name")
        {
            patient.middleName = content;
        }
        else if (property.getNodeName() == "last_name")
        {
            patient.lastName = content;
        }
        else if (property.getNodeName() == "birthday")
        {
            Date currentDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = null;
            try
            {
                birthday = format.parse(content);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Перевод количества дней между датами из миллисекунд в года
            patient.age =  (int)( (currentDate.getTime() - birthday.getTime() ) / (365 * 24 * 60 * 60 * 1000) ); // миллисекунды / (365дн * 4ч * 60мин * 60сек * 1000мс)
        }
        else if (property.getNodeName() == "gender")
        {
            patient.gender = content;
        }
        else if (property.getNodeName() == "phone")
        {
            patient.phone = content;
        }

    }
}
