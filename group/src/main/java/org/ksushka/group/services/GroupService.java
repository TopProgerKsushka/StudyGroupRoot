package org.ksushka.group.services;

import org.ksushka.group.model.FormOfEducation;
import org.ksushka.group.model.Semester;
import org.ksushka.group.model.StudyGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class GroupService {
    static LinkedList<StudyGroup> groups = new LinkedList<>();
    int cntExpelledByDelete = 0;

    @Autowired
    MatchService matchService;

    @Autowired
    ParseService parseService;

    public List<StudyGroup> getAll(
        Integer pageNumber,
        Integer pageSize,
        String sortType,
        String orderBy,
        String filterBy
    ) {
        if ((pageSize == null && pageNumber != null) || (pageNumber == null && pageSize != null)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (pageSize != null && (pageSize < 1 || pageNumber < 0)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (sortType != null && !sortType.equals("asc") && !sortType.equals("desc")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        List<String> filters;
        if (filterBy.equals("")) filterBy = null;

        try {
            filters = parseService.getFilters(filterBy);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<StudyGroup> tempGroups = new ArrayList<>();
        List<String> sortFields = parseService.getFields(orderBy);

        for (StudyGroup group: groups) {
            if (parseService.fitFilters(group, filters)) {
                if (group.getGroupAdmin() != null && group.getGroupAdmin().getName() == null)
                    group.setGroupAdmin(null);
                tempGroups.add(group);
                group.setFields(sortFields);
            }
        }
        if (pageSize != null && pageSize * (pageNumber - 1) >= tempGroups.size()) {
            return new ArrayList<>();
        }

        Collections.sort(tempGroups);
        if (sortType != null && sortType.equals("desc")) Collections.reverse(tempGroups);
        if (pageSize != null) return tempGroups.subList(pageSize * (pageNumber - 1), Math.min(pageSize * pageNumber, tempGroups.size()));
        return tempGroups;
    }

    public StudyGroup add(StudyGroup group) {

        if (!matchService.studyGroup(group)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        group.setCreationDate(java.time.LocalDate.now());
        group.setExpelledStudents(0);
        try {
            group.setId(groups.getLast().getId() + 1);
        } catch (NoSuchElementException ex) {
            group.setId(0);
        }
        if (group.getGroupAdmin() != null && !matchService.person(group.getGroupAdmin())) {
            group.setGroupAdmin(null);
        }

        groups.add(group);
        return group;
    }

    public StudyGroup get(int id) {
        if (groups.isEmpty() || id > groups.getLast().getId()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Optional<StudyGroup> item = groups.stream().filter(g -> g.getId() == id).findFirst();

        if (item.isPresent()) {
            return item.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public StudyGroup modify(int id, StudyGroup group) {
        if (!matchService.studyGroup(group)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        int listId = -1;
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getId() == id) {
                listId = i;
                break;
            }
        }

        if (listId == -1) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        group.setId(groups.get(listId).getId());
        group.setCreationDate(groups.get(listId).getCreationDate());
        group.setExpelledStudents(
                Math.max(groups.get(listId).getStudentsCount() - group.getStudentsCount(), 0));

        groups.set(listId, group);
        return group;
    }

    public void delete(int id) {
        if (groups.isEmpty() || id > groups.getLast().getId()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Optional<StudyGroup> item = groups.stream().filter(g -> g.getId() == id).findFirst();
        if (item.isPresent()) {
            cntExpelledByDelete += item.get().getStudentsCount() + item.get().getExpelledStudents();
            groups.remove(item.get());
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public int countHigherSemester(String semester) {
        Semester semesterValue;
        try {
            semesterValue = Semester.valueOf(semester);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int count = 0;
        for (StudyGroup group : groups) {
            if (group.getSemesterEnum().getN() > semesterValue.getN()) count++;
        }

        return count;
    }

    public List<StudyGroup> getStartsFrom(@RequestParam String prefix) {
        if (prefix == null) prefix = "";
        List<StudyGroup> tempGroups = new ArrayList<>();
        for (StudyGroup group : groups) {
            if (group.getName().startsWith(prefix)) {
                tempGroups.add(group);
            }
        }
        return tempGroups;
    }

    public HashSet<FormOfEducation> getUniqueForms() {
        HashSet<FormOfEducation> set = new HashSet<>();
        for (StudyGroup group : groups) set.add(group.getFormOfEducation());
        return set;
    }

    public int countExpelled() {
        int cntExpelled = 0;
        for (StudyGroup group: groups) {
            cntExpelled += group.getExpelledStudents();
        }
        return cntExpelled + cntExpelledByDelete;
    }
}
