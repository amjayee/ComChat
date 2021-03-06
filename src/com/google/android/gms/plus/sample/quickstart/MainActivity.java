/**
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.plus.sample.quickstart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.userprofile.UsrProfile;

/**
 * Android Google+ Quickstart activity.
 * 
 * Demonstrates Google+ Sign-In and usage of the Google+ APIs to retrieve a
 * users profile information.
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public class MainActivity extends FragmentActivity implements
ConnectionCallbacks, OnConnectionFailedListener,
ResultCallback<People.LoadPeopleResult>, View.OnClickListener {
	Bitmap bmp=null;
	private static final String TAG = "android-plus-quickstart";

	private static final int STATE_DEFAULT = 0;
	private static final int STATE_SIGN_IN = 1;
	private static final int STATE_IN_PROGRESS = 2;

	private static final int RC_SIGN_IN = 0;

	private static final int DIALOG_PLAY_SERVICES_ERROR = 0;

	private static final String SAVED_PROGRESS = "sign_in_progress";

	// GoogleApiClient wraps our service connection to Google Play services and
	// provides access to the users sign in state and Google's APIs.
	private GoogleApiClient mGoogleApiClient;

	// We use mSignInProgress to track whether user has clicked sign in.
	// mSignInProgress can be one of three values:
	//
	//       STATE_DEFAULT: The default state of the application before the user
	//                      has clicked 'sign in', or after they have clicked
	//                      'sign out'.  In this state we will not attempt to
	//                      resolve sign in errors and so will display our
	//                      Activity in a signed out state.
	//       STATE_SIGN_IN: This state indicates that the user has clicked 'sign
	//                      in', so resolve successive errors preventing sign in
	//                      until the user has successfully authorized an account
	//                      for our app.
	//   STATE_IN_PROGRESS: This state indicates that we have started an intent to
	//                      resolve an error, and so we should not start further
	//                      intents until the current intent completes.
	private int mSignInProgress;

	// Used to store the PendingIntent most recently returned by Google Play
	// services until the user clicks 'sign in'.
	private PendingIntent mSignInIntent;

	// Used to store the error code most recently returned by Google Play services
	// until the user clicks 'sign in'.
	private int mSignInError;

	private SignInButton mSignInButton;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
		mSignInButton.setOnClickListener(this);
		if (savedInstanceState != null) {
			mSignInProgress = savedInstanceState
					.getInt(SAVED_PROGRESS, STATE_DEFAULT);
		}
		mGoogleApiClient = buildGoogleApiClient();
	}

	private GoogleApiClient buildGoogleApiClient() {
		// When we build the GoogleApiClient we specify where connected and
		// connection failed callbacks should be returned, which Google APIs our
		// app uses and which OAuth 2.0 scopes our app requests.
		return new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(Plus.API, null)
		.addScope(Plus.SCOPE_PLUS_LOGIN)
		.build();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
		resolveSignInError();
	}

	@Override
	protected void onStop() {
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(SAVED_PROGRESS, mSignInProgress);
	}

	@Override
	public void onClick(View v) {
		if (!mGoogleApiClient.isConnecting()) {
			// We only process button clicks when GoogleApiClient is not transitioning
			// between connected and not connected.

			resolveSignInError();
			/*else if (v.getId()==R.id.sign_out_button) {    
				// We clear the default account on sign out so that Google Play
				// services will not return an onConnected callback without user
				// interaction.
				Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
				mGoogleApiClient.disconnect();
				mGoogleApiClient.connect();
			}*/
		}
	}

	/* onConnected is called when our Activity successfully connects to Google
	 * Play services.  onConnected indicates that an account was selected on the
	 * device, that the selected account has granted any requested permissions to
	 * our app and that we were able to establish a service connection to Google
	 * Play services.
	 */
	@Override
	public void onConnected(Bundle connectionHint) {
		// Reaching onConnected means we consider the user signed in.
		Log.i(TAG, "onConnected");

		// Update the user interface to reflect that the user is signed in.
		mSignInButton.setEnabled(false);

		// Retrieve some profile information to personalize our app for the user.
		Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
		loadTheBean(currentUser);
		Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
		.setResultCallback(this);
		// Indicate that the sign in process is complete.
		mSignInProgress = STATE_DEFAULT;
	}

	private void loadTheBean(Person currentUser) {

		Home.usrBean.setV_MAIL(Plus.AccountApi.getAccountName(mGoogleApiClient));
		Home.usrBean.setV_NAME(currentUser.getDisplayName());
		Home.usrBean.setV_IMAGE_URL(currentUser.getImage().getUrl());	
		new executeTask().execute();
	}
	private class executeTask extends AsyncTask {
		@Override
		protected Object doInBackground(Object... arg0) {
			DownloadImageFromPath();
			handler.sendMessage(new Message());
			//sendMail();
			return null;
		}
		private Handler handler = new Handler(){
			@Override	
			public void handleMessage(Message msg) {
				/*	ImageButton iv =(ImageButton) findViewById(R.id.Usrpic);
				iv.setVisibility(View.VISIBLE);
				iv.setImageBitmap(bmp);*/

				loadUserDetailsAndMove();
			}
		};
	}
	private void loadUserDetailsAndMove() {
		createDirectoryAndSaveFile(bmp,"me.jpeg");
		loadSignIn();
	}
	private void loadSignIn() {
		super.onBackPressed();
		GlobalParameter.existFlag = true;
		Intent meIntent = new Intent(getApplicationContext(), UsrProfile.class);
		startActivityForResult(meIntent, 0);

	}

	private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {
		String dir = Environment.getExternalStorageDirectory() + GlobalParameter.picPath;
		File direct = new File(dir);
		if (!direct.exists()) {
			File wallpaperDirectory = new File(dir);
			wallpaperDirectory.mkdirs();
		}
		File file = new File(new File(dir), fileName);
		if (file.exists())
			file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void DownloadImageFromPath(){
		InputStream in =null;
		int responseCode = -1;
		try{
			String profileUrl = Home.usrBean.getV_IMAGE_URL().replace("sz=50", "sz=200");
			URL url = new URL(profileUrl);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setDoInput(true);
			con.connect();
			responseCode = con.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_OK)
			{
				in = con.getInputStream();			
				bmp = BitmapFactory.decodeStream(in);
				in.close();
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	static class MyAuthenticator extends Authenticator {  
		public PasswordAuthentication getPasswordAuthentication() {  
			String username = "BandWManager@gmail.com";  
			String password = "AmitCyril";  
			return new PasswordAuthentication(username, password);  
		}  
	}
	public void sendMail (){

		try{

			/*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy); */
			Properties props = System.getProperties();
			props.setProperty("mail.smtp.port", "25");  
			props.setProperty("mail.smtp.socketFactory.port", "587");  
			props.setProperty("mail.smtp.host", "smtp.gmail.com");  
			props.setProperty("mail.smtp.starttls.enable", "true");  
			props.setProperty("mail.smtp.auth", "true");  
			Authenticator auth = new MyAuthenticator();  
			Session session = Session.getDefaultInstance(props,auth);
			session.setDebug(true);

			MimeMessage message = new MimeMessage(session);  
			message.setFrom(new InternetAddress("BandWManager@gmail.com"));  
			message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(Home.usrBean.getV_MAIL())); 
			final String encoding = "UTF-8";  

			message.setSubject(GlobalParameter.getMail_subject(), encoding);  
			message.setContent(GlobalParameter.getMail_salute().replace("[USERID]", capitalize(Home.usrBean.getV_NAME()))+""+GlobalParameter.getMail_body(), "text/html");  
			Transport.send(message); 
			return;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	private String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	} 
	/* onConnectionFailed is called when our Activity could not connect to Google
	 * Play services.  onConnectionFailed indicates that the user needs to select
	 * an account, grant permissions or resolve an error in order to sign in.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// Refer to the javadoc for ConnectionResult to see what error codes might
		// be returned in onConnectionFailed.
		Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
				+ result.getErrorCode());

		if (mSignInProgress != STATE_IN_PROGRESS) {
			// We do not have an intent in progress so we should store the latest
			// error resolution intent for use when the sign in button is clicked.
			mSignInIntent = result.getResolution();
			mSignInError = result.getErrorCode();

			if (mSignInProgress == STATE_SIGN_IN) {
				// STATE_SIGN_IN indicates the user already clicked the sign in button
				// so we should continue processing errors until the user is signed in
				// or they click cancel.
				resolveSignInError();
			}
		}

		// In this sample we consider the user signed out whenever they do not have
		// a connection to Google Play services.
		onSignedOut();
	}

	/* Starts an appropriate intent or dialog for user interaction to resolve
	 * the current error preventing the user from being signed in.  This could
	 * be a dialog allowing the user to select an account, an activity allowing
	 * the user to consent to the permissions being requested by your app, a
	 * setting to enable device networking, etc.
	 */
	private void resolveSignInError() {
		if (mSignInIntent != null) {
			// We have an intent which will allow our user to sign in or
			// resolve an error.  For example if the user needs to
			// select an account to sign in with, or if they need to consent
			// to the permissions your app is requesting.

			try {
				// Send the pending intent that we stored on the most recent
				// OnConnectionFailed callback.  This will allow the user to
				// resolve the error currently preventing our connection to
				// Google Play services.  
				mSignInProgress = STATE_IN_PROGRESS;
				startIntentSenderForResult(mSignInIntent.getIntentSender(),
						RC_SIGN_IN, null, 0, 0, 0);
			} catch (SendIntentException e) {
				Log.i(TAG, "Sign in intent could not be sent: "
						+ e.getLocalizedMessage());
				// The intent was canceled before it was sent.  Attempt to connect to
				// get an updated ConnectionResult.
				mSignInProgress = STATE_SIGN_IN;
				mGoogleApiClient.connect();
			}
		} else {
			// Google Play services wasn't able to provide an intent for some
			// error types, so we show the default Google Play services error
			// dialog which may still start an intent on our behalf if the
			// user can resolve the issue.
			showDialog(DIALOG_PLAY_SERVICES_ERROR);
		}  
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		switch (requestCode) {
		case RC_SIGN_IN:
			if (resultCode == RESULT_OK) {
				// If the error resolution was successful we should continue
				// processing errors.
				mSignInProgress = STATE_SIGN_IN;
			} else {
				// If the error resolution was not successful or the user canceled,
				// we should stop processing errors.
				mSignInProgress = STATE_DEFAULT;
			}

			if (!mGoogleApiClient.isConnecting()) {
				// If Google Play services resolved the issue with a dialog then
				// onStart is not called so we need to re-attempt connection here.
				mGoogleApiClient.connect();
			}
			break;
		}
	}
	private void onSignedOut() {
		// Update the UI to reflect that the user is signed out.
		mSignInButton.setEnabled(true);
		GlobalParameter.existFlag = false;
	}

	@Override
	public void onConnectionSuspended(int cause) {
		// The connection to Google Play services was lost for some reason.
		// We call connect() to attempt to re-establish the connection or get a
		// ConnectionResult that we can attempt to resolve.
		mGoogleApiClient.connect();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case DIALOG_PLAY_SERVICES_ERROR:
			if (GooglePlayServicesUtil.isUserRecoverableError(mSignInError)) {
				return GooglePlayServicesUtil.getErrorDialog(
						mSignInError,
						this,
						RC_SIGN_IN, 
						new DialogInterface.OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								Log.e(TAG, "Google Play services resolution cancelled");
								mSignInProgress = STATE_DEFAULT;
							}
						});
			} else {
				return new AlertDialog.Builder(this)
				.setMessage(R.string.play_services_error)
				.setPositiveButton(R.string.close,
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.e(TAG, "Google Play services error could not be "
								+ "resolved: " + mSignInError);
						mSignInProgress = STATE_DEFAULT;
					}
				}).create();
			}
		default:
			return super.onCreateDialog(id);
		}
	}

	@Override
	public void onResult(LoadPeopleResult arg0) {}
}
