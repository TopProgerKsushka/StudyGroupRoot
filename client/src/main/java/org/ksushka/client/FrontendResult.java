package org.ksushka.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.ksushka.client.model.StudyGroup;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FrontendResult {
    public String action;
    public String message;
    public List<StudyGroup> groups;
    public Integer number;
    public List<String> list;

    public static FrontendResult balloon(String message) {
        FrontendResult result = new FrontendResult();
        result.action = "balloon";
        result.message = message;
        return result;
    }

    public static FrontendResult goHome() {
        FrontendResult result = new FrontendResult();
        result.action = "goHome";
        return result;
    }

    public static FrontendResult show(List<StudyGroup> groups) {
        FrontendResult result = new FrontendResult();
        result.action = "show";
        result.groups = groups;
        return result;
    }

    public static FrontendResult number(int number) {
        FrontendResult result = new FrontendResult();
        result.action = "number";
        result.number = number;
        return result;
    }

    public static FrontendResult list(List<String> list) {
        FrontendResult result = new FrontendResult();
        result.action = "list";
        result.list = list;
        return result;
    }
}
