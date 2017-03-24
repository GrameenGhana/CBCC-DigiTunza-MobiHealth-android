package org.cbccessence.mobihealth.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.cbccessence.mobihealth.PlaceHolder;
import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.adapter.VisualAidsVideoAdapter;
import org.cbccessence.mobihealth.application.BaseActivity;
import org.cbccessence.mobihealth.application.MobiHealth;
import org.cbccessence.mobihealth.application.Player;
import org.cbccessence.mobihealth.database.MobiHealthDatabaseHandler;
import org.cbccessence.mobihealth.model.Content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aangjnr on 17/02/2017.
 */

public class VisualAidsVideosFragment extends Fragment {

    View rootView;
    private ListView video_grid;
    int[] mThumbIds;
    String[] captions;
    ArrayList<String> videoPaths;
    private File[] fileList;
    private MobiHealthDatabaseHandler db;
    private String submodule;
    private String module;
    private String type;
    Player player;
    private String extras;
    RecyclerView mRecycler;
    VisualAidsVideoAdapter mAdapter;

    //Same model can be used for images
    List<Content> videos;
    String TAG = VisualAidsVideosFragment.class.getSimpleName();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.visual_aids_video_gallery,null,false);

        mRecycler = (RecyclerView) rootView.findViewById(R.id.va_video_recyclerView);
        videos = new ArrayList<>();

        return rootView;

    }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onActivityCreated(savedInstanceState);


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mRecycler.setHasFixedSize(true);


        videos = BaseActivity.getAssetFromLocation(MobiHealth.VA_VID_DIR);


        if (videos != null && videos.size() != 0){
            mAdapter = new VisualAidsVideoAdapter(getActivity(), videos);
            mRecycler.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mAdapter.setOnItemClickListener(onItemClickListener);
        }

        else //PlaceHolder.inflateNoContentEmptyView((AppCompatActivity) getActivity());
            rootView.findViewById(R.id.emptyView_fragments).setVisibility(View.VISIBLE);


    }





    VisualAidsVideoAdapter.OnItemClickListener onItemClickListener = new VisualAidsVideoAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {


            //get section Id, start subsections activity with section id, get sub sections from db which has section_id = section_id you clicked :)
            Content doc = videos.get(position);

            String file_name = doc.getDocName();
            String location = doc.getDocLoc();



            Log.i(TAG, "Absolute path is  " + location);

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            Uri data = Uri.parse(location);
            intent.setDataAndType(data, "video/mp4");
            startActivity(intent);




        }
    };
}
