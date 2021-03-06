package com.planourmeet.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.planourmeet.android.R;
import com.planourmeet.android.helper.GenerateRandomPin;
import com.planourmeet.android.helper.GetCountryCode;
import com.planourmeet.android.helper.GetPhoneNumber;
import com.planourmeet.android.helper.PhoneType;

public class WelcomeScreen extends Activity{
	
	// Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    GetCountryCode getCountryCode = null;
    GetPhoneNumber getPhoneNumber = null;
    GenerateRandomPin gp = null;
    Editor edit = null;
    TelephonyManager tm = null;
    SharedPreferences sp = null;
    String countryID = null;
    String countryZipCode =null;
    String countryName = null;
    int index = 0;
    String phoneType = null;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        //get wether the android device has phone functionality or not 
        boolean isPhone = new PhoneType(this).getPhoneType();
        sp= PreferenceManager.getDefaultSharedPreferences(this);
        
        gp = new GenerateRandomPin();
        String Pin = gp.generateRandomPin();
        edit = sp.edit();
        edit.putString("Pin", Pin);
        
        if(isPhone){
        	
        	edit.putBoolean("isPhone",true);
        }
        else{
        	edit.putBoolean("isPhone", false);
        }
        edit.commit();
        
        
 
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

