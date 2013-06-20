package com.weilin.smartpictureframe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.Menu;

public class Openother extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent i = new Intent();
		PackageManager manager = getPackageManager();
		i = manager.getLaunchIntentForPackage("com.weilin.fall");
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(i);
		
		
	}



}
