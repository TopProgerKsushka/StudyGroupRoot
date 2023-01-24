package org.ksushka.group.ejb;

import org.ksushka.group.model.Coordinates;
import org.ksushka.group.model.Person;
import org.ksushka.group.model.StudyGroup;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;

@Stateless
@Local
public class NullCheckerImpl implements NullChecker {

    @Override
    public boolean coordinates(Coordinates coordinates) {
        return (coordinates.getY() != null && (coordinates.getX() == null || coordinates.getX() <= 594));
    }

    @Override
    public boolean person(Person person) {
        if (person == null) return true;

        return (person.getName() != null && !person.getName().isEmpty()
                && (person.getPassportID() == null || person.getPassportID().length() >= 7)
                && person.getEyeColor() != null && person.getHairColor() != null
                && person.getNationality() != null);
    }

    @Override
    public boolean studyGroup(StudyGroup group) {
        if (group.getId() != null || group.getName() == null || group.getCoordinates() == null ||
                group.getCreationDate() != null || group.getStudentsCount() == null ||
                group.getFormOfEducation() == null || group.getSemesterEnum() == null) {
            return false;
        }

        return (coordinates(group.getCoordinates()) && !group.getName().isEmpty() && group.getStudentsCount() > 0);
    }
    
}
