/* 
 * This file is part of OppiaMobile - http://oppia-mobile.org/
 * 
 * OppiaMobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * OppiaMobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OppiaMobile. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cbccessence.mobihealth.application;


import org.cbccessence.mobihealth.R;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import java.io.File;


public class MobiHealth extends Application {

	public static final String TAG = MobiHealth.class.getSimpleName();

	public static String ROOT_DIR = Environment
			.getExternalStorageDirectory() + File.separator + "Tunza";

	public static final String VA_DOC_DIR = "Visual Aids/Documents";
	public static final String VA_IMA_DIR = "Visual Aids/Images";
	public static final String VA_VID_DIR = "Visual Aids/Videos";

	public static final String PM_DIR =  "Pregnancy Messages";

	public static final String PM_FIRST_TRE = PM_DIR + File.separator + "First Trimester";
	public static final String PM_SECOND_TRE = PM_DIR + File.separator + "Second Trimester";
	public static final String PM_THIRD_TRE = PM_DIR + File.separator + "Third Trimester";

	public static final String BFY_DIR =  "Baby's First Year";

	public static final String YSH_DIR =  "Youth Sexual Health";



	private static Context context;
	public static String SERVER_ADDRESS="";
	public static final String MOBIHEALTHSYNC="MobiHealthSync.php";
	public static final String SYNC_STATUS_NEW="new_record";
	public static final String SYNC_STATUS_OLD="updated";
	public static final String SMS_STATUS_SENT="sent";
	public static final String SMS_STATUS_NOT_SENT="not sent";
	public static final String AUDIO_STATUS_COMPLETED="completed";
	public static final String AUDIO_STATUS_NOT_COMPLETED="not completed";
	public static final String VISUAL_STATUS_COMPLETED="viewed";
	public static final String VISUAL_STATUS_NOT_COMPLETED="not viewed";
	public static final String MODULE_BABY_FIRST_YEAR="Baby's first year";
	public static final String MODULE_PREGNANCY_MESSAGES="Pregnancy messages";
	public static final String MODULE_YOUTH_SEXUAL_HEALTH="Youth Sexual Health";
	public static final String MODULE_VISUAL_AIDS ="Visual Aids";
	public static final String MODULE_REFERRAL_ALERTS="Referral Alerts System";
	public static final String EXTRAS_FIRST_TRIMESTER="	1st Trimester";
	public static final String EXTRAS_SECOND_TRIMESTER="2nd Trimester";
	public static final String EXTRAS_THIRD_TRIMESTER="3rd Trimester";
	public static SharedPreferences prefs;
	public static final String LANGUAGE_EWE="Ewe";
	public static final String LANGUAGE_ENGLISH="English";
	public final static String AUDIO_URL = "audio_url";
    public final static String IMG_URL = "image_url";
    public final static String MODULE = "module";
    public final static String SUB_MODULE = "submodule";
    public final static String TYPE = "type";
    public final static String EWE_AUDIO_LOCATION = "eweLocation";
    public final static String ENGLISH_AUDIO_LOCATION = "englishLocation";
    public final static String EXTRAS = "extras";
	public void onCreate(){
        super.onCreate();
        MobiHealth.context = getApplicationContext();
        	SERVER_ADDRESS=context.getResources().getString(R.string.prefServerDefault);

    }

	 public static Context getAppContext() {
	        return MobiHealth.context;
	        
	    }
	
	public static String getUsername(){
		String username=null;
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		username=prefs.getString("fullname", "name");
		return username;
	}
	
	
	public static String getLanguage(){
		String language=null;
		if(prefs==null){
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		language=prefs.getString("option", LANGUAGE_ENGLISH);
		}
		return language;
	}
}
