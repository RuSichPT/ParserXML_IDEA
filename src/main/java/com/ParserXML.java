package com;

import de.vandermeer.asciitable.AT_Context;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.TA_Grid;
import de.vandermeer.asciithemes.TA_GridThemes;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
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
    private ArrayList<Patient> patients;

    public ParserXML(String path)
    {
        patients = new ArrayList<Patient>();

        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(new File(path));

            Node root = doc.getDocumentElement();
            NodeList patientsNodeList = root.getChildNodes();

            for (int i = 0; i < patientsNodeList.getLength(); i++)
            {
                Node patientNode = patientsNodeList.item(i);
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
                    patients.add(patient);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print()
    {
        AsciiTable at = new AsciiTable();
        AT_Context context = at.getContext();
        context.setGridTheme(TA_GridThemes.NONE);
        context.setWidth(150);

        at.addRule();
        at.addRow("ФИО", "Возраст", "Пол", "Телефон");
        at.addRule();
        for (Patient patient: patients)
        {
            String name = patient.getLastName() + " " + patient.getFirstName() + " " + patient.getMiddleName();
            at.addRow(name, patient.getAge(), patient.getGender(),patient.getPhone());
            at.addRule();
        }

        String rend = at.render();
        System.out.println(rend);

    }

    public void sort(String type)
    {
        if (type.equals("name"))
        {
            patients.sort(new Comparator<Patient>() {
                @Override
                public int compare(Patient o1, Patient o2) {
                    return o1.getLastName().compareTo(o2.getLastName());
                }
            });
        }
        else if (type.equals("age"))
        {
            patients.sort(new Comparator<Patient>() {
                @Override
                public int compare(Patient o1, Patient o2) {
                    return o2.getAge() - o1.getAge();
                }
            });
        }
    }

}
