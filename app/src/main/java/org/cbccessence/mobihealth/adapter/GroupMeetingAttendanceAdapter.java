package org.cbccessence.mobihealth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.activity.BottomSheet3DialogFragment;
import org.cbccessence.mobihealth.application.ExpandableItemLayout;
import org.cbccessence.mobihealth.database.MobiHealthDatabaseHandler;
import org.cbccessence.mobihealth.model.Attendee;
import org.cbccessence.mobihealth.model.GroupMeeting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aangjnr on 01/03/2017.
 */

public class GroupMeetingAttendanceAdapter extends RecyclerView.Adapter<GroupMeetingAttendanceAdapter.GroupMeetingAttendanceViewHolder> {

    MobiHealthDatabaseHandler databaseHandler;
    List<Attendee> attendees;
    private Context context;
    OnItemClickListener mItemClickListener;



    public GroupMeetingAttendanceAdapter(Context c, List<Attendee> attendees){
        this.context = c;
        this.attendees = attendees;
        databaseHandler = new MobiHealthDatabaseHandler(context);

    }

    @Override
    public GroupMeetingAttendanceViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_attendees_itemview, viewGroup, false);

        return new  GroupMeetingAttendanceViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final GroupMeetingAttendanceAdapter.GroupMeetingAttendanceViewHolder viewHolder, final int position) {
        Attendee attendee = attendees.get(position);

        viewHolder.attendee_name.setText(attendee.getAttendeeName());

        if(databaseHandler.doesAttendeeExist(attendee.getAttendeeName(), BottomSheet3DialogFragment.meeting_uid))
         viewHolder.checkBox.setChecked(true);

            else viewHolder.checkBox.setChecked(false);



    }



    public class GroupMeetingAttendanceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView attendee_name;
        RelativeLayout relLay;
        CheckBox checkBox;


        GroupMeetingAttendanceViewHolder(View itemView) {
            super(itemView);

            attendee_name = (TextView) itemView.findViewById(R.id.attendeeName_textView);
            relLay = (RelativeLayout) itemView.findViewById(R.id.attendance_relLay);
            checkBox = (CheckBox) itemView.findViewById(R.id.attendee_checkBox);


            relLay.setOnClickListener(this);
         }




    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(itemView, getAdapterPosition());


        }

    }
}


    public void setOnItemClickListener(final GroupMeetingAttendanceAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


public interface OnItemClickListener {
    void onItemClick(View view, int position);
}


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return attendees.size();

    }
}
