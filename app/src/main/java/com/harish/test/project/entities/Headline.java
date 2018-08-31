package com.harish.test.project.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Headline implements Serializable {

    @SerializedName("main")
    private String main;

    @SerializedName("kicker")
    private Object kicker;

    @SerializedName("content_kicker")
    private Object contentKicker;

    @SerializedName("print_headline")
    private Object printHeadline;

    @SerializedName("name")
    private Object name;

    @SerializedName("seo")
    private Object seo;

    @SerializedName("sub")
    private Object sub;

    public Headline() {

    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public Object getKicker() {
        return kicker;
    }

    public void setKicker(Object kicker) {
        this.kicker = kicker;
    }

    public Object getContentKicker() {
        return contentKicker;
    }

    public void setContentKicker(Object contentKicker) {
        this.contentKicker = contentKicker;
    }

    public Object getPrintHeadline() {
        return printHeadline;
    }

    public void setPrintHeadline(Object printHeadline) {
        this.printHeadline = printHeadline;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getSeo() {
        return seo;
    }

    public void setSeo(Object seo) {
        this.seo = seo;
    }

    public Object getSub() {
        return sub;
    }

    public void setSub(Object sub) {
        this.sub = sub;
    }
}
