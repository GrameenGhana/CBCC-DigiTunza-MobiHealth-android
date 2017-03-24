package org.cbccessence.mobihealth.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.cbccessence.mobihealth.PlaceHolder;
import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.adapter.GroupMeetingAttendanceAdapter;
import org.cbccessence.mobihealth.adapter.GroupMeetingsAdapter;
import org.cbccessence.mobihealth.application.BaseActivity;
import org.cbccessence.mobihealth.database.MobiHealthDatabaseHandler;
import org.cbccessence.mobihealth.model.Attendee;
import org.cbccessence.mobihealth.model.GroupMeeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by aangjnr on 23/02/2017.
 */

public class GroupMeetingsActivity extends BaseActivity {

    MobiHealthDatabaseHandler databaseHandler;
    String year = null;
    String month = null; String day = null; String hr = null; String min = null;

    private int mYear, mMonth, mDay, mHour, mMinute;

    Boolean isStartDateClicked = null;
    GroupMeetingAttendanceAdapter attendeeAdapter;
    List<Attendee> attendees;

    GroupMeetingsAdapter meetingsAdapter;
    List<GroupMeeting> meetings;
    RecyclerView meetingsRecyclerView;

    String meeting_uid = null;
    TextView _startDateTime;
    TextView _endDateTime;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_meetings_activity);

        meetingsRecyclerView = (RecyclerView) findViewById(R.id.meetings_recyclerView);

        databaseHandler = new MobiHealthDatabaseHandler(this);

        meetings = new ArrayList<>();


        meetings = databaseHandler.getAllMeetings();

        if (meetings != null){

            if(meetings.size() > 0) {

                meetingsAdapter = new GroupMeetingsAdapter(this, meetings);
                LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                meetingsRecyclerView.setLayoutManager(llm);
                meetingsRecyclerView.setAdapter(meetingsAdapter);
                meetingsRecyclerView.hasFixedSize();
            }else{

                PlaceHolder.inflateNoContentEmptyView(this);


            }


        }


        meetingsRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if(rv.getChildCount() > 0) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                         int action = e.getAction();
                        switch (action) {
                            case MotionEvent.ACTION_DOWN:
                                rv.requestDisallowInterceptTouchEvent(true);
                        }
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        findViewById(R.id.add_new_meeting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheet3DialogFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());








            }
        });


    }





    }


