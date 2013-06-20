package com.weilin.smartpictureframe;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ViewSwitcher.ViewFactory;
import com.weilin.smartpictureframe.R;

public class Smartdisplay extends Activity implements ViewFactory,OnClickListener {
	private static String TAG = "Gallery_Auto";
	private static int MSG_UPDATE = 1;

	// total of pictures 
	private int count_drawble = 0;   
	//current position
	private int cur_index = 0;     
	private boolean isalive = true;
	private ImageSwitcher imgSwitcher;
	private Gallery mgallery;
	//  set Adapter for Gallery
	private ImageAdapter imgAdapter = null;
	private static int TimingForPicture=8000;  
	
	Button playBtn;
    Button pauseBtn;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		   playBtn = (Button) findViewById(R.id.Play);
		   pauseBtn = (Button) findViewById(R.id.Pause); 
	        playBtn.setOnClickListener(this);
	        pauseBtn.setOnClickListener(this);
		
		
		imgSwitcher = (ImageSwitcher) findViewById(R.id.myimgSwitcher);
		imgSwitcher.setFactory(this);
		
		mgallery = (Gallery) findViewById(R.id.mygallery);
		mgallery.setSpacing(3);
		
		//set a ClickListener
		mgallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				System.out.println("setOnItemClickListener + " + position);
				cur_index = position ;
//				imgSwitcher.setBackgroundResource(imgAdapter.getResId(position));
			}
		});
		
		mgallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				 System.out.println("setOnItemSelectedListener");
				 
				 imgSwitcher.setBackgroundResource(imgAdapter.getResId(position));
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		
		
		
		//creat adapter 
		imgAdapter = new ImageAdapter(this);
		mgallery.setAdapter(imgAdapter);
		count_drawble = imgAdapter.getCount();

		//  using the threat to updata and id of current picture and call handler to refer to picture
	
		
		
		new  Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (isalive) {
					cur_index = cur_index % count_drawble;  // [0,count_drawable)
					Log.i(TAG, "cur_index"+ cur_index +" count_drawble --"+ count_drawble);
					//msg.arg1 = cur_index 
					Message msg = mhandler.obtainMessage(MSG_UPDATE, cur_index,	0);
					mhandler.sendMessage(msg);
					//the time of updating  picture
					try {
						Thread.sleep(TimingForPicture);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//set Thread.sleep(10000) ； prevent mhandler from insync， result in cur_index>=count_drawble
					cur_index++;  
				}
			}
		}).start();
	
	}
		
	
	public void onClick(View v) {
        switch (v.getId()) {
        case R.id.Play:
            // play music here
            playBtn.setVisibility(Button.GONE);
            pauseBtn.setVisibility(Button.VISIBLE);
            isalive = true;
    		new  Thread(new Runnable() {
    			@Override
    			public void run() {
    				// TODO Auto-generated method stub
    				while (isalive) {
    					cur_index = cur_index % count_drawble;  // [0,count_drawable)
    					Log.i(TAG, "cur_index"+ cur_index +" count_drawble --"+ count_drawble);
    					//msg.arg1 = cur_index 
    					Message msg = mhandler.obtainMessage(MSG_UPDATE, cur_index,	0);
    					mhandler.sendMessage(msg);
    					//the time of updating  picture
    					try {
    						Thread.sleep(TimingForPicture);
    					} catch (InterruptedException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    					//set Thread.sleep(10000) ； prevent mhandler from insync， result in cur_index>=count_drawble
    					cur_index++;  
    				}
    			}
    		}).start();
    	
            break;
        case R.id.Pause:
            // pause music here
            pauseBtn.setVisibility(Button.GONE);
            playBtn.setVisibility(Button.VISIBLE);
            isalive = false;
            break;
        }
    }
	
	
	
//		if (isStop){
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) { 
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		}
//		if (isRestart){
//			try {
//				Thread.sleep(1000000000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	
	
	
	
	
	
	
	

	
	
	
	
	//Using handler to updata mgallery.setSelection(positon),，then OnItemSelectedListener to change
	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == MSG_UPDATE) {
				Log.i(TAG, "cur_index"+ cur_index);
				mgallery.setSelection(msg.arg1);
		
			}
		}
	};

	public void onDestroy() {
		super.onDestroy();
		isalive = false;
	}

	@Override
	//ImageSwitcher ViewFactory
	public View makeView() {   
		// TODO Auto-generated method stub
		ImageView img = new ImageView(this);
		return img;
	}

	//  provide Gallery with adapater
	class ImageAdapter extends BaseAdapter {
		private Context mcontext;
		//using reflection to save IDs of pictures
		private ArrayList<Integer> residList = new ArrayList<Integer>(); 

		public ImageAdapter(Context context) {
			mcontext = context;
			
			// R.id在R文件中本质上是一个类，我们通过这个R.id.class.getClass().getDeclaredFields()可以找到它的所有属性
			Field[] residFields = R.drawable.class.getDeclaredFields();
			for (Field residField : residFields) {
				// For instance： public static final int icon=0x7f020000;
				// Field : name= icon ; field.getInt() = 0x7f020000
				if (!"icon".equals(residField.getName())) {
					int resid;
					try {
						resid = residField.getInt(null);
						residList.add(resid);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		@Override
		public int getCount() {
			Log.e(TAG, " " + residList.size());
			return residList.size();
		}

		@Override
		public Object getItem(int position) {
			return residList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
        //得到该图片的res id
		public int getResId(int position) {
			return residList.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView img;
			if (convertView == null) {
				img = new ImageView(mcontext);
				img.setScaleType(ImageView.ScaleType.FIT_XY);
				img.setLayoutParams(new Gallery.LayoutParams(80, 80)); // 图片显示宽和长
				img.setImageResource(residList.get(position));
			} else {
				img = (ImageView) convertView;
			}
			return img;
		}

	}
	
	
	

}