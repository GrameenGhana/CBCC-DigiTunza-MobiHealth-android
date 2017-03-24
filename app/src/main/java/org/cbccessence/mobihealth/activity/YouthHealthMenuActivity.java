package org.cbccessence.mobihealth.activity;

import org.cbccessence.mobihealth.PlaceHolder;
import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.adapter.TrimesterListViewAdapter;
import org.cbccessence.mobihealth.application.BaseActivity;
import org.cbccessence.mobihealth.application.MobiHealth;
import org.cbccessence.mobihealth.database.MobiHealthDatabaseHandler;
import org.cbccessence.mobihealth.model.SubSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class YouthHealthMenuActivity extends BaseActivity implements OnItemClickListener{

	private ListView listView;
	private TextView header;
	private String type;
	private String submodule;
	private String module;
	private String extras;


	List<SubSection> subSecs;
	String TAG = YouthHealthMenuActivity.class.getSimpleName();
	String dir = MobiHealth.YSH_DIR;

	MobiHealthDatabaseHandler dbh;


	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		 getSubSectionNamesFromLocation(this, dir, TAG);
		dbh = new MobiHealthDatabaseHandler(this);
	    setContentView(R.layout.pregnancy_menu_activity);

		if(getSupportActionBar() != null) getSupportActionBar().setSubtitle("Youth Sexual Health");

		subSecs = new ArrayList<>();
		subSecs = dbh.getSubSections(TAG, dir);

	    listView = (ListView) findViewById(R.id.pregnancy_menu_listView);


	    int[] images={R.drawable.abstinence,
	    		R.drawable.rape,
	    		R.drawable.teenage_pregnancy};
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
