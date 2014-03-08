package com.userprofile;


import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.sample.quickstart.GlobalParameter;
import com.google.android.gms.plus.sample.quickstart.Home;
import com.google.android.gms.plus.sample.quickstart.R;
import com.google.android.gms.plus.sample.quickstart.Settings;

public class UsrProfile extends Activity {
	ImageView mePic ;
	TextView usrName;
	TextView usrProfilePhone;
	TextView usrProfileStat;
	TextView usrEmail;
	ImageView editStat;
	Dialog dialog;
	String newname;
	EditText editText;
	Button button1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usr_profile);
		loadUserProperties();
		registerEvents();
	}
	private void registerEvents() {
		editStat= (ImageView) findViewById(R.id.editStat)	;
		editStat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadStatusPopUp();
			}
		});
	}
	private void loadStatusPopUp() {
		Intent meIntent = new Intent(getApplicationContext(), UsrStatus.class);
		startActivityForResult(meIntent, 0);
		/*		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_edittext);
		dialog.setTitle("");
		dialog.setCancelable(true);
		editText = (EditText) dialog.findViewById(R.id.editText1);
		button1 = (Button) dialog.findViewById(R.id.saveButton);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String status=editText.getText().toString();
				Home.usrBean.setV_STATUS(status);
				usrProfileStat.setText(Home.usrBean.getV_STATUS());
				dialog.dismiss();
			}
		});
		dialog.show();*/
	}
	private void loadUserProperties() {
		mePic = (ImageView)findViewById(R.id.mePic);
		File f= new File(Environment.getExternalStorageDirectory() + GlobalParameter.picPath+"me.jpeg");
		Bitmap bMap = BitmapFactory.decodeFile(f.getAbsolutePath());
		mePic.setImageBitmap(bMap);
		usrName = (TextView) findViewById(R.id.usrName);
		usrName.setText(Home.usrBean.getV_NAME());
		usrProfilePhone= (TextView)findViewById(R.id.usrProfilePhone);
		promptForPhoneNumber();

		usrProfileStat= (TextView)findViewById(R.id.usrProfileStat);
		usrProfileStat.setText(Home.usrBean.getV_STATUS()==null?"No Status":Home.usrBean.getV_STATUS());

		usrEmail = (TextView) findViewById(R.id.usrEmail);
		usrEmail.setText(Home.usrBean.getV_MAIL());		
	}
	private void promptForPhoneNumber() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Please enter your Phone Number");

		// Set up the input
		final EditText input = new EditText(this);
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		builder.setView(input);
		builder.setCancelable(false);
		// Set up the buttons
		builder.setPositiveButton("Register", new DialogInterface.OnClickListener() { 
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Long phoneNumber =  validatePhone(input.getText().toString());
				System.out.println("phoneNumber"+phoneNumber);
				if(phoneNumber==0)
				{
					int duration = Toast.LENGTH_SHORT;
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "Please check the Phone Number", duration);
					toast.show();
					promptForPhoneNumber();
				}
				else
				{
					Home.usrBean.setN_PHONE_NUMBER(phoneNumber);
					usrProfilePhone.setText(Long.toString(phoneNumber));
				}
			}
		});
		builder.show();

	}
	private Long validatePhone(String string) {
		if(string==null || string.equalsIgnoreCase("")){
			return (long) 0;
		}
		else
		{
			if(string.length()==10)
			{
				return Long.parseLong(string);
			}
			else
			{
				return (long) 0;
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.usr_profile, menu);
		return true;
	}

}
