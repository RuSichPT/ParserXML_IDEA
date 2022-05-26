package com;

import org.w3c.dom.Node;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Patient
{
    private String firstName;
    private String middleName;
    private String lastName;
    private int age;
    private String gender;
    private String phone;

    public Patient(){ }

    public String getFirstName() { return firstName; }

    public String getMiddleName() { return middleName; }

    public String getLastName() { return lastName; }

    public int getAge() { return age; }

    public String getGender() {
        return gender;
    }

    public String getPhone() { return phone; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString()
    {
        return "ФИО: " + firstName + " " + middleName + " " + lastName +
            " Возраст: " + age + " Пол: " + gender + " Телефон: " + phone;
    }

    public void fillProperty(Node property)
    {
        String content = property.getTextContent();
        if (property.getNodeName() == "first_name")
        {
            setFirstName(content);
        }
        else if (property.getNodeName() == "middle_name")
        {
            setMiddleName(content);
        }
        else if (property.getNodeName() == "last_name")
        {
            setLastName(content);
        }
        else if (property.getNodeName() == "birthday")
        {
            Date currentDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                Date birthday = format.parse(content);
                // Перевод количества дней между датами из миллисекунд в года
                int age =  (int)( (currentDate.getTime() - birthday.getTime() ) / ( (long) 365* 24 * 60 * 60 * 1000) ); // миллисекунды / (365дн * 4ч * 60мин * 60сек * 1000мс)
                setAge(age);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        else if (property.getNodeName() == "gender")
        {
            setGender(content);
        }
        else if (property.getNodeName() == "phone")
        {
            setPhone(content);;
        }
    }

}
