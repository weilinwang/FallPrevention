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
        //ʵ����WebView���� 
        webview = new WebView(this); 
        //����WebView���ԣ��ܹ�ִ��Javascript�ű� 
        webview.getSettings().setJavaScriptEnabled(true); 
        //������Ҫ��ʾ����ҳ 
        webview.loadUrl("https://m.facebook.com"); 
        //����Web��ͼ 
        setContentView(webview); 
        
        
        webview.setWebViewClient(new WebViewClient(){
        	  @Override
        	    public boolean shouldOverrideUrlLoading(WebView view, String url) {  //��д�˷������������ҳ��������ӻ����ڵ�ǰ��webview����ת��������������Ǳ�
        	       view.loadUrl(url);
        	       return true;
        	  }
        });
        WebSettings webSettings = webview.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.LARGER);
        
        
    } 
     
    @Override
    //���û��� 
    //����Activity���onKeyDown(int keyCoder,KeyEvent event)���� 
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) { 
            webview.goBack(); //goBack()��ʾ����WebView����һҳ�� 
            return true; 
        } 
        else 
        	return false; 

        }
    
    
    
    public boolean shouldOverrideUrlLoading(WebView view, String url) {  //��д�˷������������ҳ��������ӻ����ڵ�ǰ��webview����ת��������������Ǳ�
        view.loadUrl(url);
        return true;
   }
    
    
    
    
}
