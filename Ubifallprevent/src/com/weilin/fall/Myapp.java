package com.weilin.fall;

import android.app.Application;

public class Myapp extends Application {
	private long mystate;
	public long getState(){
	  return mystate;
	}
	public void setState(long s){
		mystate=s;
	}
	    

}
