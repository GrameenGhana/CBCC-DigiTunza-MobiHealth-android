package org.cbccessence.mobihealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import org.cbccessence.mobihealth.PlaceHolder;
import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.adapter.TrimesterListViewAdapter;
import org.cbccessence.mobihealth.application.BaseActivity;
import org.cbccessence.mobihealth.application.MobiHealth;
import org.cbccessence.mobihealth.database.MobiHealthDatabaseHandler;
import org.cbccessence.mobihealth.model.SubSection;

import java.util.ArrayList;
import java.util.List;

public class SecondTrimesterActivity extends BaseActivity implements OnItemClickListener{
	private ListView listView;
	private TextView header;
	private String type;
	private String submodule;
	private String module;
	private String extras;
	List<SubSection> subSecs;
	String TAG = SecondTrimesterActivity.class.getSimpleName();
	String dir = MobiHealth.PM_SECOND_TRE;

	MobiHealthDatabaseHandler dbh;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		 getSubSectionNamesFromLocation(this, dir, TAG);
		dbh = new MobiHealthDatabaseHandler(this);
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.trimester_menu_activity);

		if(getSupportActionBar() != null) getSupportActionBar().setSubtitle("Second Trimester");

		subSecs = new ArrayList<>();


	    listView=(ListView) findViewById(R.id.pregnancy_menu_listView);
	    header=(TextView) findViewById(R.id.textView1);

		subSecs = dbh.getSubSections(TAG, dir);


	    	int[] images={
	    		R.drawable.birth_preparedness,
	    		R.drawable.warning_signs,
	    		R.drawable.pregnancymalaria,
	    		R.drawable.nutrition,
	    		R.drawable.handwashing
	    	};


		if(subSecs != null && subSecs.size() > 0) {
			header = (TextView) findViewById(R.id.textView1);

			TrimesterListViewAdapter adapter = new TrimesterListViewAdapter(SecondTrimesterActivity.this, subSecs, images);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);

		}else{
			PlaceHolder.inflateNoContentEmptyView(this);
		}
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent( this, AudioGalleryActivity.class );
		intent.putExtra("subSecName", subSecs.get(position).getSubSectionName());
		intent.putExtra("directory", dir);
		intent.putExtra("type", "Audio");
		startActivity(intent);

	}

}
