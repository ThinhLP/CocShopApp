package com.thinhlp.cocshopapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;
import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.commons.Const;
import com.thinhlp.cocshopapp.entities.Cart;
import com.thinhlp.cocshopapp.entities.CartItem;
import com.thinhlp.cocshopapp.entities.Order;
import com.thinhlp.cocshopapp.fragments.CartFragment;
import com.thinhlp.cocshopapp.fragments.HistoryFragment;
import com.thinhlp.cocshopapp.fragments.QRFragment;
import com.thinhlp.cocshopapp.services.CartService;

import java.util.List;

/**
 * Created by thinhlp on 7/4/17.
 */

public class EmployeeActivity extends AppCompatActivity {
    private TextView txtOrderInfo;
    private IntentIntegrator qrScan;
    //    private CartAdapter cartAdapter = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_qr:
                    selectedFragment = QRFragment.newInstance();
                    break;
                case R.id.navigation_cart:
                    selectedFragment = CartFragment.newInstance();
                    break;
                case R.id.navigation_history:
                    selectedFragment = HistoryFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_emp, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.emp_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        qrScan = new IntentIntegrator(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_emp, QRFragment.newInstance());
        transaction.commit();

        // Remove all current cart
        CartService cartService = new CartService(getBaseContext());
        cartService.deleteAllItem();
    }

    public void scanQR(View view) {
        qrScan.initiateScan();
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    String stringJson = result.getContents();

                    // Update QR Img
                    changeQrImg(stringJson);
                    // Parse JSON Array to Order
                    Gson gson = new Gson();
                    Cart cart = gson.fromJson(stringJson, Cart.class);
                    List<CartItem> cartItems = cart.getCartItems();

                    String orderInfo = "Customer Name: " + cart.getCustomerName() + "\n"
                            + "Order Date: " + cart.getOrderDate();
                    txtOrderInfo = (TextView) findViewById(R.id.txtOrderInfo);
                    txtOrderInfo.setText(orderInfo);
                    // Save customer id
                    SharedPreferences sp = getBaseContext().getSharedPreferences(Const.APP_SHARED_PREFERENCE.SP_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(Const.APP_SHARED_PREFERENCE.KEY_CUSTOMER_ID, cart.getCustomerId());
                    editor.commit();
                    // Save to database
                    CartService cartService = new CartService(getBaseContext());
                    cartService.addListOfCartItem(cartItems);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w("Json-Unable to parse", result.getContents());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void changeQrImg(String json) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(json, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) findViewById(R.id.imgQR)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
