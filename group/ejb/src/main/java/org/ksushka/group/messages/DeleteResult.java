package org.ksushka.group.messages;

import java.io.Serializable;
import java.util.LinkedList;

import org.ksushka.group.model.StudyGroup;

public class DeleteResult implements Serializable {
    public LinkedList<StudyGroup> groups;
    public int cntExpelled;
}
