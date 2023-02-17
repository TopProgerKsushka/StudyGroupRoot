package org.ksushka.group.services;

import org.ksulabut.soa.lab4.SoapCoordinates;
import org.ksulabut.soa.lab4.SoapPerson;
import org.ksulabut.soa.lab4.SoapStudyGroup;
import org.ksushka.group.model.*;
import org.springframework.stereotype.Service;

@Service
public class ConvertService {
    public SoapCoordinates coordinates(Coordinates coords) {
        SoapCoordinates soapCoords = new SoapCoordinates();
        soapCoords.setX(coords.getX());
        soapCoords.setY(coords.getY());
        return soapCoords;
    }

    public Coordinates coordinates(SoapCoordinates soapCoords) {
        Coordinates coords = new Coordinates();
        coords.setX(soapCoords.getX());
        coords.setY(soapCoords.getY());
        return coords;
    }

    public SoapPerson person(Person person) {
        SoapPerson soapPerson = new SoapPerson();
        soapPerson.setName(person.getName());
        soapPerson.setPassportID(person.getPassportID());
        soapPerson.setEyeColor(person.getEyeColor().toString());
        soapPerson.setHairColor(person.getHairColor().toString());
        soapPerson.setNationality(person.getNationality().toString());
        return soapPerson;
    }

    public Person person(SoapPerson soapPerson) {
        Person person = new Person();
        person.setName(soapPerson.getName());
        person.setPassportID(soapPerson.getPassportID());
        person.setEyeColor(Color.valueOf(soapPerson.getEyeColor()));
        person.setHairColor(Color.valueOf(soapPerson.getHairColor()));
        person.setNationality(Country.valueOf(soapPerson.getNationality()));
        return person;
    }

    public SoapStudyGroup studyGroup(StudyGroup group) {
        SoapStudyGroup soapGroup = new SoapStudyGroup();
        soapGroup.setId(group.getId());
        soapGroup.setName(group.getName());

        soapGroup.setCoordinates(coordinates(group.getCoordinates()));

        if (group.getCreationDate() == null) soapGroup.setCreationDate(null);
        else soapGroup.setCreationDate(group.getCreationDate().toString());

        soapGroup.setStudentsCount(group.getStudentsCount());
        soapGroup.setFormOfEducation(group.getFormOfEducation().toString());
        soapGroup.setSemesterEnum(group.getSemesterEnum().toString());

        if (group.getGroupAdmin() == null) soapGroup.setGroupAdmin(null);
        else soapGroup.setGroupAdmin(person(group.getGroupAdmin()));

        return soapGroup;
    }

    public StudyGroup studyGroup(SoapStudyGroup soapGroup) {
        StudyGroup group = new StudyGroup();
        group.setId(soapGroup.getId());
        group.setName(soapGroup.getName());

        group.setCoordinates(coordinates(soapGroup.getCoordinates()));

        if (soapGroup.getCreationDate() == null) group.setCreationDate(null);
        else group.setCreationDate(java.time.LocalDate.parse(soapGroup.getCreationDate()));

        group.setStudentsCount(soapGroup.getStudentsCount());
        group.setFormOfEducation(FormOfEducation.valueOf(soapGroup.getFormOfEducation()));
        group.setSemesterEnum(Semester.valueOf(soapGroup.getSemesterEnum()));

        if (soapGroup.getGroupAdmin() == null) group.setGroupAdmin(null);
        else group.setGroupAdmin(person(soapGroup.getGroupAdmin()));

        return group;
    }
}
