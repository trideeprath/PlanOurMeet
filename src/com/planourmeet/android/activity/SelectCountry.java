package com.planourmeet.android.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.planourmeet.android.R;


public class SelectCountry extends Activity{
	

	// List view
    private ListView lv;
    
    // Listview Adapter
    public ArrayAdapter<String> adapter;
 
    // Search EditText
    EditText inputSearch;
    
    public ListView countryListCountry;
    
    public String[] countryNameArray;
      
    public Map<String,Integer> CountryNamehash;
    
    public final static int SUCCESS_RETURN_CODE = 1;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_country);
		
		countryListCountry = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        countryNameArray =  this.getResources().getStringArray(R.array.countryList);    
        
        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_item,R.id.country_name, countryNameArray);
        countryListCountry.setAdapter(adapter); 
        
        CountryNamehash = new HashMap<String,Integer>();
        for(int i = 0 ; i < countryNameArray.length; i++)
        {
           CountryNamehash.put(countryNameArray[i],i);
        }
        
        
        inputSearch.addTextChangedListener(new TextWatcher() {
	            
	        @Override
	        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	        	// When user changed the Text
	        	//this.adapter.getFilter().filter(cs);
	        	adapter.getFilter().filter(cs);
	        	
	        }
	             
	        @Override
	        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	        	// TODO Auto-generated method stub
	                 
	        }
	             
	        @Override
	        public void afterTextChanged(Editable arg0) {
	                // TODO Auto-generated method stub                          
	        }
        });
        
        countryListCountry.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView parent, View view,int position, long _id) {
        	
        		//Toast.makeText(getApplicationContext(), String.valueOf(countryListCountry.getItemAtPosition(position)), Toast.LENGTH_SHORT).show();
        		
        		int index = (Integer) CountryNamehash.get(String.valueOf(countryListCountry.getItemAtPosition(position)));
        		Log.d("index search", String.valueOf(index));
        		/* buggy behaviour , does not go back to the parent activity on the first selection on second goes back */
        		
        		//Toast.makeText(getApplicationContext(), String.valueOf(index), Toast.LENGTH_SHORT).show();	
        		Intent intent = new Intent();       		
        		Bundle b = new Bundle();
        		b.putInt("index", index);
        		intent.putExtras(b);
                setResult(SUCCESS_RETURN_CODE, intent);
        		finish();
				
        	}
        });
		
	}
	


}
