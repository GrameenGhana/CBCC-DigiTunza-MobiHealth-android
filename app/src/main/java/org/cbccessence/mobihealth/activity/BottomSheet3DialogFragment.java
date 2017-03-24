package org.cbccessence.mobihealth.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.adapter.GroupMeetingAttendanceAdapter;
import org.cbccessence.mobihealth.adapter.GroupMeetingsAdapter;
import org.cbccessence.mobihealth.database.MobiHealthDatabaseHandler;
import org.cbccessence.mobihealth.model.Attendee;
import org.cbccessence.mobihealth.model.GroupMeeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by aangjnr on 01/03/2017.
 */

public class BottomSheet3DialogFragment extends BottomSheetDialogFragment {

    MobiHealthDatabaseHandler databaseHandler;
    String year = null;
    String month = null; String day = null; String hr = null; String min = null;

    private int mYear, mMonth, mDay, mHour, mMinute;
    TextView ok;

    Boolean isStartDateClicked = null;
    GroupMeetingAttendanceAdapter attendeeAdapter;
    List<Attendee> attendees;

    TextInputLayout meeting_topic;
    GroupMeetingsAdapter meetingsAdapter;
    List<GroupMeeting> meetings;
    RecyclerView meetingsRecyclerView;

    public static String meeting_uid = null;
    public static String END_DATE_TIME = null;
    TextView _startDateTime;
    TextView _endDateTime;

    View rootView;

    public BottomSheet3DialogFragment(){


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.bottom_sheet_layout, null, false);



        databaseHandler = new MobiHealthDatabaseHandler(getActivity());

        return rootView;

    }





    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final Calendar c = Calendar.getInstance();

        //Get current Date
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Get Current Time
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);







    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onActivityCreated(savedInstanceState);



        _startDateTime = (TextView) rootView.findViewById(R.id.startDateText);
        _endDateTime = (TextView)  rootView.findViewById(R.id.endDateText);

        RelativeLayout selectStartDate = (RelativeLayout) rootView.findViewById(R.id.selStartDateTime);
        RelativeLayout selectEndDate = (RelativeLayout)  rootView.findViewById(R.id.selEndDateTime);

        meeting_topic = (TextInputLayout) rootView.findViewById(R.id.meeting_topic);

        ok = (TextView) rootView.findViewById(R.id.add_new_meeting);


        selectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isStartDateClicked = true;
                showDatePickerDialog();


            }
        });


        selectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStartDateClicked = false;
                showDatePickerDialog();

            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(meeting_uid == null || meeting_uid.equals("")){

                    showAlertDialog("Select date", "Please select the start date");

                }else {

                    if (attendees != null && attendees.size() <= 0) {

                        showAlertDialog("Uh-Oh!", "You haven't selected any attendees");

                    }else{


                       if( databaseHandler.insertMeeting(meeting_topic.getEditText().getText().toString().trim(), meeting_uid, END_DATE_TIME, "loc" ))
                           Toast.makeText(getActivity(), "Meeting saved!", Toast.LENGTH_SHORT).show();
                            attendees.clear();
                            attendeeAdapter.notifyDataSetChanged();

                                getActivity().finish();
                                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                getActivity().startActivity(new Intent(getActivity(), getActivity().getClass()));



                    }
                }

            }
        });

        attendees = new ArrayList<>();
        attendees.add(new Attendee("Camara, Laye"));
        attendees.add(new Attendee("Mark, James"));
        attendees.add(new Attendee("Kent, Clark"));
        attendees.add(new Attendee("Downey, Robert Jnr."));
        attendees.add(new Attendee("Hutchful, David"));
        attendees.add(new Attendee("Davis, Joseph"));
        attendees.add(new Attendee("Kang, Liu"));
        attendees.add(new Attendee("Skywalker, Anakin"));
        attendees.add(new Attendee("Skywalker, Schmi"));
        attendees.add(new Attendee("Musk, Elon"));
        attendees.add(new Attendee("Page, Larry"));
        attendees.add(new Attendee("Obama, Barrak"));
        attendees.add(new Attendee("Schwarzenegger, Arnold"));
        attendees.add(new Attendee("Person 1"));
        attendees.add(new Attendee("Person 2"));
        attendees.add(new Attendee("Person 3"));
        attendees.add(new Attendee("Wan, Obi"));
        attendees.add(new Attendee("Afflek, Ben"));
        attendees.add(new Attendee("Bush, George"));
        attendees.add(new Attendee("Gates, Bill"));

        attendeeAdapter = new GroupMeetingAttendanceAdapter(getActivity().getApplicationContext(), attendees);

        RecyclerView recycler = (RecyclerView)rootView.findViewById(R.id.bottom_sheet_recyclerView);
        LinearLayoutManager lll = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(lll);
        recycler.hasFixedSize();
        recycler.setAdapter(attendeeAdapter);
        attendeeAdapter.notifyDataSetChanged();
        attendeeAdapter.setOnItemClickListener(onItemClickListener);

    }


    GroupMeetingAttendanceAdapter.OnItemClickListener onItemClickListener = new GroupMeetingAttendanceAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

            Attendee _attendee = attendees.get(position);

            if(meeting_uid != null && !meeting_uid.equals("")){

                if(databaseHandler.doesAttendeeExist(_attendee.getAttendeeName(), meeting_uid)){

                    if(databaseHandler.removeMeetingAttendee(_attendee.getAttendeeName(), meeting_uid) > 0)
                        Log.i("DATABASE", "New attendee removed with details : " + "Name " + _attendee.getAttendeeName());

                    else Log.i("DATABASE", "Attedee " + _attendee.getAttendeeName() + " couldn't be removed");

                    _attendee.setAttended(false);
                }else{
                    databaseHandler.insertMeetingAttendee(_attendee.getAttendeeName(), meeting_uid);
                    _attendee.setAttended(true);


                }
                if(attendeeAdapter != null) attendeeAdapter.notifyDataSetChanged();



            }else{ //Display toast or dialog! select start date! because that's the meeting uid!!! it cant be null.

                showAlertDialog("Select date", "Please select the start date");
            }
        }
    };





    public void showDatePickerDialog() {

        DatePickerDialog date_picker_dialog = new DatePickerDialog(getActivity(), datePickerListener, mYear, mMonth, mDay);
        date_picker_dialog.show();


    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = String.valueOf(selectedYear);

            if(selectedMonth <= 9) month = "0" + String.valueOf(selectedMonth);
            else month = String.valueOf(selectedMonth);

            if(selectedDay <= 9) day = "0" + String.valueOf(selectedDay);
            else day = String.valueOf(selectedDay);

            showTimePickerDialog();

        }
    };


    public void showTimePickerDialog() {


        TimePickerDialog time_picker_dialog = new TimePickerDialog(getActivity(), timeSetListener, mHour, mMinute, true);
        time_picker_dialog.show();


    }

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {


            if(i <= 9)
                hr = "0" + String.valueOf(i);
            else hr = String.valueOf(i);


            if(i1 <= 9)
                min = "0" + String.valueOf(i1);
            else min = String.valueOf(i1);





            if(isStartDateClicked != null) {

                if (isStartDateClicked) { //Set text on start date
                    if(_startDateTime != null){

                        meeting_uid = day + month + year + hr + min;
                        Log.i("MEETING UID is",  meeting_uid);

                        _startDateTime.setText(day + "/" + month + "/" + year + " " + hr + ":" + min);
                        Log.i("S_DateTime Sel is ",  day + "/" + month + "/" + year + " " + hr + ":" + min);
                    }


                } else {

                    if(_endDateTime != null){

                        END_DATE_TIME = day + month + year + hr + min;

                        _endDateTime.setText(day + "/" + month + "/" + year + " " + hr + ":" + min);
                        Log.i("E_DateTime Sel is ",  day + "/" + month + "/" + year + " " + hr + ":" + min);


                    }

                    //Set date text on end date

                }
            }

        }
    };


    public void showAlertDialog(String title, String message){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.AppDialog);
        alertDialogBuilder.setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        dialogInterface.dismiss();


                    }
                }).create();


        alertDialogBuilder.show();
    }



}