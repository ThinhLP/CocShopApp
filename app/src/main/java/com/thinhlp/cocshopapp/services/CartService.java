package com.thinhlp.cocshopapp.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.thinhlp.cocshopapp.adapters.DBAdapter;
import com.thinhlp.cocshopapp.commons.Const;
import com.thinhlp.cocshopapp.entities.CartItem;
import com.thinhlp.cocshopapp.entities.Product;

/**
 * Created by thinhlp on 7/7/17.
 */

public class CartService {

    Context context;

    public CartService(Context context) {
        this.context = context;
    }

    public boolean addToCart(Product product, int quantity) {
        DBAdapter db = new DBAdapter(context);
        db.open();
        SharedPreferences sp = context.getSharedPreferences(Const.APP_SHARED_PREFERENCE.SP_NAME, context.MODE_PRIVATE);
        int customerId = sp.getInt(Const.APP_SHARED_PREFERENCE.KEY_USER_ID, 0);
        Cursor itemCursor = db.findItemByCustomerIDAndProductID(customerId, product.getProductId());
        boolean result = false;
        if (itemCursor == null || itemCursor.getCount() == 0) {
            CartItem item = new CartItem(product, quantity, customerId);
            result = db.insertCartItem(item) > 0;
        } else {
            int id = itemCursor.getInt(itemCursor.getColumnIndex(Const.SQLITE.TABLE_NAME.KEY_ROWID));
            int currentQuantity = itemCursor.getInt(itemCursor.getColumnIndex(Const.SQLITE.TABLE_NAME.QUANTITY));
            result = db.updateQuantityOfItem(id, quantity + currentQuantity);
        }
        return result;
    }
}
