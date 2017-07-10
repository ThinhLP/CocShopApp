package com.thinhlp.cocshopapp.entities;

/**
 * Created by thinhlp on 7/7/17.
 */

public class CartItem {
    private int id;
    private int customerId;
    private int productId;
    private String productName;
    private int quantity;
    private int price;
    private String imageUrl;
    private int productInStock;

    public CartItem() {
    }

    public CartItem(Product product, int quantity, int customerId) {
        this.customerId = customerId;
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.quantity = quantity;
        this.productInStock = product.getQuantity();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

    public CartItem(int id, int customerId, int productId, String productName, int quantity, int price, String imageUrl, int productInStock) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.imageUrl = imageUrl;
        this.productInStock = productInStock;
    }

    public int getProductInStock() {
        return productInStock;
    }

    public void setProductInStock(int productInStock) {
        this.productInStock = productInStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
