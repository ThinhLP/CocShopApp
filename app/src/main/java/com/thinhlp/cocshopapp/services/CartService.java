package com.thinhlp.cocshopapp.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.thinhlp.cocshopapp.adapters.DBAdapter;
import com.thinhlp.cocshopapp.commons.Const;
import com.thinhlp.cocshopapp.entities.CartItem;
import com.thinhlp.cocshopapp.entities.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinhlp on 7/7/17.
 */

public class CartService {

    private Context context;
    private DBAdapter db;

    public CartService(Context context) {
        this.context = context;
        db = new DBAdapter(context);
    }

    public String addToCart(Product product, int quantity) {
        db.open();
        SharedPreferences sp = context.getSharedPreferences(Const.APP_SHARED_PREFERENCE.SP_NAME, context.MODE_PRIVATE);
        int customerId = sp.getInt(Const.APP_SHARED_PREFERENCE.KEY_USER_ID, 0);
        Cursor itemCursor = db.findItemByCustomerIDAndProductID(customerId, product.getProductId());
        boolean result;
        String msg = "Add to cart successfully!";
        if (itemCursor == null || itemCursor.getCount() == 0) {
            CartItem item = new CartItem(product, quantity, customerId);
            if (quantity > product.getQuantity()) {
                msg = product.getProductName() + " just has " + product.getQuantity() + " left(s)";
            }
            else {
                result = db.insertCartItem(item) > 0;
                if (!result) {
                    msg = "Cannot add this product to cart. Please try again!";
                }
            }
        } else {
            int id = itemCursor.getInt(itemCursor.getColumnIndex(Const.SQLITE.TABLE_NAME.KEY_ROWID));
            int currentQuantity = itemCursor.getInt(itemCursor.getColumnIndex(Const.SQLITE.TABLE_NAME.QUANTITY));
            if (quantity + currentQuantity > product.getQuantity()) {
                msg = product.getProductName() + " just has " + product.getQuantity() + " left(s)";
            } else {
                result = db.updateQuantityOfItem(id, quantity + currentQuantity);
                if (!result) {
                    msg = "Cannot add this product to cart. Please try again!";
                }
            }
        }
        db.close();
        return msg;
    }

    public List<CartItem> getCart() {
        db.open();
        SharedPreferences sp = context.getSharedPreferences(Const.APP_SHARED_PREFERENCE.SP_NAME, context.MODE_PRIVATE);
        int customerId = sp.getInt(Const.APP_SHARED_PREFERENCE.KEY_USER_ID, 0);
        List<CartItem> result = new ArrayList<>();
        Cursor cursor = db.getAllItems(customerId);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer id = cursor.getInt(cursor.getColumnIndex(Const.SQLITE.TABLE_NAME.KEY_ROWID));
                Integer cusId = cursor.getInt(cursor.getColumnIndex(Const.SQLITE.TABLE_NAME.CUSTOMER_ID));
                Integer productId = cursor.getInt(cursor.getColumnIndex(Const.SQLITE.TABLE_NAME.PRODUCT_ID));
                String productName = cursor.getString(cursor.getColumnIndex(Const.SQLITE.TABLE_NAME.PRODUCT_NAME));
                Integer quantity = cursor.getInt(cursor.getColumnIndex(Const.SQLITE.TABLE_NAME.QUANTITY));
                Integer price = cursor.getInt(cursor.getColumnIndex(Const.SQLITE.TABLE_NAME.PRICE));
                String imageUrl = cursor.getString(cursor.getColumnIndex(Const.SQLITE.TABLE_NAME.IMAGE_URL));
                Integer inStock = cursor.getInt(cursor.getColumnIndex(Const.SQLITE.TABLE_NAME.PRODUCT_IN_STOCK));
                result.add(new CartItem(id, cusId, productId, productName, quantity, price, imageUrl, inStock));
                cursor.moveToNext();
            }
        }
        db.close();
        return result;
    }

    public boolean updateQuantity(int id, int quantity) {
        db.open();
        boolean result = db.updateQuantityOfItem(id, quantity);
        db.close();
        return result;
    }

    public boolean deleteItem(int id) {
        db.open();
        boolean result = db.deleteCartItem(id);
        db.close();
        return result;
    }

}
