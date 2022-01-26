package com.company.models;

import java.util.List;

public class Group {
    private List<Student> students;

    public Group() {

    }

    public Group(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Group{" +
                "students=" + students +
                '}';
    }
}
