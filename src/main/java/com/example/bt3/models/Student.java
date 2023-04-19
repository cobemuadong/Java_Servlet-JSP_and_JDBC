package com.example.bt3.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Student {
    private int id;
    private String name;
    private int grade;
    private Date birthday;
    private String address;
    private String notes;

    public Student(int id, String name, String grade, Date birthday, String address, String notes) {
        this.id = id;
        this.name = name;
        this.grade = Integer.parseInt(grade);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        this.birthday = LocalDate.parse(birthday, formatter);
        this.birthday = birthday;
        this.address = address;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public String getNotes() {
        return notes;
    }

    public Student() {
    }
}
