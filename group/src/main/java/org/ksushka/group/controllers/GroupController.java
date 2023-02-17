package org.ksushka.group.controllers;

import org.ksushka.group.model.*;
import org.ksushka.group.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api")
public class GroupController {
//    static LinkedList<StudyGroup> groups = new LinkedList<>();
//
//    int cntExpelledByDelete = 0;
//    @Autowired
//    MatchService matchService;
//
//    @Autowired
//    ParseService parseService;

    @Autowired
    GroupService groupService;

    @GetMapping(value = "group", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<StudyGroup>> getAll(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, defaultValue = "asc") String sortType,
            @RequestParam(required = false, defaultValue = "id") String orderBy,
            @RequestParam(required = false) String filterBy
            ) {
        try {
            List<StudyGroup> groups = groupService.getAll(pageNumber, pageSize, sortType, orderBy, filterBy);
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getStatus());
        }
    }

    @PostMapping(value = "group", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StudyGroup> add(@RequestBody StudyGroup group) {
        try {
            StudyGroup addedGroup = groupService.add(group);
            return new ResponseEntity<>(addedGroup, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getStatus());
        }
    }

    @GetMapping(value = "group/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StudyGroup> get(@PathVariable int id) {
        try {
            StudyGroup foundGroup = groupService.get(id);
            return new ResponseEntity<>(foundGroup, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getStatus());
        }
    }

    @PutMapping(value = "group/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StudyGroup> put(@PathVariable int id, @RequestBody StudyGroup group) {
        try {
            StudyGroup modifiedGroup = groupService.modify(id, group);
            return new ResponseEntity<>(modifiedGroup, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getStatus());
        }
    }

    @DeleteMapping(value = "group/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            groupService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getStatus());
        }
    }

    @GetMapping("group/semester/{semester}")
    public ResponseEntity<Integer> countHigherSemester(@PathVariable String semester) {
        try {
            int groupCount = groupService.countHigherSemester(semester);
            return new ResponseEntity<>(groupCount, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getStatus());
        }
    }

    @GetMapping(value = "group/prefix", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<StudyGroup>> getStartsFrom(@RequestParam String prefix) {
        try {
            List<StudyGroup> groups = groupService.getStartsFrom(prefix);
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getStatus());
        }
    }

    @GetMapping(value = "group/edu_forms", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<HashSet<FormOfEducation>> getUniqueForms() {
        try {
            HashSet<FormOfEducation> forms = groupService.getUniqueForms();
            return new ResponseEntity<>(forms, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getStatus());
        }
    }

    @GetMapping("count_expelled")
    public ResponseEntity<Integer> countExpelled() {
        try {
            int countExpelled = groupService.countExpelled();
            return new ResponseEntity<>(countExpelled, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getStatus());
        }
    }
}
