package com.weilin.smartpictureframe;



import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.app.Activity;
import android.view.Menu;


import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;




public class MainActivity extends TabActivity implements OnClickListener {

	String pic2;
	int lightvalue=100;
	private WakeLock wakeLock;  
	
	@Override
	public void onClick(View view) {
		//
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHost tabHost = getTabHost();
		LayoutInflater.from(this).inflate(R.layout.main,
				tabHost.getTabContentView(), true);
		
/*
		tabHost.addTab(tabHost
				.newTabSpec("tab2")
				.setIndicator("ПаІб",
						getResources().getDrawable(R.drawable.icon1))
				.setContent(new Intent(this, GalleryActivity.class)));*/
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Display")
				.setContent(new Intent(this, Smartdisplay.class)));
				
				tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Falls")
						.setContent(new Intent(this, Fallweb.class)));
				
				
				
				tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Social")
						.setContent(new Intent(this, Social.class)));
				
			
				
				tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("Check",getResources().getDrawable(R.drawable.question))
						.setContent(new Intent(this, Openother.class)));
				
				
				
			    SensorManager sensorManager 
		        = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		        Sensor lightSensor 
		        = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		        
		        sensorManager.registerListener(lightSensorEventListener, 
		                lightSensor, 
		                SensorManager.SENSOR_DELAY_NORMAL);
		
	
	}
	
	
	
	
	   SensorEventListener lightSensorEventListener
	    = new SensorEventListener(){
	 
	  @Override
	  public void onAccuracyChanged(Sensor sensor, int accuracy) {
	   // TODO Auto-generated method stub
	    
	  }
	 
	  @Override
	  public void onSensorChanged(SensorEvent event) {
	   // TODO Auto-generated method stub
	   if(event.sensor.getType()==Sensor.TYPE_LIGHT){
	    float currentReading = event.values[0];
	    PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
	    wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK  
                | PowerManager.ON_AFTER_RELEASE, "DPA");  
	     if(currentReading<3)
	     {
	    	 wakeLock.release();  
	        
	     }else{
	    	 wakeLock.acquire();
	     }
	   
	

	    
	   }
	  }
	      
	    };
	
	
	
	
	
	
	
	
}