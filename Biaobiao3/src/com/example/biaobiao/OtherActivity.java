package com.example.biaobiao;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class OtherActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.grade);
		ImageResource mr = ImageResource.getImageResource();
		getWindow().setBackgroundDrawable(
				mr.getDrawable(getResources(),
						R.drawable.backgroundpic));
		
		TextView tv = (TextView)findViewById(R.id.gradeshow);
		ImageView iv = (ImageView)findViewById(R.id.gradeback);
		iv.setImageDrawable(mr.getDrawable(getResources(), R.drawable.comemenu));
		iv.setAdjustViewBounds(true);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		iv.setMaxWidth(width/4);
		
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		Intent intent = getIntent();
		String application = intent.getStringExtra("application");
		if(application.equals("help")){
			tv.setText(R.string.help);
		} else if(application.equals("about")){
			tv.setText(R.string.about);
		} else if(application.equals("grade")){
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(OtherActivity.this);
			int grade = sp.getInt("grade", 1);
			String str = "�鿴�ɼ�\n\n\n";
			str += "��߼�¼��"+grade+"��\n\n��óƺţ�";
			if(grade<500){
				str += "��������\n";
			} else if(grade<700){
				str += "����\n";
			} else if(grade<1000){
				str += "ʤ������\n";
			} else if(grade<2000){
				str += "ʤ������\n";
			} else {
				str += "���վ�տ\n";
			}
			tv.setText(str);
		}
	}
}
