package com.exchange.suppliesme;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://files.000webhost.com/register.php";
    private Map<String, String> params;

    public RegisterRequest(String messengername, String zip_code, String email, String password, String purpose, Response .Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("messengername", messengername);
        params.put("zip_code", zip_code);
        params.put("email", email);
        params.put("password", password);
        params.put("purpose", purpose);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}