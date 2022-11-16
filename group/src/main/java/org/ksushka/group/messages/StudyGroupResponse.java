package org.ksushka.group.messages;

import org.ksushka.group.model.*;

public class StudyGroupResponse {
    public String status;
    public StudyGroup studyGroup;
    public String error;
    public static StudyGroupResponse ok(StudyGroup studyGroup) {
        StudyGroupResponse r = new StudyGroupResponse();
        r.status = "ok";
        r.studyGroup = studyGroup;
        return r;
    }
    public static StudyGroupResponse error(String message) {
        StudyGroupResponse r = new StudyGroupResponse();
        r.status = "error";
        r.error = message;
        return r;
    }
}
