package com.thinhlp.cocshopapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.thinhlp.cocshopapp.commons.Const;
import com.thinhlp.cocshopapp.entities.CartItem;

/**
 * Created by thinhlp on 7/7/17.
 */

public class DBAdapter {
    static final String KEY_ROWID = Const.SQLITE.TABLE_NAME.KEY_ROWID;
    static final String CUSTOMER_ID = Const.SQLITE.TABLE_NAME.CUSTOMER_ID;
    static final String PRODUCT_ID = Const.SQLITE.TABLE_NAME.PRODUCT_ID;
    static final String PRODUCT_NAME = Const.SQLITE.TABLE_NAME.PRODUCT_NAME;
    static final String QUANTITY = Const.SQLITE.TABLE_NAME.QUANTITY;
    static final String PRICE = Const.SQLITE.TABLE_NAME.PRICE;
    static final String IMAGE_URL = Const.SQLITE.TABLE_NAME.IMAGE_URL;

    static final String TAG = Const.SQLITE.TAG;
    static final String DATABASE_NAME = Const.SQLITE.DATABASE_NAME;
    static final String DATABASE_TABLE = Const.SQLITE.DATABASE_TABLE;
    static final int DATABASE_VERSION = Const.SQLITE.DATABASE_VERSION;

    static final String DATABASE_CREATE = "create table " + DATABASE_TABLE +
            " (" +  KEY_ROWID +" integer primary key autoincrement, " +
            CUSTOMER_ID + " integer not null, " + PRODUCT_ID +" integer not null, " +
            PRODUCT_NAME + " text not null, " + QUANTITY + " integer not null, " +
            PRICE + " integer not null, " + IMAGE_URL + " text not null);";

    final Context context;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " +
                    newVersion +", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    // OPEN DATABASE
    public DBAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    // CLOSE DATABASE
    public void close() {
        dbHelper.close();
    }

    // INSERT CART ITEM INTO DATABASE
    public long insertCartItem(CartItem item) {
        ContentValues initValues = new ContentValues();
        initValues.put(CUSTOMER_ID, item.getCustomerId());
        initValues.put(PRODUCT_ID, item.getProductId());
        initValues.put(PRODUCT_NAME, item.getProductName());
        initValues.put(QUANTITY, item.getQuantity());
        initValues.put(PRICE, item.getPrice());
        initValues.put(IMAGE_URL, item.getImageUrl());
        return db.insert(DATABASE_TABLE, null, initValues);
    }

    private String[] tables = new String[] {KEY_ROWID, CUSTOMER_ID, PRODUCT_ID, PRODUCT_NAME, QUANTITY, PRICE, IMAGE_URL};

    // FIND ITEM BY CUSTOMER ID AND PRODUCT ID
    public Cursor findItemByCustomerIDAndProductID(int customerId, int productId) {
        Cursor mCursor = db.query(true, DATABASE_TABLE, tables,
                CUSTOMER_ID + "=" + customerId + " AND " + PRODUCT_ID + "=" + productId, null, null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // DELETE A PARTICULAR CART ITEM
    public boolean deleteCartItem(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    // GET ALL CART ITEMS OF CUSTOMER
    public Cursor getAllItems(int customerId) {
        return db.query(DATABASE_TABLE, tables, null, null, null, null, null);
    }

    // UPDATE CART ITEM
    public boolean updateQuantityOfItem(long rowId, int quantity) {
        ContentValues args = new ContentValues();
        args.put(QUANTITY, quantity);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }





}
