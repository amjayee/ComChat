package com.google.android.gms.plus.sample.quickstart;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.about.About;
import com.settingFeedback.Feedbk;
import com.settingblkcontact.BlockContact;
import com.settinghelp.Help;
import com.settingprofile.SettingProfile;

public class Settings extends Activity {
	LinearLayout help;	
	ImageView helpImg;
	LinearLayout aboutLinear;
	ImageView aboutImg;
	LinearLayout profLinear;
	ImageView profImg;
	LinearLayout blkContLinear;
	ImageView blkContImg;	
	LinearLayout feedbkLinear;
	ImageView feedbkImg;	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        registerEvents();
    }
    private void registerEvents() {
		help = (LinearLayout) findViewById(R.id.helpLinear);
		helpImg = (ImageView) findViewById(R.id.FaqImg);
		help.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent meIntent = new Intent(getApplicationContext(), Help.class);
				startActivityForResult(meIntent, 0);
			}
		});
		helpImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent meIntent = new Intent(getApplicationContext(), Help.class);
				startActivityForResult(meIntent, 0);
			}
		});
		aboutLinear=(LinearLayout)findViewById(R.id.aboutLinear);
		aboutImg = (ImageView) findViewById(R.id.aboutImg);
		aboutLinear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent meIntent = new Intent(getApplicationContext(), About.class);
				startActivityForResult(meIntent, 0);
			}
		});
		aboutImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent meIntent = new Intent(getApplicationContext(), About.class);
				startActivityForResult(meIntent, 0);
			}
		});
		
		
		profLinear=(LinearLayout)findViewById(R.id.profLinear);
		profImg = (ImageView) findViewById(R.id.profImg);
		profLinear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent meIntent = new Intent(getApplicationContext(), SettingProfile.class);
				startActivityForResult(meIntent, 0);
			}
		});
		profImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent meIntent = new Intent(getApplicationContext(), SettingProfile.class);
				startActivityForResult(meIntent, 0);
			}
		});
		
		blkContLinear=(LinearLayout)findViewById(R.id.blkContLinear);
		blkContImg = (ImageView) findViewById(R.id.blkContImg);
		blkContLinear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent meIntent = new Intent(getApplicationContext(), BlockContact.class);
				startActivityForResult(meIntent, 0);
			}
		});
		blkContImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent meIntent = new Intent(getApplicationContext(), BlockContact.class);
				startActivityForResult(meIntent, 0);
			}
		});
		
		feedbkLinear=(LinearLayout)findViewById(R.id.feedbkLinear);
		feedbkImg = (ImageView) findViewById(R.id.feedbkImg);
		feedbkLinear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent meIntent = new Intent(getApplicationContext(), Feedbk.class);
				startActivityForResult(meIntent, 0);
			}
		});
		feedbkImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent meIntent = new Intent(getApplicationContext(), Feedbk.class);
				startActivityForResult(meIntent, 0);
			}
		});
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
}
