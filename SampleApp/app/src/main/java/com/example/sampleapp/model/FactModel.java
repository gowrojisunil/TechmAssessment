package com.example.sampleapp.model;
/**
 * Created by sunil gowroji on 07/07/15.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FactModel implements Serializable {

    @SerializedName("title")
    private String title = "";

    @SerializedName("rows")
    private List<RowsModel> row;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RowsModel> getRow() {
        return row;
    }

    public void setRow(List<RowsModel> row) {
        this.row = row;
    }
}
