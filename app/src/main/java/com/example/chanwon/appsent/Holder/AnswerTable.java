package com.example.chanwon.appsent.Holder;

/**
 * Created by CHANWON on 7/24/2015.
 */
public class AnswerTable {
    String sentences;
    String sentiment;
    String neutral;
    String postive;
    String negative;

    public AnswerTable() {
    }

    public AnswerTable(String sentences, String sentiment, String neutral, String postive, String negative) {
        this.sentences = sentences;
        this.sentiment = sentiment;
        this.neutral = neutral;
        this.postive = postive;
        this.negative = negative;
    }

    public String getSentences() {
        return sentences;
    }

    public String getSentiment() {
        return sentiment;
    }

    public String getNeutral() {
        return neutral;
    }

    public String getPostive() {
        return postive;
    }

    public String getNegative() {
        return negative;
    }

    public void setSentences(String sentences) {
        this.sentences = sentences;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public void setNeutral(String neutral) {
        this.neutral = neutral;
    }

    public void setPostive(String postive) {
        this.postive = postive;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    @Override
    public String toString() {
        return "AnswerTable{" +
                "sentences='" + sentences + '\'' +
                ", sentiment='" + sentiment + '\'' +
                ", neutral='" + neutral + '\'' +
                ", postive='" + postive + '\'' +
                ", negative='" + negative + '\'' +
                '}';
    }
}