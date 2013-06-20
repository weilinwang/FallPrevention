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
        	
        	//��ȡLayoutInflater���󣬸ö����ܰ�XML�ļ�ת��Ϊ��֮һֱ��View����  
            LayoutInflater inflater = getLayoutInflater();  
            //����ָ���Ĳ����ļ�����һ�����в㼶��ϵ��View����  
            //�ڶ�������ΪView����ĸ��ڵ㣬��LinearLayout��ID  
            View layout = inflater.inflate(R.layout.toast_layout1, (ViewGroup) findViewById(R.id.RelativeLayout1));  

            TextView text = (TextView) layout.findViewById(R.id.text);  
            text.setText(NameAge.name+": "+"very High Risk for Falling, Please refer to Health Professional");  

            Toast toast = new Toast(getApplicationContext());  
            //����Toast��λ��  
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  
            toast.setDuration(Toast.LENGTH_LONG);  
            //��Toast��ʾΪ�����Զ��������  
            toast.setView(layout);  
            toast.show();  
        	
        	
        	
        } else if(elapsedMillis<20000 && elapsedMillis>14500)
        {
        	
        	//��ȡLayoutInflater���󣬸ö����ܰ�XML�ļ�ת��Ϊ��֮һֱ��View����  
            LayoutInflater inflater = getLayoutInflater();  
            //����ָ���Ĳ����ļ�����һ�����в㼶��ϵ��View����  
            //�ڶ�������ΪView����ĸ��ڵ㣬��LinearLayout��ID  
            View layout = inflater.inflate(R.layout.toast_layout1, (ViewGroup) findViewById(R.id.RelativeLayout1));  

            TextView text = (TextView) layout.findViewById(R.id.text);  
            text.setText(NameAge.name+": "+"You are at High Risk for Falling ");  

            Toast toast = new Toast(getApplicationContext());  
            //����Toast��λ��  
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  
            toast.setDuration(Toast.LENGTH_LONG);  
            //��Toast��ʾΪ�����Զ��������  
            toast.setView(layout);  
            toast.show();  
        	
        }
        else {
        	 
			//��ȡLayoutInflater���󣬸ö����ܰ�XML�ļ�ת��Ϊ��֮һֱ��View����  
             LayoutInflater inflater = getLayoutInflater();  
             //����ָ���Ĳ����ļ�����һ�����в㼶��ϵ��View����  
             //�ڶ�������ΪView����ĸ��ڵ㣬��LinearLayout��ID  
             View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.RelativeLayout1));  
 
             TextView text = (TextView) layout.findViewById(R.id.text);  
             text.setText(NameAge.name+": "+"You are at Low Risk for Falling");  

             Toast toast = new Toast(getApplicationContext());  
             //����Toast��λ��  
             toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  
             toast.setDuration(Toast.LENGTH_LONG);  
             //��Toast��ʾΪ�����Զ��������  
             toast.setView(layout);  
             toast.show();  
			 
 		
 			 
        }
        
        
        
        
        
 		 
 			 
     }
 		

 }

    
    