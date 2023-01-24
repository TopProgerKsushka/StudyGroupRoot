package org.ksushka.group.controllers;

import org.ksushka.group.HttpStatusThrowable;
import org.ksushka.group.ejb.StudyGroupManager;
import org.ksushka.group.messages.DeleteResult;
import org.ksushka.group.messages.IntegerResponse;
import org.ksushka.group.messages.PutResult;
import org.ksushka.group.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api")
public class GroupController {
    static LinkedList<StudyGroup> groups = new LinkedList<>();

    int totalExpelled = 0;

    @Resource(lookup = "java:global/ejb/StudyGroupManagerImpl!org.ksushka.group.ejb.StudyGroupManager")
    StudyGroupManager manager;

    @GetMapping(value = "group", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<StudyGroup>> getAll(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, defaultValue = "asc") String sortType,
            @RequestParam(required = false, defaultValue = "id") String orderBy,
            @RequestParam(required = false) String filterBy
            ) {
        try {
            return new ResponseEntity<>(manager.getAll(groups, pageNumber, pageSize, sortType, orderBy, filterBy), HttpStatus.OK);
        } catch (HttpStatusThrowable t) {
            return new ResponseEntity<>(HttpStatus.resolve(t.getStatus()));
        }
    }

    @PostMapping(value = "group", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StudyGroup> add(@RequestBody StudyGroup group) {
        try {
            groups = manager.add(groups, group);
            return new ResponseEntity<>(group, HttpStatus.OK);
        } catch (HttpStatusThrowable t) {
            return new ResponseEntity<>(HttpStatus.resolve(t.getStatus()));
        }
    }

    @GetMapping(value = "group/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StudyGroup> get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(manager.get(groups, id), HttpStatus.OK);
        } catch (HttpStatusThrowable t) {
            return new ResponseEntity<>(HttpStatus.resolve(t.getStatus()));
        }
    }

    @PutMapping(value = "group/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StudyGroup> put(@PathVariable int id, @RequestBody StudyGroup group) {
        try {
            PutResult result = manager.put(groups, id, group);
            groups = result.groups;
            totalExpelled += result.cntExpelled;
            return new ResponseEntity<>(group, HttpStatus.OK);
        } catch (HttpStatusThrowable t) {
            return new ResponseEntity<>(HttpStatus.resolve(t.getStatus()));
        }
    }

    @DeleteMapping(value = "group/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            DeleteResult result = manager.delete(groups, id);
            groups = result.groups;
            totalExpelled += result.cntExpelled;
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpStatusThrowable t) {
            return new ResponseEntity<>(HttpStatus.resolve(t.getStatus()));
        }
    }

    @GetMapping("group/semester/{semester}")
    public ResponseEntity<Integer> countHigherSemester(@PathVariable String semester) {
        try {
            return new ResponseEntity<>(manager.countHigherSemester(groups, Semester.valueOf(semester)), HttpStatus.OK);
        } catch (HttpStatusThrowable t) {
            return new ResponseEntity<>(HttpStatus.resolve(t.getStatus()));
        }
    }

    @GetMapping(value = "group/prefix", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<StudyGroup>> getStartsFrom(@RequestParam String prefix) {
        try {
            return new ResponseEntity<>(manager.getStartsFrom(groups, prefix), HttpStatus.OK);
        } catch (HttpStatusThrowable t) {
            return new ResponseEntity<>(HttpStatus.resolve(t.getStatus()));
        }
    }

    @GetMapping(value = "group/edu_forms", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<HashSet<FormOfEducation>> getUniqueForms() {
        try {
            return new ResponseEntity<>(manager.getUniqueForms(groups), HttpStatus.OK);
        } catch (HttpStatusThrowable t) {
            return new ResponseEntity<>(HttpStatus.resolve(t.getStatus()));
        }
    }

    @GetMapping("count_expelled")
    public ResponseEntity<IntegerResponse> countExpelled() {
        return new ResponseEntity<>(new IntegerResponse(totalExpelled), HttpStatus.OK);
    }

}
