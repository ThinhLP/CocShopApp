package com.thinhlp.cocshopapp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thinhlp on 7/6/17.
 */

public class Product {
    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("updateAt")
    @Expose
    private String updateAt;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public Product() {
    }

    public Product(Integer productId, String productName, Integer quantity, Integer price, String createAt, String updateAt, String description, Category category, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
