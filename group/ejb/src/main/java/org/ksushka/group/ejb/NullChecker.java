package org.ksushka.group.ejb;

import org.ksushka.group.model.Coordinates;
import org.ksushka.group.model.Person;
import org.ksushka.group.model.StudyGroup;

public interface NullChecker {
    public boolean coordinates(Coordinates coordinates);
    public boolean person(Person person);
    public boolean studyGroup(StudyGroup group);
}
