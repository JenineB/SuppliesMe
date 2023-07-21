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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity{
    /*implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener*/

    Button bButton_register;
    RadioGroup purpose_group;
    EditText etMessengername, etZipCode, etEmail, etPassword;
    RadioButton selectedRadioButton;//rbgive_supplies, rbreceive_supplies;
    CheckBox cbShow_Password;
    TextView purpose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        final EditText etMessengername = (EditText) findViewById(R.id.etMessengername);
        final EditText etZipCode = (EditText) findViewById(R.id.etZipCode);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bButton_register = (Button) findViewById(R.id.bButton_register);
        final CheckBox cbShow_Password = (CheckBox) findViewById(R.id.cbShow_Password);
        final RadioGroup purpose_group = (RadioGroup) findViewById(R.id.purpose_group);

        cbShow_Password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        bButton_register.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick (View v){
                // get the selected RadioButton of the group
                    selectedRadioButton = (RadioButton) findViewById(purpose_group.getCheckedRadioButtonId());
                //get RadioButton text
                    String yourChoice = selectedRadioButton.getText().toString();
                // display it as Toast to the user
                    Toast.makeText(RegisterActivity.this, yourChoice, Toast.LENGTH_LONG).show();

                final String messengername = etMessengername.getText().toString();
                final String zip_code = etZipCode.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                final String purpose = selectedRadioButton.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(messengername, zip_code, email, password, purpose, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }});}}
