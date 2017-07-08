package com.thinhlp.cocshopapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.commons.ApiUtils;
import com.thinhlp.cocshopapp.commons.Const;
import com.thinhlp.cocshopapp.commons.Utils;
import com.thinhlp.cocshopapp.entities.User;
import com.thinhlp.cocshopapp.services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private UserService userService;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get service
        userService = ApiUtils.getUserService();
        setContentView(R.layout.activity_login);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        // Set event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }



    public void checkLogin() {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getBaseContext(), "Username and password must be filled", Toast.LENGTH_SHORT).show();
            return;
        }
        userService.checkLogin(username, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                int statusCode  = response.code();
                switch (statusCode) {
                    case 200:
                        makeToast("Login successfully");
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        finish();


                        break;
                    case 401:
                        makeToast("Login failed");
                        break;
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                makeToast("Can't connect to server");
            }
        });
    }

    public void makeToast(String msg) {
        Toast.makeText(this.getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }



}
