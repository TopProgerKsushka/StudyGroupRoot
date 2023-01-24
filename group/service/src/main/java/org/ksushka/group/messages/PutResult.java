package org.ksushka.group.messages;

import org.ksushka.group.model.StudyGroup;

import java.io.Serializable;
import java.util.LinkedList;

public class PutResult implements Serializable {
    public LinkedList<StudyGroup> groups;
    public int cntExpelled;
}
