package com.mayankattri.mc_assignment_3;

/**
 * Created by mayank on 1/10/16.
 */
public class Student {
    private int roll;
    private String name;
    private String email;
    private String batch;
    private String contact;
    private String current_sem;

    public Student() {
    }

    public Student(int roll, String name, String email, String batch, String contact) {
        this.roll = roll;
        this.name = name;
        this.email = email;
        this.batch = batch;
        this.contact = contact;
    }

//    public Student(String name, String email) {
//        this.name = name;
//        this.email = email;
//    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getRoll() {
        return roll;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getBatch() {
        return batch;
    }

    public String getContact() {
        return contact;
    }
}
