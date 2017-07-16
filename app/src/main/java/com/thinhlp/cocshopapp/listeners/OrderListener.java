package com.thinhlp.cocshopapp.listeners;

import com.thinhlp.cocshopapp.entities.OrderDetail;
import com.thinhlp.cocshopapp.entities.OrderDto;

import java.util.List;

/**
 * Created by thinhlp on 7/16/17.
 */

public interface OrderListener {

    void viewOrderItem(OrderDto order);
}
