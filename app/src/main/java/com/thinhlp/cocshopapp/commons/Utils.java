package com.thinhlp.cocshopapp.commons;


import android.content.Context;
import android.content.SharedPreferences;

import com.thinhlp.cocshopapp.entities.OrderDetail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by thinhlp on 7/4/17.
 */

public class Utils {

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Integer computeTotalOfOrderDetails(List<OrderDetail> list) {
        if (list == null || list.isEmpty())
            return 0;
        int total = 0;
        for (OrderDetail orderDetail: list) {
            total += orderDetail.getProduct().getPrice() * orderDetail.getQuantity();
        }
        return total;
    }

    public static Integer getCurrentUserId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Const.APP_SHARED_PREFERENCE.SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(Const.APP_SHARED_PREFERENCE.KEY_USER_ID, 0);
    }
}
