
package com.weilin.fall;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
 
public class Lightsensor extends Activity {
  
 ProgressBar lightMeter;
 TextView textMax, textReading;
  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightsensor);
        lightMeter = (ProgressBar)findViewById(R.id.lightmeter);
        textMax = (TextView)findViewById(R.id.max);
        textReading = (TextView)findViewById(R.id.reading);
         
        SensorManager sensorManager 
        = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor 
        = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
      
        if (lightSensor == null){
         Toast.makeText(Lightsensor.this, 
           "No Light Sensor! quit-", 
           Toast.LENGTH_LONG).show();
        }else{
         float max =  lightSensor.getMaximumRange();
         lightMeter.setMax((int)max);
         textMax.setText("Max Reading: " + String.valueOf(max));
          
         sensorManager.registerListener(lightSensorEventListener, 
           lightSensor, 
           SensorManager.SENSOR_DELAY_NORMAL);
          
        }
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
    lightMeter.setProgress((int)currentReading);
    textReading.setText("Current Reading: " + String.valueOf(currentReading));
  

    
   }
  }
      
    };
}