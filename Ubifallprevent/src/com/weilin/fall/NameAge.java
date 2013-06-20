package com.weilin.fall;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


public class NameAge extends Activity {
     public static String name; 
     EditText Text1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_age);
		
		
	}

	public void bt1(View view){
    	startActivity(new Intent("com.weilin.fall.IntrodutionTimeUpGo"));
    	
    			Text1= (EditText) findViewById(R.id.editText1);
    			name= Text1.getText().toString();
    			SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
    	      	SharedPreferences.Editor prefsEditor;  
    	      	prefsEditor = myPrefs.edit();  
    	      	//strVersionName->Any value to be stored  
    	      	prefsEditor.putString("Name", name);  
    	      	prefsEditor.commit();
    			
     }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.name_age, menu);
		return true;
	}

}
