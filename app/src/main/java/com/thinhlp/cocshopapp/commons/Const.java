package com.thinhlp.cocshopapp.commons;

/**
 * Created by thinhlp on 7/4/17.
 */

public class Const {
    public static class HTTP_STATUS {
        public static final int OK = 200;
        public static final int NO_CONTENT = 204;
        public static final int UNAUTHORIZED = 401;
    }

    public static class ROLE {
        public static final int ADMIN = 1;
        public static final int EMPLOYEE = 2;
        public static final int CUSTOMER = 3;
    }

    public static class APP_SHARED_PREFERENCE {
        public static final String SP_NAME = "CocShopSharedPreference";
        public static final String KEY_USER_ID = "USER_ID";
    }

    public static class SQLITE {
        public static class TABLE_NAME {
            public static final String KEY_ROWID = "_id";
            public static final String CUSTOMER_ID = "customerId";
            public static final String PRODUCT_ID = "productId";
            public static final String PRODUCT_NAME = "productName";
            public static final String QUANTITY = "quantity";
            public static final String PRICE = "price";
            public static final String IMAGE_URL = "imageUrl";
        }

        public static final String TAG = "DBAdapter";
        public static final String DATABASE_NAME = "CocshopDB";
        public static final String DATABASE_TABLE = "Cart";
        public static final int DATABASE_VERSION = 1;
    }

}
