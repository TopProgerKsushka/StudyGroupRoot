package org.ksushka.group.ejb;

import java.util.HashSet;
import java.util.LinkedList;

import org.ksushka.group.HttpStatusThrowable;
import org.ksushka.group.messages.DeleteResult;
import org.ksushka.group.messages.PutResult;
import org.ksushka.group.model.FormOfEducation;
import org.ksushka.group.model.Semester;
import org.ksushka.group.model.StudyGroup;

public interface StudyGroupManager {
    public LinkedList<StudyGroup> getAll(
        LinkedList<StudyGroup> groups,
        Integer pageNumber, Integer pageSize,
        String sortType,
        String orderBy, String filterBy
    ) throws HttpStatusThrowable;

    public StudyGroup get(LinkedList<StudyGroup> groups, int id) throws HttpStatusThrowable;

    public LinkedList<StudyGroup> add(LinkedList<StudyGroup> groups, StudyGroup groupToAdd) throws HttpStatusThrowable;

    public PutResult put(LinkedList<StudyGroup> groups, int id, StudyGroup groupToPut) throws HttpStatusThrowable;

    public DeleteResult delete(LinkedList<StudyGroup> groups, int id) throws HttpStatusThrowable;

    public int countHigherSemester(LinkedList<StudyGroup> groups, Semester semester) throws HttpStatusThrowable;

    public LinkedList<StudyGroup> getStartsFrom(LinkedList<StudyGroup> groups, String prefix) throws HttpStatusThrowable;

    public HashSet<FormOfEducation> getUniqueForms(LinkedList<StudyGroup> groups) throws HttpStatusThrowable;
}
