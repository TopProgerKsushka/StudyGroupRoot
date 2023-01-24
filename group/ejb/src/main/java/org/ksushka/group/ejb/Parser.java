package org.ksushka.group.ejb;

import java.util.List;

import org.ksushka.group.model.StudyGroup;

public interface Parser {
    public boolean field(String check);
    public List<String> getFields(String orderBy) throws RuntimeException;
    public boolean fitFilters(StudyGroup group, List<String> filters);
    public List<String> getFilters(String filterBy);
}
