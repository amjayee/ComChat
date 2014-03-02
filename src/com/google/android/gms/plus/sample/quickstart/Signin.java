package com.google.android.gms.plus.sample.quickstart;


import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.TextView;

public class Signin extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		setInitialData();
	}


	private void setInitialData() {
		TextView signIn = (TextView) findViewById(R.id.signInStat);
		signIn.setText("Signed in as "+Home.usrBean.getV_NAME());
		ImageButton imgBttn = (ImageButton) findViewById(R.id.profPic);
		File f= new File(Environment.getExternalStorageDirectory() + GlobalParameter.picPath+"me.jpeg");
		Bitmap bMap = BitmapFactory.decodeFile(f.getAbsolutePath());
		imgBttn.setImageBitmap(bMap);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signin, menu);
		return true;
	}

}
