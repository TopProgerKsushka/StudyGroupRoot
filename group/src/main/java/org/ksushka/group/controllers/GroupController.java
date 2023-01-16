package org.ksushka.group.controllers;

import org.ksushka.group.model.*;
import org.ksushka.group.services.MatchService;
import org.ksushka.group.services.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class GroupController {
    static LinkedList<StudyGroup> groups = new LinkedList<>();

    int cntExpelledByDelete = 0;
    @Autowired
    MatchService matchService;

    @Autowired
    ParseService parseService;

    @GetMapping(value = "group", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<StudyGroup>> getAll(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, defaultValue = "asc") String sortType,
            @RequestParam(required = false, defaultValue = "id") String orderBy,
            @RequestParam(required = false) String filterBy
            ) {
        if ((pageSize == null && pageNumber != null) || (pageNumber == null && pageSize != null)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (pageSize != null && (pageSize < 1 || pageNumber < 0)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (sortType != null && !sortType.equals("asc") && !sortType.equals("desc")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<String> filters;
        if (filterBy.equals("")) filterBy = null;

        try {
            filters = parseService.getFilters(filterBy);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        Collections.sort(tempGroups);
        if (sortType != null && sortType.equals("desc")) Collections.reverse(tempGroups);
        if (pageSize != null) return new ResponseEntity<>(tempGroups.subList(pageSize * (pageNumber - 1), Math.min(pageSize * pageNumber, tempGroups.size())), HttpStatus.OK);
        return new ResponseEntity<>(tempGroups, HttpStatus.OK);
    }

    @PostMapping(value = "group", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StudyGroup> add(@RequestBody StudyGroup group) {

        if (!matchService.studyGroup(group)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

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
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @GetMapping(value = "group/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StudyGroup> get(@PathVariable int id) {
        if (groups.isEmpty() || id > groups.getLast().getId()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Optional<StudyGroup> item = groups.stream().filter(g -> g.getId() == id).findFirst();

        try {
            return item.map(studyGroup -> new ResponseEntity<>(studyGroup, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (IndexOutOfBoundsException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "group/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StudyGroup> put(@PathVariable int id, @RequestBody StudyGroup group) {
        if (!matchService.studyGroup(group)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        int listId = -1;
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getId() == id) {
                listId = i;
                break;
            }
        }

        if (listId == -1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        group.setId(groups.get(listId).getId());
        group.setCreationDate(groups.get(listId).getCreationDate());
        group.setExpelledStudents(
                Math.max(groups.get(listId).getStudentsCount() - group.getStudentsCount(), 0));

        groups.set(listId, group);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @DeleteMapping(value = "group/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        if (groups.isEmpty() || id > groups.getLast().getId()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Optional<StudyGroup> item = groups.stream().filter(g -> g.getId() == id).findFirst();
        if (item.isPresent()) {
            cntExpelledByDelete += item.get().getStudentsCount() + item.get().getExpelledStudents();
            groups.remove(item.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("group/semester/{semester}")
    public ResponseEntity<Integer> countHigherSemester(@PathVariable String semester) {
        Semester semesterValue;
        try {
            semesterValue = Semester.valueOf(semester);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        int count = 0;
        for (StudyGroup group : groups) {
            if (group.getSemesterEnum().getN() > semesterValue.getN()) count++;
        }

        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping(value = "group/prefix", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<StudyGroup>> getStartsFrom(@RequestParam String prefix) {
        if (prefix == null) prefix = "";
        List<StudyGroup> tempGroups = new ArrayList<>();
        for (StudyGroup group : groups) {
            if (group.getName().startsWith(prefix)) {
                tempGroups.add(group);
            }
        }
        return new ResponseEntity<>(tempGroups, HttpStatus.OK);
    }

    @GetMapping(value = "group/edu_forms", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<HashSet<FormOfEducation>> getStartsFrom() {
        HashSet<FormOfEducation> set = new HashSet<>();
        for (StudyGroup group : groups) set.add(group.getFormOfEducation());
        return new ResponseEntity<>(set, HttpStatus.OK);
    }

//    @PutMapping(value = "change_edu_form", produces = MediaType.APPLICATION_XML_VALUE)
//    public ResponseEntity<StudyGroup> changeEduForm(@RequestParam Integer id, @RequestParam FormOfEducation eduForm) {
//        int listId = -1;
//        for (int i = 0; i < groups.size(); i++) {
//            if (groups.get(i).getId() == id) {
//                listId = i;
//                break;
//            }
//        }
//
//        if (listId == -1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        groups.get(listId).setFormOfEducation(eduForm);
//
//        return new ResponseEntity<>(groups.get(listId), HttpStatus.OK);
//    }

    @GetMapping("count_expelled")
    public ResponseEntity<Integer> countExpelled() {
        int cntExpelled = 0;
        for (StudyGroup group: groups) {
            cntExpelled += group.getExpelledStudents();
        }
        return new ResponseEntity<>(cntExpelled + cntExpelledByDelete, HttpStatus.OK);
    }

}
