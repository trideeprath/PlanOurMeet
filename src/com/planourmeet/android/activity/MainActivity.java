package com.planourmeet.android.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.planourmeet.android.R;
import com.planourmeet.android.helper.NetworkAvailable;
import com.planourmeet.android.helper.SendData;

public class MainActivity extends Activity {

	public String phoneNo = null;
	protected static final int SUB_ACTIVITY_REQUEST_CODE = 100;
	public Spinner countryListSpinner =null;
    public Spinner countryZipCodeSpinner= null;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String zipCode= getIntent().getStringExtra("zipCode");
        String countryID = getIntent().getStringExtra("countryID");
        int index = getIntent().getIntExtra("index", 0);
        
        countryListSpinner = (Spinner) findViewById(R.id.CountryList);
        countryListSpinner.setSelection(index);
        
        countryZipCodeSpinner = (Spinner) findViewById(R.id.CountryZipCode);
        countryZipCodeSpinner.setSelection(index);
        
        Toast.makeText(getApplicationContext(),zipCode+"  "+countryID, Toast.LENGTH_SHORT).show(); 
       
        Button ok = (Button) findViewById(R.id.RegistrationOk);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	EditText phoneNumber = (EditText) findViewById(R.id.PhoneNumber);
            	phoneNo = phoneNumber.getText().toString();
            	//Toast.makeText(getApplicationContext(), phoneNo, Toast.LENGTH_SHORT).show();
            	 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                 //nameValuePairs.add(new BasicNameValuePair("PhoneNumber", phoneNo));
                 //nameValuePairs.add(new BasicNameValuePair("Pin","1234"));
                 nameValuePairs.add(new BasicNameValuePair("phn", phoneNo));
                 nameValuePairs.add(new BasicNameValuePair("msg","1234"));
                 if(new NetworkAvailable(getApplicationContext()).haveNetworkConnection()){
                 	 AsyncTask<List<NameValuePair>, Void, String> Response = new SendData().execute(nameValuePairs);
                 	
                 	 //System.out.println(Response.toString());
                 	
                	 Toast.makeText(getApplicationContext(), "available", Toast.LENGTH_SHORT).show();
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
					Intent i = new Intent(MainActivity.this, SelectCountry.class);
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
