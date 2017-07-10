package com.thinhlp.cocshopapp.listeners;

/**
 * Created by thinhlp on 7/10/17.
 */

public interface CartListener {
    void updateQuantity(int id, int quantity);
    void deleteCartItem(int id);
}
