package com.thinhlp.cocshopapp.entities;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by thinhlp on 7/15/17.
 */

public class OrderDetail {
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("product")
    @Expose
    private Product product;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}