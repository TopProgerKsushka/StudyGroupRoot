package org.ksushka.group.ejb;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import org.ksushka.group.HttpStatusThrowable;
import org.ksushka.group.messages.DeleteResult;
import org.ksushka.group.messages.PutResult;
import org.ksushka.group.model.FormOfEducation;
import org.ksushka.group.model.Semester;
import org.ksushka.group.model.StudyGroup;

import jakarta.ejb.EJB;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;

@Stateless
@Remote(StudyGroupManager.class)
public class StudyGroupManagerImpl implements StudyGroupManager {

    @EJB
    Parser parser;

    @EJB
    NullChecker nullChecker;

    @Override
    public LinkedList<StudyGroup> getAll(LinkedList<StudyGroup> groups, Integer pageNumber, Integer pageSize,
            String sortType,
            String orderBy, String filterBy) throws HttpStatusThrowable {
        if ((pageSize == null && pageNumber != null) || (pageNumber == null && pageSize != null))
            throw new HttpStatusThrowable(400);
        if (pageSize != null && (pageSize < 1 || pageNumber < 0))
            throw new HttpStatusThrowable(400);
        if (sortType != null && !sortType.equals("asc") && !sortType.equals("desc"))
            throw new HttpStatusThrowable(400);

        List<String> filters;
        if (filterBy.equals(""))
            filterBy = null;

        try {
            filters = parser.getFilters(filterBy);
        } catch (RuntimeException ex) {
            throw new HttpStatusThrowable(400);
        }

        LinkedList<StudyGroup> tempGroups = new LinkedList<>();
        List<String> sortFields = parser.getFields(orderBy);

        for (StudyGroup group : groups) {
            if (parser.fitFilters(group, filters)) {
                if (group.getGroupAdmin() != null && group.getGroupAdmin().getName() == null)
                    group.setGroupAdmin(null);
                tempGroups.add(group);
                group.setFields(sortFields);
            }
        }
        if (pageSize != null && pageSize * (pageNumber - 1) >= tempGroups.size()) {
            return new LinkedList<>();
        }

        Collections.sort(tempGroups);
        if (sortType != null && sortType.equals("desc"))
            Collections.reverse(tempGroups);
        if (pageSize != null)
            return (LinkedList<StudyGroup>) tempGroups.subList(pageSize * (pageNumber - 1),
                    Math.min(pageSize * pageNumber, tempGroups.size()));
        return tempGroups;
    }

    @Override
    public StudyGroup get(LinkedList<StudyGroup> groups, int id) throws HttpStatusThrowable {
        if (groups.isEmpty() || id > groups.getLast().getId())
            throw new HttpStatusThrowable(400);
        Optional<StudyGroup> item = groups.stream().filter(g -> g.getId() == id).findFirst();

        try {
            if (item.isPresent()) {
                return item.get();
            } else {
                throw new HttpStatusThrowable(404);
            }
        } catch (IndexOutOfBoundsException ex) {
            throw new HttpStatusThrowable(404);
        }
    }

    @Override
    public LinkedList<StudyGroup> add(LinkedList<StudyGroup> groups, StudyGroup groupToAdd) throws HttpStatusThrowable {
        if (!nullChecker.studyGroup(groupToAdd))
            throw new HttpStatusThrowable(400);

        groupToAdd.setCreationDate(java.time.LocalDate.now());
        try {
            groupToAdd.setId(groups.getLast().getId() + 1);
        } catch (NoSuchElementException ex) {
            groupToAdd.setId(0);
        }
        if (groupToAdd.getGroupAdmin() != null && !nullChecker.person(groupToAdd.getGroupAdmin())) {
            groupToAdd.setGroupAdmin(null);
        }

        groups.add(groupToAdd);
        return groups;
    }

    @Override
    public PutResult put(LinkedList<StudyGroup> groups, int id, StudyGroup groupToPut)
            throws HttpStatusThrowable {
        if (!nullChecker.studyGroup(groupToPut))
            throw new HttpStatusThrowable(400);

        int listId = -1;
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getId() == id) {
                listId = i;
                break;
            }
        }

        if (listId == -1)
            throw new HttpStatusThrowable(404);

        groupToPut.setId(groups.get(listId).getId());
        groupToPut.setCreationDate(groups.get(listId).getCreationDate());
        int expelled = Math.max(groups.get(listId).getStudentsCount() - groupToPut.getStudentsCount(), 0);

        groups.set(listId, groupToPut);

        PutResult result = new PutResult();
        result.groups = groups;
        result.cntExpelled = expelled;
        return result;
    }

    @Override
    public DeleteResult delete(LinkedList<StudyGroup> groups, int id) throws HttpStatusThrowable {
        int cntExpelled;
        if (groups.isEmpty() || id > groups.getLast().getId())
            throw new HttpStatusThrowable(400);
        ;
        Optional<StudyGroup> item = groups.stream().filter(g -> g.getId() == id).findFirst();
        if (item.isPresent()) {
            cntExpelled = item.get().getStudentsCount();
            groups.remove(item.get());
            DeleteResult result = new DeleteResult();
            result.groups = groups;
            result.cntExpelled = cntExpelled;
            return result;
        } else
            throw new HttpStatusThrowable(404);
    }

    @Override
    public int countHigherSemester(LinkedList<StudyGroup> groups, Semester semester) throws HttpStatusThrowable {
        int count = 0;
        for (StudyGroup group : groups) {
            if (group.getSemesterEnum().getN() > semester.getN())
                count++;
        }

        return count;
    }

    @Override
    public LinkedList<StudyGroup> getStartsFrom(LinkedList<StudyGroup> groups, String prefix)
            throws HttpStatusThrowable {
        if (prefix == null)
            prefix = "";
        LinkedList<StudyGroup> tempGroups = new LinkedList<>();
        for (StudyGroup group : groups) {
            if (group.getName().startsWith(prefix)) {
                tempGroups.add(group);
            }
        }
        return tempGroups;
    }

    @Override
    public HashSet<FormOfEducation> getUniqueForms(LinkedList<StudyGroup> groups) throws HttpStatusThrowable {
        HashSet<FormOfEducation> set = new HashSet<>();
        for (StudyGroup group : groups) set.add(group.getFormOfEducation());
        return set;
    }
}
