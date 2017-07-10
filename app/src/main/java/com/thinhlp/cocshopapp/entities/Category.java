package com.thinhlp.cocshopapp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thinhlp on 7/6/17.
 */

public class Category {
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;

    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
