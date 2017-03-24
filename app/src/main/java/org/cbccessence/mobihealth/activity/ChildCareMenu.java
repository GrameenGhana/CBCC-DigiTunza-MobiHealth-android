package org.cbccessence.mobihealth.activity;

import org.cbccessence.mobihealth.PlaceHolder;
import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.adapter.TrimesterListViewAdapter;
import org.cbccessence.mobihealth.application.BaseActivity;
import org.cbccessence.mobihealth.application.MobiHealth;
import org.cbccessence.mobihealth.database.MobiHealthDatabaseHandler;
import org.cbccessence.mobihealth.model.SubSection;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChildCareMenu extends BaseActivity implements OnItemClickListener{

	private ListView listView;
	private TextView header;
	private String type;
	private String submodule;
	private String module;
	private String extras;

	List<SubSection> subSecs;
	String TAG = ChildCareMenu.class.getSimpleName();
	String dir = MobiHealth.BFY_DIR;

	MobiHealthDatabaseHandler dbh;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		getSubSectionNamesFromLocation(this, dir, TAG);
		dbh = new MobiHealthDatabaseHandler(this);
	    setContentView(R.layout.pregnancy_menu_activity);



		if(getSupportActionBar() != null) getSupportActionBar().setSubtitle("Baby's First Year");

		subSecs = new ArrayList<>();
		subSecs = dbh.getSubSections(TAG, dir);

	    listView=(ListView) findViewById(R.id.pregnancy_menu_listView);

	    int[] images={R.drawable.respiratory,
	    			  R.drawable.danger_signs_pregnancy,
	    			  R.drawable.family_planning,
	    			  R.drawable.healthcare,
	    			  R.drawable.immunization,
	    			  R.drawable.malaria_infancy,
	    			  R.drawable.birth_preparedness,
	    			  R.drawable.breastfeeding,
	    			  R.drawable.motherhood,
	    			  R.drawable.diarhoea};

		if(subSecs != null && subSecs.size() > 0) {
			header = (TextView) findViewById(R.id.textView1);

			TrimesterListViewAdapter adapter = new TrimesterListViewAdapter(this, subSecs, images);
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
