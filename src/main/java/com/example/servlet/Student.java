package com.example.servlet;

public class Student {
    private int id;
    private String fname;
    private String lname;

    public Student(int id, String fname, String lname) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }
}
