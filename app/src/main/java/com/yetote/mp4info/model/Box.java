package com.yetote.mp4info.model;

public class Box {
    private String name;
    private int id;
    private int pos;
    private int length;
    private int level;
    private int parentId;

    public Box(String name, int id, int pos, int length, int level, int parentId) {
        this.id = id;
        this.pos = pos;
        this.length = length;
        this.level = level;
        this.parentId = parentId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
