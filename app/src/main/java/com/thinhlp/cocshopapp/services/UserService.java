package com.thinhlp.cocshopapp.services;

import com.thinhlp.cocshopapp.entities.RegisterError;
import com.thinhlp.cocshopapp.entities.User;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by thinhlp on 7/4/17.
 */

public interface UserService {

    @POST("login")
    Call<User> checkLogin(@Query("username") String username, @Query("password") String password);

    @POST("register")
    Call<RegisterError> register(@Query("username") String username, @Query("password") String password,
                                 @Query("firstName") String firstName, @Query("lastName") String lastName,
                                 @Query("email") String email, @Query("birthday") String birthday,
                                 @Query("phone") String phone);

}
