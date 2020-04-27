package com.smzn.quizapp.model;

public class Categories {

    public static final int PROGRAMMING = 1;
    public static final int DATA_STRUCTURE = 2;
    public static final int PHYSICS = 3;
    public static final int GRAPH_THEORY = 4;


    private int id;
    private String name;

    public Categories() {
    }

    public Categories(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
