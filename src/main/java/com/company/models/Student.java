package com.company.models;

import java.util.List;

public class Student {
    private String firstName;
    private String secondName;
    private int groupNumber;
    private double average;
    private List<Subject> subjects;

    public Student() {}

    public Student(String firstName, String secondName, int groupNumber, double average, List<Subject> subjects) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.groupNumber = groupNumber;
        this.average = average;
        this.subjects = subjects;
    }

    public static void checkAverage(Group group) {
        List<Student> list = group.getStudents();
        for (int i = 0; i < list.size(); i++) {
            double average;
            double sum = 0;
            for (Subject subject : list.get(i).getSubjects()) {
                sum += subject.getMark();
            }
            average = sum / list.get(i).getSubjects().size();
            list.get(i).setAverage(average);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", groupNumber=" + groupNumber +
                ", average=" + average +
                ", subjects=" + subjects +
                '}';
    }
}
