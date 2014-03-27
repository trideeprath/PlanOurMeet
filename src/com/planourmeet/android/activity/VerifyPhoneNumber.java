package com.planourmeet.android.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.planourmeet.android.R;
import com.planourmeet.android.helper.GenerateRandomPin;
import com.planourmeet.android.helper.GetPhoneNumber;
import com.planourmeet.android.helper.NetworkAvailable;
import com.planourmeet.android.helper.SendData;

public class VerifyPhoneNumber extends Activity {

	public String phoneNo = null;
	protected static final int SUB_ACTIVITY_REQUEST_CODE = 100;
	public Spinner countryListSpinner =null;
    public Spinner countryZipCodeSpinner= null;
    public GenerateRandomPin gp = null;
    public GetPhoneNumber getPhoneNumber= null;
    public TextView incorrectPhoneNumber = null;
    public SharedPreferences sp = null;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_phone_number);
        String zipCode= getIntent().getStringExtra("zipCode");
        String countryID = getIntent().getStringExtra("countryID");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        if(phoneNumber!=null){
        	Log.d("get phoneNumber", phoneNumber);
        }
        
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isPhone = sp.getBoolean("isPhone", false);
        Log.d("newis phone",String.valueOf(isPhone));
        
        int index = getIntent().getIntExtra("index", 0);
        
        countryListSpinner = (Spinner) findViewById(R.id.CountryList);
        countryListSpinner.setSelection(index);
        
        countryZipCodeSpinner = (Spinner) findViewById(R.id.CountryZipCode);
        countryZipCodeSpinner.setSelection(index);
        
        Toast.makeText(getApplicationContext(),zipCode+"  "+countryID, Toast.LENGTH_SHORT).show(); 
       
        EditText phoneNumberEditText = (EditText) findViewById(R.id.PhoneNumber);
        if(phoneNumber!=null){
        	phoneNumberEditText.setText(phoneNumber);
        }
        Button ok = (Button) findViewById(R.id.RegistrationOk);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	gp = new GenerateRandomPin();
                String Pin = gp.generateRandomPin();
            	EditText phoneNumber = (EditText) findViewById(R.id.PhoneNumber);
            	phoneNo = phoneNumber.getText().toString();
            	
            	getPhoneNumber = new GetPhoneNumber(VerifyPhoneNumber.this);
            	boolean isPhoneNumberValid = getPhoneNumber.phoneNumberValidate(phoneNo);
            	
            	//Toast.makeText(getApplicationContext(), phoneNo, Toast.LENGTH_SHORT).show();
            	 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                 //nameValuePairs.add(new BasicNameValuePair("PhoneNumber", phoneNo));
                 //nameValuePairs.add(new BasicNameValuePair("Pin","1234"));
                 nameValuePairs.add(new BasicNameValuePair("phn", phoneNo));
                 nameValuePairs.add(new BasicNameValuePair("msg",Pin));                
                 
                 Log.d("valid",String.valueOf(isPhoneNumberValid));
                 
                 if(new NetworkAvailable(getApplicationContext()).haveNetworkConnection()){
                	 if(isPhoneNumberValid){
                		 AsyncTask<List<NameValuePair>, Void, String> Response = new SendData(VerifyPhoneNumber.this).execute(nameValuePairs);
                		 Toast.makeText(getApplicationContext(), "available", Toast.LENGTH_SHORT).show();
                	 }
                	 else{
                		 incorrectPhoneNumber = (TextView) findViewById(R.id.IncorrectPhoneNumber);
                		 /*
                		 phoneNumber.setText(null);
                		 phoneNumber.setHint("Number should be 10 digit");
                		 */
                		 incorrectPhoneNumber.setVisibility(1);
                		 
                	 }
                	 
                 }
                 else{
                	 Toast.makeText(getApplicationContext(), "not available", Toast.LENGTH_SHORT).show();
                 }
                 	
            }
        });
        
        countryListSpinner.setOnTouchListener(new OnTouchListener() {	
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Intent i = new Intent(VerifyPhoneNumber.this, SelectCountry.class);
					startActivityForResult(i, SUB_ACTIVITY_REQUEST_CODE);
				}	
				
				return true;
			}
		});
                   
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        setTitle("Verify your phone number");
        return true;
    }
    
    @Override
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SUB_ACTIVITY_REQUEST_CODE){
        	Bundle b = data.getExtras();
        	int index = b.getInt("index", 0);
        	Log.d("index back" , String.valueOf(index));
        	countryListSpinner.setSelection(index);
        	countryZipCodeSpinner.setSelection(index);
        	//Toast.makeText(getApplicationContext(), String.valueOf(index), Toast.LENGTH_SHORT).show();
        }
    }

    
}
