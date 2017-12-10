package com.strider.admin.shoppingapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private EditText username,password,firstName,lastName;
    private RadioButton male,female;
    private CheckBox isTravel,isBusiness,isHolidays;
    private ProgressBar progressBar;
    private static final String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register");
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        username = (EditText)findViewById(R.id.userNameEditText);
        password = (EditText)findViewById(R.id.passwordEditText);
        firstName = (EditText)findViewById(R.id.firstNameEditText);
        lastName = (EditText)findViewById(R.id.lastNameEditText);
        male = (RadioButton)findViewById(R.id.maleRadioBtn);
        female = (RadioButton)findViewById(R.id.femaleRadioBtn);
        isBusiness = (CheckBox) findViewById(R.id.businessCheckBox);
        isHolidays = (CheckBox)findViewById(R.id.holidaysCheckBox);
        isTravel = (CheckBox)findViewById(R.id.travelCheckBox);
    }

    public void onRegister(View view)
    {
        HashMap<String,String> payloads = new HashMap<String,String>();
        payloads.put("username",username.getText().toString());
        payloads.put("password",password.getText().toString());
        payloads.put("firstname",firstName.getText().toString());
        payloads.put("lastname",lastName.getText().toString());

        // gender 1 = male, 2 = female
        if (male.isChecked()){
            payloads.put("gender","1");
        }else{
            payloads.put("gender","2");
        }
        if (isBusiness.isChecked()){
            payloads.put("is_business","1");
        }else{
            payloads.put("is_business","2");
        }
        if (isTravel.isChecked()){
            payloads.put("is_travel","1");
        }else{
            payloads.put("is_travel","2");
        }
        if (isHolidays.isChecked()){
            payloads.put("is_holidays","1");
        }else{
            payloads.put("is_holidays","2");
        }

        SendToServer sendToServer = new SendToServer(this,progressBar,payloads) {
            @Override
            public void getResult(String result) {
                Log.w(TAG,result);
                try {
                    JSONObject resultObject = new JSONObject(result);
                    if (resultObject.getString("status").equals("OK")){
                        //
                        App.showToast(RegistrationActivity.this,"Successfully Registered");
                        finish();
                    }else{
                        App.showToast(RegistrationActivity.this,resultObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        sendToServer.connect(App.SERVICE_URL+"registration");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
