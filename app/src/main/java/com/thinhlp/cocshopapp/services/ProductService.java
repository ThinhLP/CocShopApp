package com.thinhlp.cocshopapp.services;

import com.thinhlp.cocshopapp.entities.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by thinhlp on 7/7/17.
 */

public interface ProductService {

    @GET("product")
    Call<List<Product>> listProducts();
}
