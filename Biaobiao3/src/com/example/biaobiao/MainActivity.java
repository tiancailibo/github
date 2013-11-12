package com.example.biaobiao;

import org.json.JSONObject;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.IconsAd;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	GameView gameview;
	LinearLayout anims;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		 // this.setContentView(R.layout.activity_main);

		gameview=new GameView(this);
     	
     //	RelativeLayout rlMain=new RelativeLayout(this);
//     	setContentView(rlMain);		
//		IconsAd iconsAd=new IconsAd(this);
//		iconsAd.loadAd(this);
//		rlMain.addView(gameview);
     	
		Init();
		}
		
	

	public void Init() {
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		  this.setContentView(gameview);
	}
	public void onStart(){
		super.onStart();	
		this.setContentView(gameview);
}
	public void onPause(){
		super.onPause();
	}
	public void Resume(){
		super.onResume();
		
	}
	public void onStop(){
		super.onStop();
	}
	public void onRestart(){
		super.onRestart();
	}
    public void Destroy(){
    	super.onDestroy();
    }
	
    

	
}
