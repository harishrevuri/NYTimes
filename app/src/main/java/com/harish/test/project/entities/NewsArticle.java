package com.harish.test.project.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NewsArticle implements Serializable {

    @SerializedName("web_url")
    private String webUrl;

    @SerializedName("snippet")
    private String snippet;

    @SerializedName("abstract")
    private String abstractData;

    @SerializedName("print_page")
    private String printPage;

    @SerializedName("blog")
    private Object blog;

    @SerializedName("source")
    private String source;

    @SerializedName("multimedia")
    private Multimedia[] multimedia;

    @SerializedName("headline")
    private Headline headline;

    @SerializedName("keywords")
    private List<Keyword> keywords;

    @SerializedName("pub_date")
    private Date pubDate; // 1904-12-31T00:00:00Z

    @SerializedName("document_type")
    private String documentType;

    @SerializedName("type_of_material")
    private String typeOfMaterial;

    @SerializedName("_id")
    private String id;

    @SerializedName("word_count")
    private int wordCount;

    @SerializedName("score")
    private double score;

    public NewsArticle() {

    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getAbstractData() {
        return abstractData;
    }

    public void setAbstractData(String abstractData) {
        this.abstractData = abstractData;
    }

    public String getPrintPage() {
        return printPage;
    }

    public void setPrintPage(String printPage) {
        this.printPage = printPage;
    }

    public Object getBlog() {
        return blog;
    }

    public void setBlog(Object blog) {
        this.blog = blog;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

//    public Object[] getMultimedia() {
//        return multimedia;
//    }
//
//    public void setMultimedia(Object[] multimedia) {
//        this.multimedia = multimedia;
//    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            NewsArticle other = (NewsArticle) obj;
            return this.id.equals(other.id);
        } catch (Exception e) {
            return super.equals(obj);
        }
    }

    @Override
    public String toString() {
        return this.abstractData;
    }

    public String getTitle() {
        if (headline != null) {
            return headline.getMain();
        }
        return snippet;
    }

    public String getThumbImage() {
        if (multimedia != null && multimedia.length > 0) {
            String fallBackUrl = "";
            for (Multimedia media : multimedia) {
                if (media.getType().equals("image")) {
                    fallBackUrl = media.getUrl();
                    if (media.getSubtype().equals("thumbnail")) {
                        return media.getUrl();
                    }
                }
            }
            return fallBackUrl;
        }
        return "";
    }
}
