package com.example.sampleapp.model;
/**
 * Created by sunil gowroji on 07/07/15.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RowsModel implements Serializable {

    @SerializedName("title")
    private String title = "";

    @SerializedName("description")
    private String description = "";

    @SerializedName("imageHref")
    private String imageHref = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }
}
