package org.cbccessence.mobihealth.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;


import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.application.BaseActivity;
import org.cbccessence.mobihealth.application.MobiHealth;
import org.cbccessence.mobihealth.database.MobiHealthDatabaseHandler;

import java.io.File;
import java.io.FileOutputStream;

public class MenuActivity extends BaseActivity implements View.OnClickListener {

	private GridView grid;
	
    Context context;
    static SharedPreferences prefs;
		int[] images={
			R.drawable.preg,
			R.drawable.baby,
			R.drawable.youth,
			R.drawable.visual,
			R.drawable.alert,
			R.drawable.settings
		};

		private SharedPreferences loginPref;
		private String name;
	    public static final String AUTHORITY = "org.cbccessence.provider";
	    	    public static final String ACCOUNT_TYPE = "example.com";
	    	    public static final String ACCOUNT = "MobiHealth";
	    	    public static final long MILLISECONDS_PER_SECOND = 1000L;
	    	    public static final long SECONDS_PER_MINUTE = 60L;
	    	    public static final long SYNC_INTERVAL_IN_MINUTES = 60L;
	    	    public static final long SYNC_INTERVAL =
	    	            SYNC_INTERVAL_IN_MINUTES *
	    	            SECONDS_PER_MINUTE *
	    	            MILLISECONDS_PER_SECOND;
	    	    Account mAccount;
	    	    ContentResolver mResolver;

				private MobiHealthDatabaseHandler db;

				private String username_value;

				private String username;
				
		
				
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.menu_activity);

		createNoMediaFile();


		prefs = PreferenceManager.getDefaultSharedPreferences(this);

        RelativeLayout pregnancy_messages = (RelativeLayout) findViewById(R.id.pregnancy_messages);
        RelativeLayout baby_first_year = (RelativeLayout) findViewById(R.id.baby_first_year);
        RelativeLayout youth_sex_health = (RelativeLayout) findViewById(R.id.youth_sex_health);
        RelativeLayout visual_aids = (RelativeLayout) findViewById(R.id.visual_aids);
        RelativeLayout referals = (RelativeLayout) findViewById(R.id.alert);
        RelativeLayout settings = (RelativeLayout) findViewById(R.id.settings);

        pregnancy_messages.setOnClickListener(this);
        baby_first_year.setOnClickListener(this);
        youth_sex_health.setOnClickListener(this);
        visual_aids.setOnClickListener(this);
        referals.setOnClickListener(this);
        settings.setOnClickListener(this);




	   
	} 
	

	    
	@Override
	public void onClick( View view) {
		Intent intent;
		switch (view.getId()){
		case R.id.pregnancy_messages:
			 
			intent=new Intent(MenuActivity.this, PregnancyMenuActivity.class);
		startActivity(intent);
			break;
		case R.id.baby_first_year:
			intent=new Intent(MenuActivity.this, ChildCareMenu.class);
			startActivity(intent);
				break;
		case R.id.youth_sex_health:
			intent=new Intent(MenuActivity.this, YouthHealthMenuActivity.class);
			startActivity(intent);
				break;
				
		case R.id.visual_aids:
			intent=new Intent(MenuActivity.this, VisualAidsActivity.class);
			startActivity(intent);
			break;
		case R.id.alert:
			intent=new Intent(MenuActivity.this, AlertMessagesActivity.class);
			startActivity(intent);
			break;
		case R.id.settings:
			intent = new Intent(MenuActivity.this, GroupMeetingsActivity.class);
			startActivity(intent);

             break;
		}
		
	}
	
	 public static Account CreateSyncAccount(Context context) {

	        Account newAccount = new Account(
	                ACCOUNT, ACCOUNT_TYPE);

	        AccountManager accountManager =
	                (AccountManager) context.getSystemService(
	                        ACCOUNT_SERVICE);
	      
	        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
	          
	        } else {
	           
	        }
			return newAccount;
	    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.getItem(0).setVisible(false);

		return true;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	    	finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}


	public static String getUserName(){
		String userName;
		userName = prefs.getString("username", "name");
		return userName;
	}

	public static String getLanguage(){

		return  prefs.getString("language", "English");
	}


	public void createNoMediaFile(){
		FileOutputStream out = null;

		try {
			File no_media = new File(MobiHealth.ROOT_DIR);
			if(!no_media.exists())
				no_media.mkdirs();

			File file = new File(no_media + File.separator , ".nomedia");
			if(!file.exists()) {
				out = new FileOutputStream(file);
				out.write(0);
				out.close();


				Log.i("Doc Adapter", "No media created!  " + file);
			}
			else { Log.i("Doc Adapter", "No media already exists!!!!!!  " + file);}

		}catch (Exception e){
			e.printStackTrace();

		}


	}



}
