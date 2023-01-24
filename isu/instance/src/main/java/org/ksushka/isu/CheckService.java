package org.ksushka.isu;


import org.ksushka.isu.model.Coordinates;
import org.ksushka.isu.model.Person;
import org.ksushka.isu.model.StudyGroup;
import org.springframework.stereotype.Service;

@Service
public class CheckService {
    public boolean coordinates(Coordinates coordinates) {
        return (coordinates.getY() != null && (coordinates.getX() == null || coordinates.getX() <= 594));
    }

    public boolean person(Person person) {
        return (person.getName() != null && (person.getPassportID() == null || person.getPassportID().length() >= 7) &&
                person.getEyeColor() != null && person.getHairColor() != null && person.getNationality() != null);
    }

    public boolean studyGroup(StudyGroup group) {
        return (group.getName() != null && coordinates(group.getCoordinates()) && group.getStudentsCount() > 0 &&
                group.getFormOfEducation() != null && group.getSemesterEnum() != null);
    }
}
