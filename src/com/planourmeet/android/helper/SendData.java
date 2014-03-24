package com.planourmeet.android.helper;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class SendData  extends AsyncTask<List<NameValuePair>, Void, String>{


	@Override
	protected String doInBackground(List<NameValuePair>... params) {
		 System.out.println(params[0].toString());
		 Log.d("params",params[0].toString());
		
		 
		 HttpClient httpclient = new DefaultHttpClient();
		 //HttpPost httppost = new HttpPost("http://trideeprath.0fees.net/response.php");
		 HttpPost httppost = new HttpPost("http://planourmeet.herokuapp.com/register");
		 try {
		        // Add your data
		        httppost.setEntity(new UrlEncodedFormEntity(params[0]));
		        ResponseHandler<String> responseHandler=new BasicResponseHandler();
		        String responseBody = httpclient.execute(httppost, responseHandler);
		        Log.d("response", responseBody);
		        return responseBody;

		  } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    	return "Exception while sending";
		  } catch (IOException e) {
		        // TODO Auto-generated catch block
		    	return "Exception while sending";
		    	
		  }
		
		
	}
	
	 @Override
     protected void onPostExecute(String result) {
            
      }
	
}
