package org.ksushka.group.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ksushka.group.model.StudyGroup;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;

@Stateless
@Local
public class ParserImpl implements Parser {

    private static final String[] fields = {"id", "name", "coordinates.x", "coordinates.y",
            "creationDate", "studentsCount", "formOfEducation", "semesterEnum",
            "groupAdmin.name", "groupAdmin.passportID", "groupAdmin.eyeColor",
            "groupAdmin.hairColor", "groupAdmin.nationality"};

    @Override
    public boolean field(String check) {
        boolean isField = false;
        for (String field: fields) {
            if (field.equals(check)) isField = true;
        }
        return isField;
    }

    @Override
    public List<String> getFields(String orderBy) throws RuntimeException {
        if (orderBy == null) return new ArrayList<>();
        List<String> fieldStrings = new ArrayList<>(Arrays.asList(orderBy.split(",")));
        for (String fieldString: fieldStrings) {
            if (!field(fieldString)) throw new RuntimeException("Invalid filter format");
        }
        return fieldStrings;
    }

    @Override
    public boolean fitFilters(StudyGroup group, List<String> filters) {
        if (filters == null) return true;

        for (String filterString: filters) {
            List<String> filter = new ArrayList<>(Arrays.asList(filterString.split("=")));
            switch (filter.get(0)) {
                case ("id"):
                    if (!(group.getId().toString().equals(filter.get(1)))) return false;
                    break;
                case ("name"):
                    if (!(group.getName().equals(filter.get(1)))) return false;
                    break;
                case ("coordinates.x"):
                    if (!(group.getCoordinates().getX().toString().equals(filter.get(1)))) return false;
                    break;
                case ("coordinates.y"):
                    if (!(group.getCoordinates().getY().toString().equals(filter.get(1)))) return false;
                    break;
                case ("creationDate"):
                    if (!(group.getCreationDate().toString().equals(filter.get(1)))) return false;
                    break;
                case ("studentsCount"):
                    if (!(group.getStudentsCount().toString().equals(filter.get(1)))) return false;
                    break;
                case ("formOfEducation"):
                    if (!(group.getFormOfEducation().toString().equals(filter.get(1)))) return false;
                    break;
                case ("semesterEnum"):
                    if (!(group.getSemesterEnum().toString().equals(filter.get(1)))) return false;
                    break;
                case ("groupAdmin.name"):
                    if (group.getGroupAdmin() == null) return false;
                    if (!(group.getGroupAdmin().getName().equals(filter.get(1)))) return false;
                    break;
                case ("groupAdmin.passportID"):
                    if (group.getGroupAdmin() == null) return false;
                    if (!(group.getGroupAdmin().getPassportID().equals(filter.get(1)))) return false;
                    break;
                case ("groupAdmin.eyeColor"):
                    if (group.getGroupAdmin() == null) return false;
                    if (!(group.getGroupAdmin().getEyeColor().toString().equals(filter.get(1)))) return false;
                    break;
                case ("groupAdmin.hairColor"):
                    if (group.getGroupAdmin() == null) return false;
                    if (!(group.getGroupAdmin().getHairColor().toString().equals(filter.get(1)))) return false;
                    break;
                case ("groupAdmin.nationality"):
                    if (group.getGroupAdmin() == null) return false;
                    if (!(group.getGroupAdmin().getNationality().toString().equals(filter.get(1)))) return false;
                    break;
            }
        }
        return true;
    }

    @Override
    public List<String> getFilters(String filterBy) {
        if (filterBy == null) return new ArrayList<>();
        List<String> filters = new ArrayList<>(Arrays.asList(filterBy.split(",")));
        for (String filterString : filters) {
            List<String> filter = new ArrayList<>(Arrays.asList(filterString.split("=")));
            if (filter.size() != 2 || !field(filter.get(0)))
                throw new RuntimeException("Invalid filter format");
        }
        return filters;
    }
    
}
