package org.ksushka.group.services;

import org.ksushka.group.model.*;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    public boolean coordinates(Coordinates coordinates) {
        return (coordinates.getY() != null && (coordinates.getX() == null || coordinates.getX() <= 594));
    }

    public boolean person(Person person) {
        if (person == null) return true;

        return (person.getName() != null && !person.getName().isEmpty()
                && (person.getPassportID() == null || person.getPassportID().length() >= 7)
                && person.getEyeColor() != null && person.getHairColor() != null
                && person.getNationality() != null);
    }

    public boolean studyGroup(StudyGroup group) {

        if (group.getId() != null || group.getName() == null || group.getCoordinates() == null ||
                group.getCreationDate() != null || group.getStudentsCount() == null ||
                group.getFormOfEducation() == null || group.getSemesterEnum() == null ||
                group.getExpelledStudents() != null) {
            return false;
        }

        return (coordinates(group.getCoordinates()) && !group.getName().isEmpty() && group.getStudentsCount() > 0);
    }
}
