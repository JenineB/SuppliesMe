package com.exchange.suppliesme;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity/* implements View.OnClickListener */{

    Button bButton_login;
    EditText etLoginEmail, etLoginPassword;
    TextView register_link;
    CheckBox cbLoginShow_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        final EditText etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);
        final EditText etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        final Button bButton_login = (Button) findViewById(R.id.bButton_login);
        final TextView register_link = (TextView) findViewById(R.id.register_link);
        final CheckBox cbLoginShow_password = (CheckBox) findViewById(R.id.cbLoginShow_password);

        cbLoginShow_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bButton_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.bButton_login:*/
                final String email = etLoginEmail.getText().toString();
                final String password = etLoginPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                String messengername = jsonResponse.getString("messengername");
                                String zip_code = jsonResponse.getString("zip_code");

                                Intent intent = new Intent(LoginActivity.this, ChooseActivity.class);
                                intent.putExtra("messengername", messengername);
                                intent.putExtra("zip_code", zip_code);

                                LoginActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
            }
        });
    }}
