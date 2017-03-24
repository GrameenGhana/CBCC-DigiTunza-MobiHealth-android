package org.cbccessence.mobihealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.model.Attendee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aangjnr on 01/03/2017.
 */

public class AttendeeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    List<Attendee> attendees;


    public AttendeeAdapter(Context c, List<Attendee> attendees) {
        this.mContext = c;
        this.attendees = attendees;
         mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {

        return attendees.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = new View(mContext);
            itemView = inflater.inflate(R.layout.attendee_itemview, null);


        } else {
            itemView = (View) convertView;
        }

        TextView textView = (TextView) itemView.findViewById(R.id.attendee_name);
        textView.setText(attendees.get(position).getAttendeeName());

        return itemView;
    }

}
