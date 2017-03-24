package org.cbccessence.mobihealth.adapter;

/**
 * Created by aangjnr on 28/02/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.model.Content;

import java.util.List;
import java.util.Locale;


/**
 * Created by aangjnr on 14/02/2017.
 */

public class VisualAidsImagesAdapter extends RecyclerView.Adapter<VisualAidsImagesAdapter.LcReferencesViewHolder>{

    private Context context;
    private List<Content> imageFiles;

    VisualAidsImagesAdapter.OnItemClickListener mItemClickListener;

    public VisualAidsImagesAdapter(Context ctx, List<Content> imageFiles){
        this.context = ctx;
        this.imageFiles = imageFiles;

    }

    @Override
    public VisualAidsImagesAdapter.LcReferencesViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.visual_aids_image_itemview, parent, false);



        return new VisualAidsImagesAdapter.LcReferencesViewHolder(v);
    }



    @Override

    public void onBindViewHolder(VisualAidsImagesAdapter.LcReferencesViewHolder holder, int position){

        String file_name;
        Content imageFile = imageFiles.get(position);


        file_name = imageFile.getDocName().toUpperCase(Locale.US);
        int pos = file_name.lastIndexOf(".");

        if(pos == -1)
            holder.file_name.setText(file_name);
        else
            holder.file_name.setText(file_name.substring(0, pos));



        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getDocLoc());
        holder.image.setImageBitmap(bitmap);





    }

    @Override
    public int getItemCount(){
        return imageFiles.size();
    }



    public class LcReferencesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;

        TextView file_name;

         RelativeLayout refLayout;

        LcReferencesViewHolder(View itemView){
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            //no_preview = (ImageView) itemView.findViewById(R.id.no_preview);
            file_name = (TextView) itemView.findViewById(R.id.image_name);

            refLayout = (RelativeLayout) itemView.findViewById(R.id.ref_layout);

            refLayout.setOnClickListener(this);

        }


        @Override
        public void onClick(View v){

            if(mItemClickListener != null) mItemClickListener.onItemClick(itemView, getAdapterPosition());


        }
    }


    public interface OnItemClickListener{

        void onItemClick(View v, int position);

    }


    public void setOnItemClickListener(final VisualAidsImagesAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }







}