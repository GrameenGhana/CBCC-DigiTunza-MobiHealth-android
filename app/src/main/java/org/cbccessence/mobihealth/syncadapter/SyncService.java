package org.cbccessence.mobihealth.syncadapter;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.cbccessence.mobihealth.database.MobiHealthDatabaseHandler;
import org.cbccessence.mobihealth.model.UsageTracking;
import org.cbccessence.mobihealth.model.User;

import android.accounts.Account;
import android.accounts.OperationCanceledException;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class SyncService extends Service {

	private static final String TAG = "CustomSyncService";
	public static MobiHealthDatabaseHandler db;
	private static int UsageSyncCount;
	private static int LoginsyncCount;
	private String value;
	 private static Context mContext;
	private static int id;
	private static SyncAdapter sSyncAdapter = null;
	 private static ContentResolver mContentResolver = null;
	 
	 public SyncService() {
	  super();
	 }
	 public class SyncAdapter extends AbstractThreadedSyncAdapter {
		  public SyncAdapter(Context context) {
		   super(context, true);
		   mContext = context;
		   
		  }
	 @Override
	  public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
	   try {
		   SyncService.performSync(mContext, account, extras, authority, provider, syncResult);
	   } catch (OperationCanceledException e) {
	   }
	  }
	 
	 }
	 private SyncAdapter getSyncAdapter() {
		  if (sSyncAdapter == null)
		   sSyncAdapter = new SyncAdapter(this);
		  return sSyncAdapter;
		 }
	@Override
	public IBinder onBind(Intent intent) {
		 IBinder ret = null;
		  ret = getSyncAdapter().getSyncAdapterBinder();
		  return ret;
	}
	
	private static void performSync(Context context, Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult)
			   throws OperationCanceledException {
			  mContentResolver = context.getContentResolver();
			  Log.i(TAG, "performSync: " + account.toString());
			  db=new MobiHealthDatabaseHandler(mContext);
			 
			  		LoginsyncCount=db.getAllLoginActivity();
				    UsageSyncCount=db.getRowUsageActivityCount();
			 	Log.i("SyncAdapter","OnPerfrmSync");
			 	 SyncResult result = new SyncResult();
				if(UsageSyncCount!=0){
					ArrayList<UsageTracking> usageTracking=db.getUsageActivity();
					
		 	    	System.out.println(usageTracking.size());
		 	    for(int i=0;i<usageTracking.size();i++){
		 	    	 //task=new RegistrationTask();
		 	    	
		 	    	String value="?username="+URLEncoder.encode(usageTracking.get(i).getUsername())+
		 	    				 "&module="+URLEncoder.encode(usageTracking.get(i).getModule())+
		 	    				 "&sub_module="+URLEncoder.encode(usageTracking.get(i).getSubModule())+
		 	    				 "&duration="+URLEncoder.encode(usageTracking.get(i).getDuration())+
		 	    				 "&duration_played="+URLEncoder.encode(usageTracking.get(i).getDurationPlayed())+
		 	    				 "&action_date="+URLEncoder.encode(usageTracking.get(i).getActionDate())+
		 	    				 "&type="+URLEncoder.encode(usageTracking.get(i).getType())+
		 	    				 "&status="+URLEncoder.encode(usageTracking.get(i).getStatus())+
		 	    				 "&extras="+URLEncoder.encode(usageTracking.get(i).getExtras());
		 	    				
		 	    	 //task.execute(context.getResources().getString(R.string.prefServerDefault)+"tracking"+value);
		 	    }
					
				}
				ArrayList<User> logindata=db.getLoginActivity();
				
				if(LoginsyncCount!=0){
					
					 for(int i=0;i<logindata.size();i++){
						// task2=new LoginSyncTask();
					String value="?username="+URLEncoder.encode(logindata.get(i).getUsername())+
							     "&date_logged_in="+URLEncoder.encode(logindata.get(i).getLoginDate())+
							     "&time_logged_in="+URLEncoder.encode(logindata.get(i).getLoginTime())+
							     "&status="+URLEncoder.encode(logindata.get(i).getStatus());
					 //task2.execute(context.getResources().getString(R.string.prefServerDefault)+"login"+value);
					 }	 
				}


	}
	/*static class RegistrationTask extends AsyncTask<String, Void, String> {
	     	String message="";
			private HttpResponse execute;

	    	@Override
	        protected String doInBackground(String... urls) {
	    		for (String url : urls) {
					DefaultHttpClient client = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);
					try {
						HttpResponse execute = client.execute(httpGet);
						InputStream content = execute.getEntity().getContent();

						BufferedReader buffer = new BufferedReader(
								new InputStreamReader(content));
						String s = "";
						while ((s = buffer.readLine()) != null) {
							message += s;
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	    		return message;
	    	}
	    	@Override
	    	protected void onPostExecute(String result) {
	    		System.out.println(result);
	    		try {
					JSONObject obj=new JSONObject(result);
					String response=obj.getString("message");
					
					if(response.equals("successful")){
						ArrayList<UsageTracking> usageTrackingId=db.getUsageActivity();
						for (int i=0;i<usageTrackingId.size();i++){
							id=Integer.valueOf(usageTrackingId.get(i).getUsageId());
							db.updateUsageActivitySyncStatus(MobiHealth.SYNC_STATUS_OLD,id);
						}
		    		}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
	
	
	static class LoginSyncTask extends AsyncTask<String, Void, String> {
     	String message="";
		private HttpResponse execute;

    	@Override
        protected String doInBackground(String... urls) {
         
    		for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						message += s;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
    		return message;
    	}
    	@Override
    	protected void onPostExecute(String result) {
    		System.out.println(result);
    		try {
				JSONObject obj=new JSONObject(result);
				String response=obj.getString("message");
				if(response.equals("successful")){
					db.updateLoginActivitySyncStatus(MobiHealth.SYNC_STATUS_OLD);
	    		}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
	}*/
}
