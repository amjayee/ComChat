package com.google.android.gms.plus.sample.quickstart;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.userprofile.UserBean;
import com.userprofile.UsrProfile;

public class Home extends Activity {
	ImageView usrInfo;
	ImageView setting;
	public static UserBean usrBean=new UserBean();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		registerEvents();
	}
	private void registerEvents() {
		usrInfo=(ImageView)findViewById(R.id.me_home);
		usrInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(GlobalParameter.existFlag)
				{
					Intent meIntent = new Intent(getApplicationContext(), UsrProfile.class);
					startActivityForResult(meIntent, 0);
				}
				else
				{				
					startLogin();
				}
			}
		});
		setting=(ImageView)findViewById(R.id.home_Settings);
		setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(GlobalParameter.existFlag)
				{
					Intent meIntent = new Intent(getApplicationContext(), Settings.class);
					startActivityForResult(meIntent, 0);
				}
				else
				{				
					startLogin();
				}
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	public void startLogin(){
		Intent meIntent = new Intent(getApplicationContext(), MainActivity.class);
		startActivityForResult(meIntent, 0);
	}
}
