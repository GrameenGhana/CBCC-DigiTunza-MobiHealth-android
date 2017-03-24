package org.cbccessence.mobihealth.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.application.ExpandableItemLayout;
import org.cbccessence.mobihealth.database.MobiHealthDatabaseHandler;
 import org.cbccessence.mobihealth.model.Attendee;
import org.cbccessence.mobihealth.model.GroupMeeting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aangjnr on 01/03/2017.
 */


public class GroupMeetingsAdapter extends RecyclerView.Adapter<GroupMeetingsAdapter.GroupMeetingsViewHolder> {

    MobiHealthDatabaseHandler databaseHandler;
    List<Attendee> attendees;
    List<GroupMeeting> meetings;
    private Context context;
    private int lastAnimatedPosition = -1;
    private static final int ANIMATED_ITEMS_COUNT = 2;

    AttendeeAdapter attendeesAdapter;



    public GroupMeetingsAdapter(Context c, List<GroupMeeting> _meetings){
        this.context = c;
        this.meetings = _meetings;
        databaseHandler = new MobiHealthDatabaseHandler(c);

    }

    @Override
    public GroupMeetingsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_meetings_itemview, viewGroup, false);

        return new GroupMeetingsViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final GroupMeetingsViewHolder viewHolder, final int position) {
        GroupMeeting meeting = meetings.get(position);

        viewHolder.topic.setText(meeting.getMeetingTopic());
        viewHolder.startDateTime.setText(dateFormatter(meeting.getMeetingStartDateTime()));
        viewHolder.endDateTime.setText(dateFormatter(meeting.getMeetingEndDateTime()));

        attendees = new ArrayList<>();
        attendees = databaseHandler.getAttendees(meeting.getMeetingStartDateTime());

        if (attendees != null) {

            attendeesAdapter = new AttendeeAdapter(context, attendees);
              viewHolder.attendeesListView.setAdapter(attendeesAdapter);
            attendeesAdapter.notifyDataSetChanged();
        }





    }



    public class GroupMeetingsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView topic;
        TextView startDateTime;
        TextView endDateTime;
        ImageView expand;
        ListView attendeesListView;
        ExpandableItemLayout expandableItemLayout;
        CardView cv;
        RelativeLayout mLayout;


        GroupMeetingsViewHolder(View itemView){
            super(itemView);

            expandableItemLayout = (ExpandableItemLayout) itemView.findViewById(R.id.expandLay);
            topic = (TextView) itemView.findViewById(R.id.topic);
            startDateTime = (TextView) itemView.findViewById(R.id.startDate);
            endDateTime = (TextView) itemView.findViewById(R.id.endDate);
            expand = (ImageView) itemView.findViewById(R.id.expand);
            attendeesListView = (ListView) itemView.findViewById(R.id.meeting_attendance_listview);

            cv = (CardView) itemView.findViewById(R.id.expCardViewLayout);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.main_linear_layout);





            mLayout.setOnClickListener(this);
            expand.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(!expandableItemLayout.isExpanded()){
                 expandableItemLayout.setExpanded(true, true);
                rotateImage(expand, (int) expand.getRotation(), 180);
            }else{
                 expandableItemLayout.setExpanded(false, true);
                rotateImage(expand, 180, 0);

            }

        }
    }





    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return meetings.size();

    }




    public String dateFormatter(String dateTime){
        String date;
        String time;

        try {

            String dd, MM, yyyy, hr, mm;

            dd = dateTime.substring(0, 2);
            MM = dateTime.substring(2, 4);
            yyyy = dateTime.substring(4, 8);
            hr = dateTime.substring(8, 10);
            mm = dateTime.substring(10, 12);

            date = dd + "/" + MM + "/" + yyyy;
            time = hr + ":" + mm;
            Log.i("GRP MTG Adapter", "Date and time is\t" + date + "  " + time);

            return date + " " + time;
        }catch(Exception e){
            e.printStackTrace();
            return dateTime;
        }






    }


    public void rotateImage(ImageView v, int currentPosition, int newPosition ){

        final RotateAnimation rotateAnim = new RotateAnimation(
                currentPosition, newPosition, v.getWidth()/2, v.getHeight()/2);

        rotateAnim.setDuration(250); // Use 0 ms to rotate instantly
        rotateAnim.setFillAfter(true); // Must be true or the animation will reset

        v.startAnimation(rotateAnim);


    }


}
