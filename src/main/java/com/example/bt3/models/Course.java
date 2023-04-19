package com.example.bt3.models;

public class Course {
    private int id;
    private String name;
    private String lecture;
    private int year;
    private String notes;

    public Course(int id, String name, String lecture, int year, String notes) {
        this.id = id;
        this.name = name;
        this.lecture = lecture;
        this.year = year;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLecture() {
        return lecture;
    }

    public int getYear() {
        return year;
    }

    public String getNotes() {
        return notes;
    }
}
