package com.harish.test.project.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Keyword implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("value")
    private String value;

    @SerializedName("rank")
    private int rank;

    @SerializedName("major")
    private Object major;

    public Keyword() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Object getMajor() {
        return major;
    }

    public void setMajor(Object major) {
        this.major = major;
    }
}
