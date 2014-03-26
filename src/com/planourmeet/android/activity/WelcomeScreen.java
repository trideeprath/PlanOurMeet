package com.planourmeet.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.planourmeet.android.R;
import com.planourmeet.android.helper.GetCountryCode;
import com.planourmeet.android.helper.GetPhoneNumber;

public class WelcomeScreen extends Activity{
	
	// Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    GetCountryCode getCountryCode = null;
    GetPhoneNumber getPhoneNumber = null;
    String countryID = null;
    String countryZipCode =null;
    String countryName = null;
    int index = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        
 
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
            	getCountryCode = new GetCountryCode(WelcomeScreen.this);
            	countryZipCode = getCountryCode.GetCountryZipCode();
            	countryID = getCountryCode.GetCountryID();
            	countryName = getCountryCode.GetCountryName();
            	index = getCountryCode.GetIndex();
            	
                
            	Log.d("zip", countryZipCode);
            	Log.d("id" , countryID);
            	Log.d("countryname",countryName);
            	Log.d("index",String.valueOf(index));
            	
            	//Locale l = new Locale("", countryID);
            	//Locale l = new Locale("", "FM");
            	//String countryName = l.getDisplayCountry();
   
            	getPhoneNumber = new GetPhoneNumber(WelcomeScreen.this);
            	String phoneNumber = getPhoneNumber.getNumber();
            	
                Intent i = new Intent(WelcomeScreen.this, VerifyPhoneNumber.class);
                i.putExtra("zipCode",countryZipCode);
                i.putExtra("countryID", countryID);
                i.putExtra("index", index);
                i.putExtra("phoneNumber", phoneNumber);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}

