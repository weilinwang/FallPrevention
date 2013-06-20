package com.weilin.fall;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class IntrodutionTimeUpGo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introdution_time_up_go);
	}

	
	 public void btn_5(View view){
	    	startActivity(new Intent("com.weilin.fall.ChronometerExampleActivity"));
	     }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.introdution_time_up_go, menu);
		return true;
	}

}
