package com.example.chanwon.appsent.MenuItem;

/**
 * Created by CHANWON on 7/27/2015.
 */
public class Information {
    public int iconId;
    public String title;

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Information{" +
                "iconId=" + iconId +
                ", title='" + title + '\'' +
                '}';
    }
}
