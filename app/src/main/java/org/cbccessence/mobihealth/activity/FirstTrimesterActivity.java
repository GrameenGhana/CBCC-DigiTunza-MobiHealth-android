package org.cbccessence.mobihealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import org.cbccessence.mobihealth.PlaceHolder;
import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.adapter.TrimesterListViewAdapter;
import org.cbccessence.mobihealth.application.BaseActivity;
import org.cbccessence.mobihealth.application.MobiHealth;
import org.cbccessence.mobihealth.database.MobiHealthDatabaseHandler;
import org.cbccessence.mobihealth.model.SubSection;

import java.util.ArrayList;
import java.util.List;

public class FirstTrimesterActivity extends BaseActivity implements OnItemClickListener{
	private TextView header;
	private ListView listView;
	private String type;
	private String submodule;
	private String module;
	private String extras;
	List<SubSection> subSecs;
	String TAG = FirstTrimesterActivity.class.getSimpleName();
	String dir = MobiHealth.PM_FIRST_TRE;

	MobiHealthDatabaseHandler dbh;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		 getSubSectionNamesFromLocation(this, dir, TAG);

		dbh = new MobiHealthDatabaseHandler(this);
	    setContentView(R.layout.trimester_menu_activity);

		if(getSupportActionBar() != null) getSupportActionBar().setSubtitle("First Trimester");
		subSecs = new ArrayList<>();



 	    listView=(ListView) findViewById(R.id.pregnancy_menu_listView);
	    int[] images={
	    		R.drawable.pregnancy,
	    		R.drawable.nutrition,
	    		R.drawable.pregnancy_normal,
	    		R.drawable.malaria_in_pregnancy,
	    		R.drawable.danger_signs_pregnancy,
	    		R.drawable.medicine
	    	};

		subSecs = dbh.getSubSections(TAG, dir);

		if(subSecs != null && subSecs.size() > 0) {
			header = (TextView) findViewById(R.id.textView1);

			TrimesterListViewAdapter adapter = new TrimesterListViewAdapter(FirstTrimesterActivity.this, subSecs, images);
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
