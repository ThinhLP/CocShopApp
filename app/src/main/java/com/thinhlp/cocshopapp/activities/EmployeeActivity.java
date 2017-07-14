package com.thinhlp.cocshopapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.entities.CartItem;
import com.thinhlp.cocshopapp.fragments.CartFragment;
import com.thinhlp.cocshopapp.fragments.HistoryFragment;
import com.thinhlp.cocshopapp.fragments.QRFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinhlp on 7/4/17.
 */

public class EmployeeActivity extends AppCompatActivity {
    private TextView txtUserInfo;
    private IntentIntegrator qrScan;
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
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        txtUserInfo = (TextView) findViewById(R.id.txtUserInfo);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.emp_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        qrScan = new IntentIntegrator(this);

        Fragment selectedFragment = QRFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, selectedFragment);
        transaction.commit();
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
                    // Parse JSON Array to CartItem Array
                    JSONArray jsonArray = new JSONArray(result.getContents());
                    JsonParser parser = new JsonParser();
                    JSONObject obj = null;
                    JsonElement mJson = null;
                    Gson gson = new Gson();
                    CartItem cartItem = new CartItem();
                    List<CartItem> cartItems = new ArrayList<CartItem>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        obj = (JSONObject) jsonArray.get(i);
                        mJson = parser.parse(obj.toString());
                        cartItem = gson.fromJson(mJson, CartItem.class);
                        cartItems.add(cartItem);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    Log.w("Json-Unable to parse", result.getContents());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
