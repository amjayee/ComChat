package com.settingFeedback;

import com.google.android.gms.plus.sample.quickstart.R;
import com.google.android.gms.plus.sample.quickstart.R.layout;
import com.google.android.gms.plus.sample.quickstart.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Feedbk extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedbk);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feedbk, menu);
		return true;
	}

}
