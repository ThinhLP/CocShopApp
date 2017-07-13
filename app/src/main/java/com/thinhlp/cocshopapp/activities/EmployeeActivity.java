package com.thinhlp.cocshopapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.fragments.CartFragment;
import com.thinhlp.cocshopapp.fragments.HistoryFragment;
import com.thinhlp.cocshopapp.fragments.ProductFragment;
import com.thinhlp.cocshopapp.fragments.QRFragment;

/**
 * Created by thinhlp on 7/4/17.
 */

public class EmployeeActivity extends AppCompatActivity {
    private TextView mTextMessage;
    //qr code scanner object

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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.emp_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        //Manually displaying the first fragment - one time only
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.content, ProductFragment.newInstance());
//        transaction.commit();
    }

}
