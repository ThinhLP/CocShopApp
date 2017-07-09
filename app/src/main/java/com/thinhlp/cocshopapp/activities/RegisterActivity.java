package com.thinhlp.cocshopapp.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.commons.ApiUtils;
import com.thinhlp.cocshopapp.entities.RegisterError;
import com.thinhlp.cocshopapp.services.UserService;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private UserService userService;
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private EditText edtEmail;
    private EditText edtConfimEmail;
    private EditText edtFirstname;
    private EditText edtLastname;
    private EditText edtPhone;
    private TextView txtBirth;
    private final String dateFormat = "yyyy-MM-dd";
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Get service
        userService = ApiUtils.getUserService();

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtConfimEmail = (EditText) findViewById(R.id.edtConfirmEmail);
        edtFirstname = (EditText) findViewById(R.id.edtFirstname);
        edtLastname = (EditText) findViewById(R.id.edtLastname);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        txtBirth = (TextView) findViewById(R.id.txtBirth);
    }

    public void register(View view) {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();
        String email = edtEmail.getText().toString();
        String confirmEmail = edtConfimEmail.getText().toString();
        String firstname = edtFirstname.getText().toString();
        String lastname = edtLastname.getText().toString();
        String phone = edtPhone.getText().toString();
        String date = txtBirth.getText().toString();

        // Check Empty
        if (username.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()
                || email.isEmpty()
                || confirmEmail.isEmpty()
                || lastname.isEmpty()
                || firstname.isEmpty()
                || phone.isEmpty()
                || date.equals(dateFormat)) {
            Toast.makeText(getBaseContext(), "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check match Password
        if (!password.equals(confirmPassword)) {
            Toast.makeText(getBaseContext(), "Password doesn't match!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check match Password
        if (!email.equals(confirmEmail)) {
            Toast.makeText(getBaseContext(), "Email doesn't match!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check exist Username
        if (false) {
            Toast.makeText(getBaseContext(), "Username has been existed!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check exist Email
        if (false) {
            Toast.makeText(getBaseContext(), "Email has been existed!", Toast.LENGTH_SHORT).show();
            return;
        }

        userService.register(username, password, firstname, lastname, email, date, phone).enqueue(new Callback<RegisterError>() {
            @Override
            public void onResponse(Call<RegisterError> call, Response<RegisterError> response) {
                int statusCode = response.body().getCode();

                if (statusCode == 200) {
                    Toast.makeText(getBaseContext(), "Sign up sucessfully", Toast.LENGTH_SHORT).show();
                    returnLogin();
                }

                if (statusCode == 400) {
                    String status = response.body().getMessages().get(0);
                    Toast.makeText(getBaseContext(), status, Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<RegisterError> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void pickDate(View view) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = "yyyy-MM-dd";
                String sMonth = (month + 1) + "";
                String sDay = dayOfMonth + "";

                if (sMonth.length() <= 1) {
                    sMonth = ("0" + sMonth);
                }

                if (sDay.length() <= 1) {
                    sDay = ("0" + sDay);
                }
                date = year + "-" + sMonth + "-" + sDay;
                txtBirth.setText(date);

                Toast.makeText(getBaseContext(), date, Toast.LENGTH_SHORT).show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            returnLogin();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public void returnLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
