package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class Box {
    private String name;
    private int id;
    private int pos;
    private int length;
    private int level;
    private int parentId;
    private int offset;
    ByteBuffer buffer;
    private boolean isRead = false;
    String describe = "box分两种，普通box与fullbox";
    private static final String TAG = "Box";

    public Box(String name, int id, int pos, int length, int level, int parentId) {
        this.id = id;
        this.pos = pos;
        this.length = length;
        this.level = level;
        this.parentId = parentId;
        this.name = name;
        buffer = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder());
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
        Log.e(TAG, "setOffset: name" + "=" + name + "\n offset" + "=" + offset);
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
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

    @Override
    public String toString() {
        return "Box{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", pos=" + pos +
                ", length=" + length +
                ", level=" + level +
                ", parentId=" + parentId +
                '}';
    }

    public void read(SpannableStringBuilder[] builders, int pos, int length, FileChannel fileChannel, Box box) {
        builders[0].append(this.describe);
        builders[1].append("暂无数据");
    }
}
