package com.thinhlp.cocshopapp.commons;

import com.thinhlp.cocshopapp.remote.RetrofitClient;
import com.thinhlp.cocshopapp.services.ProductService;
import com.thinhlp.cocshopapp.services.UserService;

/**
 * Created by thinhlp on 7/4/17.
 */

public class ApiUtils {


//    public static final String BASE_URL = "http://192.168.1.104:8080/api/1.0/";
    //public static final String BASE_URL = "http://10.0.2.2:8080/api/1.0/";
    public static final String BASE_URL = "http://192.168.2.31:8080/api/1.0/";
    //public static final String BASE_URL = "http://192.168.43.115:8080/api/1.0/";

    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static ProductService getProductService() {
        return RetrofitClient.getClient(BASE_URL).create(ProductService.class);
    }
}
