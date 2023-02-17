package org.ksushka.group.endpoints;

import org.ksulabut.soa.lab4.*;
import org.ksushka.group.model.FormOfEducation;
import org.ksushka.group.model.StudyGroup;
import org.ksushka.group.services.ConvertService;
import org.ksushka.group.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.HashSet;
import java.util.List;

@Endpoint
public class GroupEndpoint {
    private static final String NAMESPACE_URI = "http://ksulabut.org/soa/lab4";

    @Autowired
    GroupService groupService;

    @Autowired
    ConvertService convertService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllGroupsRequest")
    @ResponsePayload
    public GetAllGroupsResponse getAllGroups(@RequestPayload GetAllGroupsRequest req) {
        GetAllGroupsResponse resp = new GetAllGroupsResponse();
        try {
            List<StudyGroup> groups = groupService.getAll(req.getPageNumber(), req.getPageSize(), req.getSortType(), req.getOrderBy(), req.getFilterBy());
            for (StudyGroup group : groups) {
                SoapStudyGroup soapStudyGroup = convertService.studyGroup(group);
                resp.getStudyGroup().add(soapStudyGroup);
            }

            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(200);
            resp.setServiceStatus(status);
        } catch (ResponseStatusException ex) {
            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(ex.getRawStatusCode());
            resp.setServiceStatus(status);
        }
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addGroupRequest")
    @ResponsePayload
    public AddGroupResponse addGroup(@RequestPayload AddGroupRequest req) {
        AddGroupResponse resp = new AddGroupResponse();
        try {
            StudyGroup group = convertService.studyGroup(req.getStudyGroup());
            StudyGroup result = groupService.add(group);
            resp.setStudyGroup(convertService.studyGroup(result));

            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(200);
            resp.setServiceStatus(status);
        } catch (ResponseStatusException ex) {
            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(ex.getRawStatusCode());
            resp.setServiceStatus(status);
        }
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getGroupByIdRequest")
    @ResponsePayload
    public GetGroupByIdResponse getGroupById(@RequestPayload GetGroupByIdRequest req) {
        GetGroupByIdResponse resp = new GetGroupByIdResponse();
        try {
            StudyGroup result = groupService.get(req.getId());
            resp.setStudyGroup(convertService.studyGroup(result));

            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(200);
            resp.setServiceStatus(status);
        } catch (ResponseStatusException ex) {
            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(ex.getRawStatusCode());
            resp.setServiceStatus(status);
        }
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "modifyGroupRequest")
    @ResponsePayload
    public ModifyGroupResponse modifyGroup(@RequestPayload ModifyGroupRequest req) {
        ModifyGroupResponse resp = new ModifyGroupResponse();
        try {
            StudyGroup group = convertService.studyGroup(req.getStudyGroup());
            StudyGroup result = groupService.modify(req.getId(), group);
            resp.setStudyGroup(convertService.studyGroup(result));

            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(200);
            resp.setServiceStatus(status);
        } catch (ResponseStatusException ex) {
            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(ex.getRawStatusCode());
            resp.setServiceStatus(status);
        }
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteGroupRequest")
    @ResponsePayload
    public DeleteGroupResponse deleteGroup(@RequestPayload DeleteGroupRequest req) {
        DeleteGroupResponse resp = new DeleteGroupResponse();
        try {
            groupService.delete(req.getId());

            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(200);
            resp.setServiceStatus(status);
        } catch (ResponseStatusException ex) {
            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(ex.getRawStatusCode());
            resp.setServiceStatus(status);
        }
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "countHigherSemesterRequest")
    @ResponsePayload
    public CountHigherSemesterResponse countHigherSemester(@RequestPayload CountHigherSemesterRequest req) {
        CountHigherSemesterResponse resp = new CountHigherSemesterResponse();
        try {
            int count = groupService.countHigherSemester(req.getSemester());
            resp.setGroupCount(count);

            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(200);
            resp.setServiceStatus(status);
        } catch (ResponseStatusException ex) {
            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(ex.getRawStatusCode());
            resp.setServiceStatus(status);
        }
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStartsFromRequest")
    @ResponsePayload
    public GetStartsFromResponse getStartsFrom(@RequestPayload GetStartsFromRequest req) {
        GetStartsFromResponse resp = new GetStartsFromResponse();
        try {
            List<StudyGroup> groups = groupService.getStartsFrom(req.getPrefix());
            for (StudyGroup group : groups) {
                resp.getStudyGroup().add(convertService.studyGroup(group));
            }

            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(200);
            resp.setServiceStatus(status);
        } catch (ResponseStatusException ex) {
            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(ex.getRawStatusCode());
            resp.setServiceStatus(status);
        }
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUniqueFormsRequest")
    @ResponsePayload
    public GetUniqueFormsResponse getUniqueForms(@RequestPayload GetUniqueFormsRequest req) {
        GetUniqueFormsResponse resp = new GetUniqueFormsResponse();
        try {
            HashSet<FormOfEducation> uniqueForms = groupService.getUniqueForms();
            for (FormOfEducation form : uniqueForms) {
                resp.getFormOfEducation().add(form.toString());
            }

            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(200);
            resp.setServiceStatus(status);
        } catch (ResponseStatusException ex) {
            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(ex.getRawStatusCode());
            resp.setServiceStatus(status);
        }
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "countExpelledRequest")
    @ResponsePayload
    public CountExpelledResponse countExpelled(@RequestPayload CountExpelledRequest req) {
        CountExpelledResponse resp = new CountExpelledResponse();
        try {
            int countExpelled = groupService.countExpelled();
            resp.setCountExpelled(countExpelled);

            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(200);
            resp.setServiceStatus(status);
        } catch (ResponseStatusException ex) {
            ServiceStatus status = new ServiceStatus();
            status.setStatusCode(ex.getRawStatusCode());
            resp.setServiceStatus(status);
        }
        return resp;
    }
}
