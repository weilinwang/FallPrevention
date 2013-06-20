package com.weilin.smartpictureframe;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Social extends Activity {


	private WebView webview; 
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        //实例化WebView对象 
        webview = new WebView(this); 
        //设置WebView属性，能够执行Javascript脚本 
        webview.getSettings().setJavaScriptEnabled(true); 
        //加载需要显示的网页 
        webview.loadUrl("https://m.facebook.com"); 
        //设置Web视图 
        setContentView(webview); 
        
        
        webview.setWebViewClient(new WebViewClient(){
        	  @Override
        	    public boolean shouldOverrideUrlLoading(WebView view, String url) {  //重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
        	       view.loadUrl(url);
        	       return true;
        	  }
        });
        WebSettings webSettings = webview.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.LARGER);
        
        
    } 
     
    @Override
    //设置回退 
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法 
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) { 
            webview.goBack(); //goBack()表示返回WebView的上一页面 
            return true; 
        } 
        else 
        	return false; 

        }
    
    
    
    public boolean shouldOverrideUrlLoading(WebView view, String url) {  //重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
        view.loadUrl(url);
        return true;
   }
    
    
    
    
}
