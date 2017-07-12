package com.thinhlp.cocshopapp.services;

import com.thinhlp.cocshopapp.entities.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by thinhlp on 7/12/17.
 */

public interface OrderService {

    @POST("checkout")
    Call<Void> checkout(@Header("Content-Type") String contentType, @Body Order order);

}
