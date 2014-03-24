package com.planourmeet.android.helper;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.planourmeet.android.R;

public class GetCountryCode {
	
	public Activity activity = null;
    public String CountryID=null;
    public String CountryZipCode=null;
    public String CountryName=null;
    public int index= 0;
    
	
	public GetCountryCode(Activity welcome_activity){
		activity = welcome_activity;
	}
	
	public String GetCountryZipCode(){
	
       TelephonyManager manager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
       //getNetworkCountryIso
       CountryID= manager.getSimCountryIso().toUpperCase();
       // String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
       //String[] countryCodes =  activity.getResources().getStringArray(R.array.CountryCodesList);
       String[] countryCodes =  activity.getResources().getStringArray(R.array.CountryCodeCountryNameCountryZipList);
       
       for(int i=0;i<countryCodes.length;i++){
    	   String[] g=countryCodes[i].split(",");
           if(g[1].trim().equals(CountryID.trim())){
            	CountryZipCode=g[2];
            	CountryName =g[0];
            	index= i;
            	break;   	
            }
        } 
       return CountryZipCode;
	}

	
	public int GetIndex(){
		return index;
	}

	public String GetCountryID(){
		
		return CountryID;
	}
	
	public String GetCountryName(){
		return CountryName;
	}

}
