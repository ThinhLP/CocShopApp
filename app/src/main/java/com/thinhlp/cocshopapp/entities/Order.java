package com.thinhlp.cocshopapp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thinhlp on 7/12/17.
 */

import java.util.List;

public class Order {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("employeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("cartItems")
    @Expose
    private List<CartItem> cartItems = null;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Order(Integer userId, Integer employeeId, List<CartItem> cartItems) {
        this.userId = userId;
        this.employeeId = employeeId;
        this.cartItems = cartItems;
    }
}
