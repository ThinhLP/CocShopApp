package com.thinhlp.cocshopapp.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.thinhlp.cocshopapp.adapters.database.DBAdapter;
import com.thinhlp.cocshopapp.commons.Const;
import com.thinhlp.cocshopapp.commons.Utils;
import com.thinhlp.cocshopapp.entities.Cart;
import com.thinhlp.cocshopapp.entities.CartItem;
import com.thinhlp.cocshopapp.entities.Order;
import com.thinhlp.cocshopapp.entities.Product;

import java.util.ArrayList;
import java.util.Date;
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

    public Cart createCartObject(List<CartItem> items) {
        if (items.isEmpty())
            return null;
        SharedPreferences sp = context.getSharedPreferences(Const.APP_SHARED_PREFERENCE.SP_NAME, context.MODE_PRIVATE);
        String fullName = sp.getString(Const.APP_SHARED_PREFERENCE.KEY_FULLNAME, "");
        int customerId = Utils.getCurrentUserId(context);
        return new Cart(customerId, fullName, Utils.formatDate(new Date(), "hh:mm:ss dd-MM-yyyy"), items);
    }

    public String addToCart(Product product, int quantity) {
        db.open();
        int customerId = Utils.getCurrentUserId(context);
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
            int id = itemCursor.getInt(itemCursor.getColumnIndex(Const.SQLITE.CART_COLUMN_NAME.KEY_ROWID));
            int currentQuantity = itemCursor.getInt(itemCursor.getColumnIndex(Const.SQLITE.CART_COLUMN_NAME.QUANTITY));
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

    public void addCartItem(CartItem cartItem) {
        db.open();
        db.insertCartItem(cartItem);
        db.close();
    }

    // For employee and admin
    public void addListOfCartItem(List<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            return;
        }

        deleteAllItem();

        for (CartItem cartItem: cartItems) {
            addCartItem(cartItem);
        }
    }

    public List<CartItem> getCart() {
        db.open();

        Cursor cursor;

        if (Utils.isStaff(context)) {
            cursor = db.getAllItems();
        } else {
            int customerId = Utils.getCurrentUserId(context);
            cursor = db.getAllItems(customerId);
        }

        List<CartItem> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer id = cursor.getInt(cursor.getColumnIndex(Const.SQLITE.CART_COLUMN_NAME.KEY_ROWID));
                Integer cusId = cursor.getInt(cursor.getColumnIndex(Const.SQLITE.CART_COLUMN_NAME.CUSTOMER_ID));
                Integer productId = cursor.getInt(cursor.getColumnIndex(Const.SQLITE.CART_COLUMN_NAME.PRODUCT_ID));
                String productName = cursor.getString(cursor.getColumnIndex(Const.SQLITE.CART_COLUMN_NAME.PRODUCT_NAME));
                Integer quantity = cursor.getInt(cursor.getColumnIndex(Const.SQLITE.CART_COLUMN_NAME.QUANTITY));
                Integer price = cursor.getInt(cursor.getColumnIndex(Const.SQLITE.CART_COLUMN_NAME.PRICE));
                String imageUrl = cursor.getString(cursor.getColumnIndex(Const.SQLITE.CART_COLUMN_NAME.IMAGE_URL));
                Integer inStock = cursor.getInt(cursor.getColumnIndex(Const.SQLITE.CART_COLUMN_NAME.PRODUCT_IN_STOCK));
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

    public Order convertCartItemsToOrder(List<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            return null;
        }

        int empId = Utils.getCurrentUserId(context);
        int cusId = Utils.getCustomerID(context);

        return new Order(cusId, empId, cartItems);

    }


    public boolean deleteAllItem() {
        db.open();

        boolean result = false;

        if (Utils.isStaff(context)) {
            result = db.deleteAllCartItem();
        } else {
            int customerId = Utils.getCurrentUserId(context);
            result = db.deleteCartItemsByUserId(customerId);
        }
        db.close();
        return result;
    }
}
