package com.weilin.fall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;

public class History extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		DBAdapter db = new DBAdapter(this);

//	    db.open();
//        Cursor c = db.getAllContacts();
//        if (c.moveToFirst())
//        {
//            do {
//                DisplayContact(c);
//            } while (c.moveToNext());
//        }
//        db.close();
        
        
  //SimpleCursorAdapter 
        db.open();
        Cursor c1 = db.getAllContacts();
       
        
        
        // The desired columns to be bound
        String[] columns = new String[] {
        		
        		db.KEY_NAME,
        		db.KEY_TIME,
                db.KEY_DATA
        };
       
        // the XML defined views which the data will be bound to
        int[] to = new int[] { 
         
          R.id.name,
          R.id.time,
          R.id.data,
        };
        
     // create the adapter using the cursor pointing to the desired data 
        //as well as the layout information
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(
          this, R.layout.historychild, 
          c1, 
          columns, 
          to,
          0);
        
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
//        db.open();
//        Cursor c = db.getContact(2);
//        if (c.moveToFirst())        
//            DisplayContact(c);
//        else
//            Toast.makeText(this, "No contact found", Toast.LENGTH_LONG).show();
//        db.close(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}
	
	
	 public void DisplayContact(Cursor c)
	    {
	        Toast.makeText(this,
	                "id: " + c.getString(0) + "\n" +
	                "Name: " + c.getString(1) + "\n" +
	                "Email:  " + c.getString(2) + "\n" +
	                "TImeD: " + c.getString(3),
	                Toast.LENGTH_LONG).show();
	    }

	 

}
