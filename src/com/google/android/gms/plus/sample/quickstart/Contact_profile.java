package com.google.android.gms.plus.sample.quickstart;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class Contact_profile extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_profile);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact_profile, menu);
        return true;
    }
    
}