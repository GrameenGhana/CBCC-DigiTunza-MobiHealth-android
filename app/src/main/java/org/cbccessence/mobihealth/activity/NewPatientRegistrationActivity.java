package org.cbccessence.mobihealth.activity;

import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.application.BaseActivity;
import org.cbccessence.mobihealth.application.Utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewPatientRegistrationActivity extends BaseActivity   {
	int year, month, day, translation, hr, min;

	private int mYear, mMonth, mDay, mHour, mMinute;


	TextView addfield;
	TextInputLayout staffID;
	TextInputLayout facilityID;
	TextInputLayout name;

	Spinner reg_mode;
	LinearLayout regMode_options;


	Spinner client_type;
	LinearLayout clientType_options;

	RelativeLayout selectDate;

	CheckBox insured_yes;
	CheckBox insured_no;
	ScrollView sv;

	LinearLayout insuranceOptions;

	CheckBox male;
	CheckBox female;

	Switch rmm;

	LinearLayout lin_lay;
	LinearLayout rmm_options;
	TextInputLayout number;

	Spinner chanel;
	Spinner program;

	Spinner start_week;


 	/** Called when the activity is first created. */



	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.client_registration);


		final java.util.Calendar c = java.util.Calendar.getInstance();

		//Get current Date
		mYear = c.get(java.util.Calendar.YEAR);
		mMonth = c.get(java.util.Calendar.MONTH);
		mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

		// Get Current Time
		mHour = c.get(java.util.Calendar.HOUR_OF_DAY);
		mMinute = c.get(java.util.Calendar.MINUTE);



		translation = Utils.getScreenHeight(this)/3;

		sv = (ScrollView) findViewById(R.id.scroll_view);
		lin_lay = (LinearLayout) findViewById(R.id.lin_lay);

		staffID = (TextInputLayout) findViewById(R.id.staffId);
		facilityID  = (TextInputLayout) findViewById(R.id.facilityId);
		name  = (TextInputLayout) findViewById(R.id.user_name_layout);
		reg_mode  = (Spinner) findViewById(R.id.reg_mode_spinner);
		regMode_options  = (LinearLayout) findViewById(R.id.use_printed_id_options);

		addfield = (TextView) findViewById(R.id.add_field);

		client_type  = (Spinner) findViewById(R.id.client_type_spinner);
		clientType_options  = (LinearLayout) findViewById(R.id.child_orientation_options);

		 selectDate  = (RelativeLayout) findViewById(R.id.select_date);

		insured_yes  = (CheckBox) findViewById(R.id.yes);
		insured_no  = (CheckBox) findViewById(R.id.no);

		insuranceOptions  = (LinearLayout) findViewById(R.id.insured_options);

		male  = (CheckBox) findViewById(R.id.male);
		female  = (CheckBox) findViewById(R.id.female);

		rmm  = (Switch) findViewById(R.id.rmm);

		rmm_options  = (LinearLayout) findViewById(R.id.extraLayout);
		number  = (TextInputLayout) findViewById(R.id.user_phone_layout);

		chanel  = (Spinner) findViewById(R.id.channel_spinner);
		program  = (Spinner) findViewById(R.id.program_spinner);

		start_week  = (Spinner) findViewById(R.id.start_week);




		selectDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {


				showDatePickerDialog();



			}
		});




		male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

				if(male.isChecked()) female.setChecked(false);
				else female.setChecked(true);


			}
		});

		insured_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

				if(insured_no.isChecked()) insured_yes.setChecked(false);
				else insured_yes.setChecked(true);


			}
		});

		insured_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

				if(insured_yes.isChecked()){
					insured_no.setChecked(false);
					startContentAnimation(insuranceOptions);

				}
				else {

					resetContentAnimation(insuranceOptions);
					insured_no.setChecked(true);}


			}
		});

		female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

				if(female.isChecked()) male.setChecked(false);
				else male.setChecked(true);


			}
		});

		rmm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

				if (compoundButton.isChecked()) startContentAnimation(rmm_options);
				else resetContentAnimation(rmm_options);

			}
		});



		client_type.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				String selectedItem = adapterView.getSelectedItem().toString();

				if(selectedItem.equalsIgnoreCase("pregnant mother") || selectedItem.equalsIgnoreCase("child under 5")){

					female.setChecked(true);
					male.setChecked(false);

				}else if (selectedItem.equalsIgnoreCase("mother of infant")){
					female.setChecked(true);
					male.setChecked(false);

					startContentAnimation(clientType_options);


				}else{

					male.setChecked(true);
					female.setChecked(false);
					resetContentAnimation(clientType_options);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});


		reg_mode.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				String selectedItem = adapterView.getSelectedItem().toString();

				if(selectedItem.equalsIgnoreCase("user printed id")){

					startContentAnimation(regMode_options);

				}else
					resetContentAnimation(regMode_options);

			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});




		addfield.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				addEditText(NewPatientRegistrationActivity.this);
			}
		});
























	}







	/*

	public class DatePickerRegDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		private Calendar c;

		@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
}

		public void onDateSet(DatePicker view, int year, int month, int day) {          
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MMM-dd");
			reg_date.setText(format1.format((new StringBuilder().append(year+1).append("-")
																.append(month+1).append("-")
																.append(day))));
		}
	}
	
	public class DatePickerDOBFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		private Calendar c;

		@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
}

		public void onDateSet(DatePicker view, int year, int month, int day) {          
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MMM-dd");
			date_of_birth.setText(format1.format((new StringBuilder().append(year+1).append("-")
																.append(month+1).append("-")
																.append(day))));
		}
	}
	
	public class DatePickerNhisExpiryDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		private Calendar c;

		@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
}

		public void onDateSet(DatePicker view, int year, int month, int day) {          
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MMM-dd");
			nhis_expiry_date.setText(format1.format((new StringBuilder().append(year+1).append("-")
																.append(month+1).append("-")
																.append(day))));
		}
	}

	*/



	/*private static class RegistrationTask extends AsyncTask<String, Void, String> {
	 	String message="";

		@Override
	    protected String doInBackground(String... urls) {
	     
			for (String url : urls) {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				    HttpPost httppost = new HttpPost(url);
				    String staff_id_entered=staff_id.getText().toString();
				    try {
				        // Add your data
				        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				        nameValuePairs.add(new BasicNameValuePair("staffId", "1"));
				        nameValuePairs.add(new BasicNameValuePair("facilityId", "414"));
				        //nameValuePairs.add(new BasicNameValuePair("regMode", "PREGNANT_MOTHER"));
				        nameValuePairs.add(new BasicNameValuePair("firstName", "Florence"));
				        nameValuePairs.add(new BasicNameValuePair("middleName", "Lomotiorkor"));
				        nameValuePairs.add(new BasicNameValuePair("lastName", "Jones"));
				        nameValuePairs.add(new BasicNameValuePair("dob", "15-08-1990"));
				        nameValuePairs.add(new BasicNameValuePair("gender", "Female"));
				        nameValuePairs.add(new BasicNameValuePair("estimatedDob", "FALSE"));
				        nameValuePairs.add(new BasicNameValuePair("nhisNumber", "1234567"));
				        nameValuePairs.add(new BasicNameValuePair("nhisExpiryDate", "15-09-2014"));
				        nameValuePairs.add(new BasicNameValuePair("region", "Greater Accra"));
				        nameValuePairs.add(new BasicNameValuePair("address", "Accra"));
				        nameValuePairs.add(new BasicNameValuePair("phoneNumber", "233540827309"));
				       // nameValuePairs.add(new BasicNameValuePair("username", "admin"));
				        nameValuePairs.add(new BasicNameValuePair("apiKey", "sfsujdfibfbsifabifab"));
				        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				        HttpResponse execute = httpclient.execute(httppost);
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
		
		protected void onPostExecute(String result) {
    		System.out.println(result);
    		
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
    		
    		
    	}
	}
*/

	public void showDatePickerDialog() {

		DatePickerDialog date_picker_dialog = new DatePickerDialog(this, datePickerListener, mYear, mMonth, mDay);
		date_picker_dialog.show();


	}

	private DatePickerDialog.OnDateSetListener datePickerListener
			= new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
							  int selectedMonth, int selectedDay) {

			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

		}
	};


	public void showTimePickerDialog(){


		TimePickerDialog time_picker_dialog = new TimePickerDialog(this, timeSetListener, mHour, mMinute, true);
		time_picker_dialog.show();


	}

	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker timePicker, int i, int i1) {

			hr = i;
			min = i1;



		}
	};





	private void startContentAnimation(final View v) {
		/*v.animate()
				.translationY(0)
				.alpha(1)
				.setInterpolator(new AccelerateDecelerateInterpolator())
				.setStartDelay(200)
				.setDuration(700)
				.start();*/
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		anim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		v.startAnimation(anim);


	}

	private void resetContentAnimation(final View v) {

		Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		anim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		v.startAnimation(anim);
 	/*	v.animate()
				.translationY(translation)
				.setInterpolator(new LinearInterpolator())
				.alpha(0)
				.setDuration(500)
				.start();


*/
	}


	public void addEditText(Context context){

		TextInputLayout ed = new TextInputLayout (context);
		ViewGroup.LayoutParams lParamsMW = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		ed.setLayoutParams(lParamsMW);
		ed.setHint("Just Added");
		lin_lay.addView(ed);


		Toast.makeText(context, "added!", Toast.LENGTH_SHORT).show();



	}
}
