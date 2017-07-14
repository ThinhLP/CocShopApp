package com.thinhlp.cocshopapp.entities;

import java.util.Date;
import java.util.List;

/**
 * Created by thinhlp on 7/14/17.
 */

public class Cart {
    private String customerName;
    private String orderDate;
    private List<CartItem> cartItems;

    public Cart() {
    }

    public Cart(String customerName, String orderDate, List<CartItem> cartItems) {
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.cartItems = cartItems;
    }

    public String getCustomerId() {
        return customerName;
    }

    public void setCustomerId(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
