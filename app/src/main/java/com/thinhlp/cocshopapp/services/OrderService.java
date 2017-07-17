package com.thinhlp.cocshopapp.services;

import com.thinhlp.cocshopapp.entities.Order;
import com.thinhlp.cocshopapp.entities.OrderDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by thinhlp on 7/12/17.
 */

public interface OrderService {

    @POST("checkout")
    Call<Void> checkout(@Header("Content-Type") String contentType, @Body Order order);

    @POST("orders")
    Call<List<OrderDto>> getOrders(@Query("customerId") Integer customerId);

    @POST("orders/employee")
    Call<List<OrderDto>> getOrdersByEmployee(@Query("empId") Integer empId);
}
