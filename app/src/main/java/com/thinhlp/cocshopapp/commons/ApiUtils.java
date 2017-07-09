package com.thinhlp.cocshopapp.commons;

import com.thinhlp.cocshopapp.remote.RetrofitClient;
import com.thinhlp.cocshopapp.services.UserService;

/**
 * Created by thinhlp on 7/4/17.
 */

public class ApiUtils {

//    public static final String BASE_URL = "http://192.168.2.31:8080/api/";
//    public static final String BASE_URL = "http://192.168.1.104:8080/api/";
    public static final String BASE_URL = "http://10.0.2.2:8080/api/";

    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
