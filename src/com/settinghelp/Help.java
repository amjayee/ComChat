package com.settinghelp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.plus.sample.quickstart.R;

public class Help extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
		
	}

}
