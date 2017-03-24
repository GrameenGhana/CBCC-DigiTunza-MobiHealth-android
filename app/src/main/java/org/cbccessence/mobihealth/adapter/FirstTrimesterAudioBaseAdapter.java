package org.cbccessence.mobihealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.model.Content;

import java.util.List;

public class FirstTrimesterAudioBaseAdapter extends BaseAdapter {
    private final List<Content> audios;
    private LayoutInflater mInflater;
    private Context mContext;


    public FirstTrimesterAudioBaseAdapter(Context c, List<Content> audios) {
        this.mContext = c;
        this.audios = audios;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return audios.size();
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
        View grid;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.audio_menu_listview, null);


        } else {
            grid = (View) convertView;
        }

        TextView textView = (TextView) grid.findViewById(R.id.textView1);
        ImageView imageView = (ImageView) grid.findViewById(R.id.imageView1);
        imageView.setImageResource(R.drawable.ic_audiotrack_black_24dp);
        textView.setText(audios.get(position).getDocName());

        return grid;
    }

}
