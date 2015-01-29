package com.example.httpdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isConnectedOnline()) {
					Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_LONG).show();
					new GetData().execute("http://rss.cnn.com/rss/cnn_tech.rss");
				} else {
					Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	
	// //http://rss.cnn.com/rss/cnn_tech.rss
	
	private class GetData extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			URL url;
			HttpURLConnection con;
			BufferedReader reader = null;
			try {
				 url = new URL(params[0]);
				 con = (HttpURLConnection) url.openConnection();
				 con.setRequestMethod("GET");
				 reader = new BufferedReader(new InputStreamReader(con.getInputStream())); // Will open the connection and return a input stream
				 StringBuilder sb = new StringBuilder(); // More efficient than java's string concatenation
				 String line = "";
				 while((line = reader.readLine()) != null) {
					 sb.append(line + "\n");
				 }
				 
				 return sb.toString();
				 
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(reader != null)
						reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(result != null){
				Log.d("demo", result);
			} else {
				Log.d("demo","Null data");
			}
		}
	}
	
	private class GetImage extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			URL url;
			HttpURLConnection con;
			BufferedReader reader = null;
			try {
				 url = new URL(params[0]);
				 con = (HttpURLConnection) url.openConnection();
				 con.setRequestMethod("GET");
				 
				 Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
				 
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(reader != null)
						reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			if(result != null){
				
			} else {
				
			}
		}
	}
	
	private boolean isConnectedOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		
		if(networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
}
