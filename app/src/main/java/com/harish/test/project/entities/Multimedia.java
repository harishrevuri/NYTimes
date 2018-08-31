package com.harish.test.project.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Multimedia implements Serializable {

    @SerializedName("rank")
    private int rank;

    @SerializedName("subtype")
    private String subtype;

    @SerializedName("caption")
    private String caption;

    @SerializedName("credit")
    private Object credit;

    @SerializedName("type")
    private String type;

    @SerializedName("url")
    private String url;

    @SerializedName("height")
    private int height;

    @SerializedName("width")
    private int width;

    @SerializedName("subType")
    private String subType;

    @SerializedName("crop_name")
    private String cropName;

    public Multimedia() {

    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Object getCredit() {
        return credit;
    }

    public void setCredit(Object credit) {
        this.credit = credit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }
}
