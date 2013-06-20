package com.weilin.fall;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;




public class FallPrevention extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView imgView;
		imgView = (ImageView) findViewById(R.id.imageView1);
		
		//Get Preferenece  
      	SharedPreferences myPrefs;    
      	myPrefs = getSharedPreferences("myPrefs", MODE_WORLD_READABLE);  
      	long StoredValue=myPrefs.getLong("STOREDVALUE", 0); 
    	  

			
		if(StoredValue==0){
			imgView.setImageResource(R.drawable.question);
		}
		
		
	   if (StoredValue <8500&& StoredValue>0){
             imgView.setImageResource(R.drawable. smiley );
      }
     
      if (StoredValue >8500){
             imgView.setImageResource(R.drawable. sad1 );
      }

	
		
	}

	  

	public void btn_1(View view){
	    	startActivity(new Intent("com.weilin.fall.NageAge"));
	    	finish();
	     }
	   
	   public void btn_2(View view){
	    	startActivity(new Intent("com.weilin.fallsurvey.LoginActivity"));
	     }
	   
	   public void btn_3(View view){
		   startActivity(new Intent("com.weilin.fall.History"));
	     }
	   
	   public void btn_4(View view){
		   startActivity(new Intent("com.weilin.fall.Lightsensor"));
	     }
	   
	   
	   
	   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
