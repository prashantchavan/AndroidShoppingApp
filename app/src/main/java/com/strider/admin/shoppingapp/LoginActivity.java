package com.strider.admin.shoppingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private ProgressBar progressBar;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        username = findViewById(R.id.userNameEditText);
        password = findViewById(R.id.passwordEditText);

    }

    public void onLogin(View view) {
        HashMap<String, String> payloads = new HashMap<String, String>();
        payloads.put("username", username.getText().toString());
        payloads.put("password", username.getText().toString());
        SendToServer sendToServer = new SendToServer(this, progressBar, payloads) {
            @Override
            public void getResult(String result) {
                Log.w(TAG, result);
                try {
                    JSONObject resultObject = new JSONObject(result);
                    if (resultObject.getString("result").equals("OK")) {
                        //
                        App.setLogin(LoginActivity.this, App.LOGIN, true);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        App.setLogin(LoginActivity.this, App.LOGIN, false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            };
        };
        sendToServer.connect(App.SERVICE_URL+"login");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_register){
            Intent intent = new Intent(this,RegistrationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
