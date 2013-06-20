package com.weilin.fall;


import java.text.SimpleDateFormat;

import com.weilin.fall.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class ChronometerExampleActivity extends Activity {
    /** Called when the activity is first created. */
   
	public static long olderstatus=0;
	@Override
    
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer_example);
       
        
        
    }
    
    
    
    
    public void btn_back(View view){
    	
    	
    	
     }
  //---create an anonymous class to act as a button click listener---
 
    
    public void btn_start(View view){
    	Chronometer mChronometer = (Chronometer) findViewById(R.id.chronometer);
 		mChronometer.start();
     }
     	      
     public void btn_stop(View view){
    	 Chronometer mChronometer = (Chronometer) findViewById(R.id.chronometer);
    	 mChronometer.stop();
    	 long elapsedMillis =  mChronometer.getTimeElapsed();
    	 SimpleDateFormat sDateFormat =new    SimpleDateFormat("yyyy-MM-dd  HH:mm");       
    
    	 String  currentdate = sDateFormat.format(new   java.util.Date()); 
    	
    	  
    	SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
      	SharedPreferences.Editor prefsEditor;  
      	prefsEditor = myPrefs.edit();  
      	//strVersionName->Any value to be stored  
      	prefsEditor.putLong("STOREDVALUE", elapsedMillis);  
      	prefsEditor.commit();

     	// databace operation 
        String Username= myPrefs.getString("Name", null); 
        long StoredValue=myPrefs.getLong("STOREDVALUE", 0)/1000; 
    	  
          DBAdapter db = new DBAdapter(this);
		
		//---add a contact---db.open();
        db.open();
        long id = db.insertContact(Username, StoredValue,currentdate);
    	  
        db.close();
        
    	 
        if(elapsedMillis>20000){
        	
        	//获取LayoutInflater对象，该对象能把XML文件转换为与之一直的View对象  
            LayoutInflater inflater = getLayoutInflater();  
            //根据指定的布局文件创建一个具有层级关系的View对象  
            //第二个参数为View对象的根节点，即LinearLayout的ID  
            View layout = inflater.inflate(R.layout.toast_layout1, (ViewGroup) findViewById(R.id.RelativeLayout1));  

            TextView text = (TextView) layout.findViewById(R.id.text);  
            text.setText(NameAge.name+": "+"very High Risk for Falling, Please refer to Health Professional");  

            Toast toast = new Toast(getApplicationContext());  
            //设置Toast的位置  
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  
            toast.setDuration(Toast.LENGTH_LONG);  
            //让Toast显示为我们自定义的样子  
            toast.setView(layout);  
            toast.show();  
        	
        	
        	
        } else if(elapsedMillis<20000 && elapsedMillis>14500)
        {
        	
        	//获取LayoutInflater对象，该对象能把XML文件转换为与之一直的View对象  
            LayoutInflater inflater = getLayoutInflater();  
            //根据指定的布局文件创建一个具有层级关系的View对象  
            //第二个参数为View对象的根节点，即LinearLayout的ID  
            View layout = inflater.inflate(R.layout.toast_layout1, (ViewGroup) findViewById(R.id.RelativeLayout1));  

            TextView text = (TextView) layout.findViewById(R.id.text);  
            text.setText(NameAge.name+": "+"You are at High Risk for Falling ");  

            Toast toast = new Toast(getApplicationContext());  
            //设置Toast的位置  
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  
            toast.setDuration(Toast.LENGTH_LONG);  
            //让Toast显示为我们自定义的样子  
            toast.setView(layout);  
            toast.show();  
        	
        }
        else {
        	 
			//获取LayoutInflater对象，该对象能把XML文件转换为与之一直的View对象  
             LayoutInflater inflater = getLayoutInflater();  
             //根据指定的布局文件创建一个具有层级关系的View对象  
             //第二个参数为View对象的根节点，即LinearLayout的ID  
             View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.RelativeLayout1));  
 
             TextView text = (TextView) layout.findViewById(R.id.text);  
             text.setText(NameAge.name+": "+"You are at Low Risk for Falling");  

             Toast toast = new Toast(getApplicationContext());  
             //设置Toast的位置  
             toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  
             toast.setDuration(Toast.LENGTH_LONG);  
             //让Toast显示为我们自定义的样子  
             toast.setView(layout);  
             toast.show();  
			 
 		
 			 
        }
        
        
        
        
        
 		 
 			 
     }
 		

 }

    
    