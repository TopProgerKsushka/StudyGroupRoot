package org.ksushka.group.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyGroup implements Serializable, Comparable<StudyGroup> {

    public StudyGroup() {}
    public StudyGroup(String name, Coordinates coordinates, Integer studentsCount,
                      FormOfEducation formOfEducation, Semester semesterEnum) {
        this.name = name;
        this.coordinates = coordinates;
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
    }
    private Integer id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer studentsCount; //Значение поля должно быть больше 0, Поле не может быть null
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле может быть null


    private List<String> fields;

    public List<String> getFields() {
        return fields;
    }
    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public int compareTo(StudyGroup group) {
        if (fields == null) return this.getId().compareTo(group.getId());
        for (String field: fields) {
            switch (field) {
                case ("id"):
                    if (getId().compareTo(group.getId()) != 0)
                        return getId().compareTo(group.getId());
                case ("name"):
                    if (getName().compareTo(group.getName()) != 0)
                        return getName().compareTo(group.getName());
                case ("coordinates.x"):
                    if (getCoordinates().getX().compareTo(group.getCoordinates().getX()) != 0)
                        return getCoordinates().getX().compareTo(group.getCoordinates().getX());
                case ("coordinates.y"):
                    if (getCoordinates().getY().compareTo(group.getCoordinates().getY()) != 0)
                        return getCoordinates().getY().compareTo(group.getCoordinates().getY());
                case ("creationDate"):
                    if (getCreationDate().compareTo(group.getCreationDate()) != 0)
                        return getCreationDate().compareTo(group.getCreationDate());
                case ("studentsCount"):
                    if (getStudentsCount().compareTo(group.getStudentsCount()) != 0)
                        return getStudentsCount().compareTo(group.getStudentsCount());
                case ("formOfEducation"):
                    if (getFormOfEducation().compareTo(group.getFormOfEducation()) != 0)
                        return getFormOfEducation().compareTo(group.getFormOfEducation());
                case ("semesterEnum"):
                    if (getSemesterEnum().compareTo(group.getSemesterEnum()) != 0)
                        return getSemesterEnum().compareTo(group.getSemesterEnum());
                case ("groupAdmin.name"):
                    if (groupAdmin == null) return -1;
                    if (group.getGroupAdmin() == null) return 1;
                    if (getGroupAdmin().getName().compareTo(group.getGroupAdmin().getName()) != 0)
                        return getGroupAdmin().getName().compareTo(group.getGroupAdmin().getName());
                case ("groupAdmin.passportID"):
                    if (groupAdmin == null) return -1;
                    if (group.getGroupAdmin() == null) return 1;
                    if (getGroupAdmin().getPassportID().compareTo(group.getGroupAdmin().getPassportID()) != 0)
                        return getGroupAdmin().getPassportID().compareTo(group.getGroupAdmin().getPassportID());
                case ("groupAdmin.eyeColor"):
                    if (groupAdmin == null) return -1;
                    if (group.getGroupAdmin() == null) return 1;
                    if (getGroupAdmin().getEyeColor().compareTo(group.getGroupAdmin().getEyeColor()) != 0)
                        return getGroupAdmin().getEyeColor().compareTo(group.getGroupAdmin().getEyeColor());
                case ("groupAdmin.hairColor"):
                    if (groupAdmin == null) return -1;
                    if (group.getGroupAdmin() == null) return 1;
                    if (getGroupAdmin().getHairColor().compareTo(group.getGroupAdmin().getHairColor()) != 0)
                        return getGroupAdmin().getHairColor().compareTo(group.getGroupAdmin().getHairColor());
                case ("groupAdmin.nationality"):
                    if (groupAdmin == null) return -1;
                    if (group.getGroupAdmin() == null) return 1;
                    if (getGroupAdmin().getNationality().compareTo(group.getGroupAdmin().getNationality()) != 0)
                        return getGroupAdmin().getNationality().compareTo(group.getGroupAdmin().getNationality());
            }
        }
        return 0;
    }
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

}
