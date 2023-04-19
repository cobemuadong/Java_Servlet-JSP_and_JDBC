package com.example.bt3.models;

public class Score {
    private int studentId;
    private int courseId;
    private String courseName;
    private int year;
    private double score;

    public Score(int studentId, int courseId, String courseName, int year, double score) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.year = year;
        this.score = score;
    }
    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getYear() {
        return year;
    }

    public double getScore() {
        return score;
    }
}
