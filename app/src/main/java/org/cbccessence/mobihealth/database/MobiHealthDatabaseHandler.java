package org.cbccessence.mobihealth.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cbccessence.mobihealth.application.BaseActivity;
import org.cbccessence.mobihealth.application.MobiHealth;
import org.cbccessence.mobihealth.database.MobiHealthDataClass.MobiHealthDatabase;
import org.cbccessence.mobihealth.model.Attendee;
import org.cbccessence.mobihealth.model.GroupMeeting;
import org.cbccessence.mobihealth.model.SubSection;
import org.cbccessence.mobihealth.model.UsageTracking;
import org.cbccessence.mobihealth.model.User;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MobiHealthDatabaseHandler {
	Context context;	
	MobiHealthDatabaseHelper mDbHelper = new MobiHealthDatabaseHelper(context);
	private SQLiteDatabase database;
	
		public MobiHealthDatabaseHandler(Context context) {
		mDbHelper = new MobiHealthDatabaseHelper(context);
		
	}




	public boolean insertMeeting (String topic, String start_dateTime, String end_dateTime, String location){


		try {

			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(MobiHealthDatabase.GROUP_MEETING_TOPIC, topic);
			values.put(MobiHealthDatabase.GROUP_MEETING_START_DATE_TIME, start_dateTime);
			values.put(MobiHealthDatabase.GROUP_MEETING_END_DATE_TIME, end_dateTime);
			values.put(MobiHealthDatabase.GROUP_MEETING_LOCATION, location);
			db.insert(MobiHealthDatabase.GROUP_MEETINGS_TABLE, null, values);

			Log.i("DATABASE", "New meeting inserted with details : " + "Topic " + topic + " StartDateTime " + start_dateTime
					+ " EndDateTime " + end_dateTime + " Location " + location );
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	public boolean insertSubSection (String subSec_name,  String activity_name){


		try {

			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(MobiHealthDatabase.SUBSECTION_NAME, subSec_name);
			values.put(MobiHealthDatabase.SUBSECTION_ACTIVITY_NAME, activity_name);
 			db.insert(MobiHealthDatabase.ALL_SUBSECTION_NAMES_TABLE, null, values);

			Log.i("DATABASE", "New subsection inserted with details : " + "Name " + subSec_name + " from activity  " + activity_name );
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}



	public Integer removeSubSection (String subSec_name,  String activity_name) {

		try {
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			return db.delete(MobiHealthDatabase.ALL_SUBSECTION_NAMES_TABLE,
					MobiHealthDatabase.SUBSECTION_NAME + " = ? AND " + MobiHealthDatabase.SUBSECTION_ACTIVITY_NAME + " = ?",
					new String[]{subSec_name, activity_name});
		}catch(Exception e){

			e.printStackTrace();
			return 0;
		}

	}

	public Boolean doesSubSecNameExist(String name, String activity_name) {
		try {
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			return DatabaseUtils.queryNumEntries(db, MobiHealthDatabase.ALL_SUBSECTION_NAMES_TABLE,
					MobiHealthDatabase.SUBSECTION_NAME + " = ? AND " + MobiHealthDatabase.SUBSECTION_ACTIVITY_NAME + " = ?",
					new String[]{name, activity_name}) > 0;
		}catch(Exception e){
			e.printStackTrace();
			return false;

		}
	}




	public List<SubSection> getSubSections(String activity_name, String subDir){
		List<SubSection> subSections = new ArrayList<>();

		String query = "SELECT * FROM " + MobiHealthDatabase.ALL_SUBSECTION_NAMES_TABLE + " WHERE " +
				MobiHealthDatabase.SUBSECTION_ACTIVITY_NAME + " ='" + activity_name + "'";

		Log.i("QUERY", query);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		try{
			if(cursor.moveToFirst() && cursor.getCount() > 0){

				do{
					String name = cursor.getString(cursor.getColumnIndex(MobiHealthDatabase.SUBSECTION_NAME));
					String activity = cursor.getString(cursor.getColumnIndex(MobiHealthDatabase.SUBSECTION_ACTIVITY_NAME));


					if(BaseActivity.doesSubSecNameExistInFolder(context, subDir, name)) {
						SubSection subSection = new SubSection(name, activity);
						subSections.add(subSection);

						Log.i("DATABASE", "Sub Section added with name  " +  name
								+  " belonging to activity  " + activity);

					}else {

						if(removeSubSection(name, activity_name) > 0)
						Log.i("DATABASE", "REMOVED! No Sub Section content found with name " + name + "belonging to activity " + activity);
					}






				}while(cursor.moveToNext());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();

		}
		return subSections;
	}



	public Integer removeMeeting (String topic, String meeting_uid) {

		try {
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			return db.delete(MobiHealthDatabase.GROUP_MEETINGS_TABLE,
					MobiHealthDatabase.GROUP_MEETING_TOPIC + " = ? AND " + MobiHealthDatabase.GROUP_MEETING_START_DATE_TIME + " = ?",
					new String[]{topic, meeting_uid});
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}

	}


	public boolean insertMeetingAttendee (String attendeeName, String meetingUid){


		try {

			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(MobiHealthDatabase.GROUP_MEETING_ATTENDEE_NAME, attendeeName);
			values.put(MobiHealthDatabase.GROUP_MEETING_UID, meetingUid);
 			db.insert(MobiHealthDatabase.GROUP_MEETING_ATTENDEES_TABLE, null, values);

			Log.i("DATABASE", "New attendee inserted with details : " + "Name " + attendeeName + " StartDateTime " + meetingUid);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	public Integer removeMeetingAttendee(String attendee_name, String meeting_uid) {

		try {
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			return db.delete(MobiHealthDatabase.GROUP_MEETING_ATTENDEES_TABLE,
					MobiHealthDatabase.GROUP_MEETING_ATTENDEE_NAME + " = ? AND " + MobiHealthDatabase.GROUP_MEETING_UID + " = ?",
					new String[]{attendee_name, meeting_uid});
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}

	}



	public Boolean doesAttendeeExist(String attendee_name, String meeting_uid) {
		try {
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			return DatabaseUtils.queryNumEntries(db, MobiHealthDatabase.GROUP_MEETING_ATTENDEES_TABLE,
					MobiHealthDatabase.GROUP_MEETING_ATTENDEE_NAME + " = ? AND " + MobiHealthDatabase.GROUP_MEETING_UID + " = ?",
					new String[]{attendee_name, meeting_uid}) > 0;
		}catch(Exception e){
			e.printStackTrace();
			return false;

		}
 	}





	public List<GroupMeeting> getAllMeetings(){
		List<GroupMeeting> meetings = new ArrayList<>();

		String query = "SELECT * FROM " + MobiHealthDatabase.GROUP_MEETINGS_TABLE;
		Log.i("QUERY", query);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		try{
			if(cursor.moveToFirst() && cursor.getCount() > 0){

				do{
					GroupMeeting meeting = new GroupMeeting(
							cursor.getString(cursor.getColumnIndex(MobiHealthDatabase.GROUP_MEETING_TOPIC)),
							cursor.getString(cursor.getColumnIndex(MobiHealthDatabase.GROUP_MEETING_START_DATE_TIME)),
									cursor.getString(cursor.getColumnIndex(MobiHealthDatabase.GROUP_MEETING_END_DATE_TIME)),
											cursor.getString(cursor.getColumnIndex(MobiHealthDatabase.GROUP_MEETING_LOCATION))
					);

					meetings.add(meeting);
					Log.i("DATABASE", "Group meeting added with topic  " +  cursor.getString(cursor.getColumnIndex(MobiHealthDatabase.GROUP_MEETING_TOPIC)));


				}while(cursor.moveToNext());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();

		}
		return meetings;
	}





	public List<Attendee> getAttendees(String meeting_uid){
		List<Attendee> attendees = new ArrayList<>();

		String query = "SELECT * FROM " + MobiHealthDatabase.GROUP_MEETING_ATTENDEES_TABLE + " WHERE " + MobiHealthDatabase.GROUP_MEETING_UID
				+ " ='" + meeting_uid + "'";
		Log.i("QUERY", query);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		try{
			if(cursor.moveToFirst() && cursor.getCount() > 0){

				do{
					Attendee attendee = new Attendee(
							cursor.getString(cursor.getColumnIndex(MobiHealthDatabase.GROUP_MEETING_ATTENDEE_NAME))
					);

					attendees.add(attendee);
					Log.i("DATABASE", "Attendee added with name  " +  cursor.getString(cursor.getColumnIndex(MobiHealthDatabase.GROUP_MEETING_ATTENDEE_NAME)));


				}while(cursor.moveToNext());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();

		}
		return attendees;
	}
















	
	public void insertVolunteerInfo(String volunteer_name,String community_resident,String volunteer_phone_number,String volunteer_staff_id,String volunteer_chps_zone){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MobiHealthDatabase.COL_NAME_OF_VOLUNTEER,volunteer_name);
		values.put(MobiHealthDatabase.COL_COMMUNITY_RESIDENT,community_resident);
		values.put(MobiHealthDatabase.COL_VOLUNTEER_PHONE_NUMBER,volunteer_phone_number);
		values.put(MobiHealthDatabase.COL_VOLUNTEER_STAFF_ID,volunteer_staff_id);
		values.put(MobiHealthDatabase.COL_VOLUNTEER_CHPS_ZONE,volunteer_chps_zone);
		
		long newRowId;
		newRowId = db.insert(
				MobiHealthDatabase.VOLUNTEER_INFO_TABLE_NAME, null, values);
	}
	
	public void insertChpsNurse(String nurse_name,String cch_phone_number,String chps_zone){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MobiHealthDatabase.COL_NAME_OF_NURSE,nurse_name);
		values.put(MobiHealthDatabase.COL_CCH_PHONE_NUMBER,cch_phone_number);
		values.put(MobiHealthDatabase.COL_CHPS_ZONE,chps_zone);
		
		long newRowId;
		newRowId = db.insert(
				MobiHealthDatabase.CHPS_NURSE_TABLE_NAME, null, values);
	}
	
	public void insertUserTable(String volunteer_name,String username,String password,String chps_zone,String community_resident){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MobiHealthDatabase.COL_LOGIN_NAME_OF_VOLUNTEER,volunteer_name);
		values.put(MobiHealthDatabase.COL_USERNAME,username);
		values.put(MobiHealthDatabase.COL_PASSWORD,password);
		values.put(MobiHealthDatabase.COL_LOGIN_CHPS_ZONE, chps_zone);
		values.put(MobiHealthDatabase.COL_LOGIN_COMMUNITY_RESIDENT, community_resident);
		
		long newRowId;
		newRowId = db.insert(
				MobiHealthDatabase.LOGIN_TABLE_NAME, null, values);
	}
	
	public void insertLoginActivity(String date,String time,String username,String password,String status,String update_status){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MobiHealthDatabase.COL_DATE_LOGGED_IN,date);
		values.put(MobiHealthDatabase.COL_TIME_LOGGED_IN,time);
		values.put(MobiHealthDatabase.COL_USERNAME,username);
		values.put(MobiHealthDatabase.COL_LOGIN_STATUS,status);
		values.put(MobiHealthDatabase.COL_LOGIN_UPDATE_STATUS,update_status);
		
		long newRowId;
		newRowId = db.insert(
				MobiHealthDatabase.LOGIN_ACTIVITY_TABLE_NAME, null, values);
	}
	
	public void insertUsageActivity(String username,String module,String submodule, String type,String action_date,String duration,String duration_played,String status,String extras,String update_status){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MobiHealthDatabase.COL_USERNAME_USAGE_TRACKING,username);
		values.put(MobiHealthDatabase.COL_MODULE,module);
		values.put(MobiHealthDatabase.COL_SUBMODULE,submodule);
		values.put(MobiHealthDatabase.COL_TYPE,type);
		values.put(MobiHealthDatabase.COL_ACTION_DATE,action_date);
		values.put(MobiHealthDatabase.COL_DURATION,duration);
		values.put(MobiHealthDatabase.COL_DURATION_PLAYED,duration_played);
		values.put(MobiHealthDatabase.COL_STATUS,status);
		values.put(MobiHealthDatabase.COL_EXTRAS,extras);
		values.put(MobiHealthDatabase.COL_UPDATE_STATUS_TRACKING,update_status);
		
		
		long newRowId;
		newRowId = db.insert(
				MobiHealthDatabase.USAGE_ACTIVITY_TABLE_NAME, null, values);
	}
	
	public ArrayList<String> verifyLogin(String username, String password){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		ArrayList<String> list=new ArrayList<String>();
		 String strQuery="select "+MobiHealthDatabase._ID
                 +","+MobiHealthDatabase.COL_USERNAME
                 +"," +MobiHealthDatabase.COL_PASSWORD
                 +","+MobiHealthDatabase.COL_LOGIN_NAME_OF_VOLUNTEER
                 +","+MobiHealthDatabase.COL_LOGIN_CHPS_ZONE
                 +","+MobiHealthDatabase.COL_LOGIN_COMMUNITY_RESIDENT
                 +" from "+MobiHealthDatabase.LOGIN_TABLE_NAME
                 +" where "+MobiHealthDatabase.COL_USERNAME+" = \""+username+"\""
                 + " and "+MobiHealthDatabase.COL_PASSWORD+ " = \""+password+"\"";
		 
		System.out.println(strQuery);
		Cursor c=db.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			list.add(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_USERNAME)));
			list.add(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_PASSWORD)));
			list.add(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_LOGIN_NAME_OF_VOLUNTEER)));
			list.add(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_CHPS_ZONE)));
			list.add(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_LOGIN_COMMUNITY_RESIDENT)));
			c.moveToNext();						
		}
		c.close();
		return list;
	}
	
	public ArrayList<String> getPhoneNumbers(String chps_zone){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		ArrayList<String> list=new ArrayList<String>();
		 String strQuery="select "+MobiHealthDatabase.COL_CCH_PHONE_NUMBER
                 +" from "+MobiHealthDatabase.CHPS_NURSE_TABLE_NAME
                 +" where "+MobiHealthDatabase.COL_CHPS_ZONE+" = \""+chps_zone+"\"";		 
		System.out.println(strQuery);
		Cursor c=db.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			list.add(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_CCH_PHONE_NUMBER)));
			c.moveToNext();	
		}
		c.close();
		return list;
		
	}
	
	public int getAllLoginActivity(){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		int count=0;
		 String strQuery="select * "	
                 +" from "+MobiHealthDatabase.LOGIN_ACTIVITY_TABLE_NAME
                 +" where "+ MobiHealthDatabase.COL_LOGIN_UPDATE_STATUS
                 +" = '"+"new_record"+"'";	 
		System.out.println(strQuery);
		Cursor c=db.rawQuery(strQuery, null);
		count=c.getCount();																						
		c.close();
		return count;
		
	}
	public ArrayList<User> getLoginActivity(){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		ArrayList<User> list=new ArrayList<User>();
		 String strQuery="select * "
                 +" from "+MobiHealthDatabase.LOGIN_ACTIVITY_TABLE_NAME
                 +" where "+ MobiHealthDatabase.COL_LOGIN_UPDATE_STATUS
                 +" = '"+MobiHealth.SYNC_STATUS_NEW+"'";		 
		System.out.println(strQuery);
		Cursor c=db.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			
			User u = new User();
			u.setUserId(c.getString(c.getColumnIndex(MobiHealthDatabase._ID)));
			u.setUsername(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_USERNAME_LOGIN_ACTIVITY)));
			u.setLoginDate(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_DATE_LOGGED_IN)));
			u.setLoginTime(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_TIME_LOGGED_IN)));
			u.setStatus(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_STATUS)));
				list.add(u);
			
			c.moveToNext();	
		}
		c.close();
		return list;
		
	}
	public ArrayList<UsageTracking> getUsageActivity(){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		ArrayList<UsageTracking> list=new ArrayList<UsageTracking>();
		 String strQuery="select *"
                 +" from "+MobiHealthDatabase.USAGE_ACTIVITY_TABLE_NAME
                 +" where "+ MobiHealthDatabase.COL_UPDATE_STATUS_TRACKING
                 +" = '"+MobiHealth.SYNC_STATUS_NEW+"'";	 
		System.out.println(strQuery);
		Cursor c=db.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			UsageTracking usage = new UsageTracking();
			usage.setUsageId(c.getString(c.getColumnIndex(MobiHealthDatabase._ID)));
			usage.setUsername(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_USERNAME_USAGE_TRACKING)));
			usage.setDuration(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_DURATION)));
			usage.setDurationPlayed(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_DURATION_PLAYED)));
			usage.setActionDate(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_ACTION_DATE)));
			usage.setExtras(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_EXTRAS)));
			usage.setModule(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_MODULE)));
			usage.setSubModule(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_SUBMODULE)));
			usage.setType(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_TYPE)));
			usage.setStatus(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_STATUS)));
			list.add(usage);
			c.moveToNext();	
		}
		c.close();
		return list;
		
	}


	public int getRowLoginCount() {
		 SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + MobiHealthDatabase.LOGIN_TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
	public int getRowLoginActivityCount() {
		 SQLiteDatabase db = mDbHelper.getReadableDatabase();
       String countQuery = "SELECT  * FROM " + MobiHealthDatabase.LOGIN_ACTIVITY_TABLE_NAME;
       Cursor cursor = db.rawQuery(countQuery, null);
       int rowCount = cursor.getCount();
       db.close();
       cursor.close();
        
       // return row count
       return rowCount;
   }
	
	public int getRowUsageActivityCount() {
		 SQLiteDatabase db = mDbHelper.getReadableDatabase();
      String countQuery = "SELECT  * FROM " + MobiHealthDatabase.USAGE_ACTIVITY_TABLE_NAME;
      Cursor cursor = db.rawQuery(countQuery, null);
      int rowCount = cursor.getCount();
      db.close();
      cursor.close();
       
      // return row count
      return rowCount;
  }
	public int LoginActivitySyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM "+ MobiHealthDatabase.LOGIN_ACTIVITY_TABLE_NAME+" where "
        					+MobiHealthDatabase.COL_LOGIN_UPDATE_STATUS+ " = '"+"new_record"+"'";
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
    }
	
	public void updateLoginActivitySyncStatus(String status){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();    
        String updateQuery = "Update "+MobiHealthDatabase.LOGIN_ACTIVITY_TABLE_NAME+ " set "+
        					MobiHealthDatabase.COL_LOGIN_UPDATE_STATUS+" = '"+ status +"'";
        					Log.d("query",updateQuery);        
        database.execSQL(updateQuery);
        database.close();
    }


	public void updateUsageActivitySyncStatus(String status,int id){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();    
        String updateQuery = "Update "+MobiHealthDatabase.USAGE_ACTIVITY_TABLE_NAME+ " set "+
        					MobiHealthDatabase.COL_UPDATE_STATUS_TRACKING+" = '"+ status +"'"+
        					" where "+ MobiHealthDatabase._ID+" = "+id;
        					Log.d("query",updateQuery);        
        database.execSQL(updateQuery);
        database.close();
    }
	public ArrayList<User> getLoginActivityUpdatedStatus(){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		ArrayList<User> list=new ArrayList<User>();
		 String selectQuery = "SELECT  "+	MobiHealthDatabase.COL_LOGIN_UPDATE_STATUS+														
				 				" FROM "+ MobiHealthDatabase.LOGIN_ACTIVITY_TABLE_NAME+" where "
					+MobiHealthDatabase.COL_LOGIN_UPDATE_STATUS+ " = '"+MobiHealth.SYNC_STATUS_NEW+"'";
		Cursor c=db.rawQuery(selectQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			User u = new User();
			u.setUserId(c.getString(c.getColumnIndex(MobiHealthDatabase._ID)));
			u.setLoginDate(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_DATE_LOGGED_IN)));
			u.setLoginTime(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_TIME_LOGGED_IN)));
			u.setStatus(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_STATUS)));
			u.setUsername(c.getString(c.getColumnIndex(MobiHealthDatabase.COL_USERNAME_LOGIN_ACTIVITY)));
				list.add(u);
			
			c.moveToNext();	
		}
		c.close();
		return list;
		
	}
	public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}
}
