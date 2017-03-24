package org.cbccessence.mobihealth.adapter;

/**
 * Created by aangjnr on 28/02/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.application.MobiHealth;
import org.cbccessence.mobihealth.model.Content;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Locale;


/**
 * Created by aangjnr on 14/02/2017.
 */

public class VisualAidsDocumentAdapter extends RecyclerView.Adapter<VisualAidsDocumentAdapter.LcReferencesViewHolder>{

    private Context context;
    private List<Content> documents;

    VisualAidsDocumentAdapter.OnItemClickListener mItemClickListener;

    public VisualAidsDocumentAdapter(Context ctx, List<Content> documents){
        this.context = ctx;
        this.documents = documents;

    }

    @Override
    public VisualAidsDocumentAdapter.LcReferencesViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.references_item_view, parent, false);


        return new VisualAidsDocumentAdapter.LcReferencesViewHolder(v);
    }




    @Override

    public void onBindViewHolder(VisualAidsDocumentAdapter.LcReferencesViewHolder holder, int position){

        String file_name, loc;
        String file_name_no_ext = null;

        Content reference = documents.get(position);


        file_name = reference.getDocName().toUpperCase(Locale.US);
        loc = reference.getDocLoc();
        int pos = file_name.lastIndexOf(".");



        if(pos != -1) file_name_no_ext = file_name.substring(0, pos);

            holder.file_name.setText(file_name_no_ext);



        File thumbnail = new File(MobiHealth.ROOT_DIR + File.separator + MobiHealth.VA_DOC_DIR + File.separator + "thumbnails/"+ file_name_no_ext + ".png");
        if(thumbnail.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(thumbnail.toString());
            holder.file_image.setImageBitmap(bitmap);

        }else{
            if (generateImageFromPdf(Uri.parse( "file:///" + loc), file_name_no_ext)){
                 Bitmap bitmap = BitmapFactory.decodeFile(thumbnail.toString());
                holder.file_image.setImageBitmap(bitmap);

            }




        }



    }

    @Override
    public int getItemCount(){
        return documents.size();
    }



    public class LcReferencesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView file_image;
        ImageView no_preview;
        ImageView download;

        TextView file_name;
        TextView file_size;

        RelativeLayout downloadPlaceHolder;
        LinearLayout refLayout;

        LcReferencesViewHolder(View itemView){
            super(itemView);
            file_image = (ImageView) itemView.findViewById(R.id.file_image);
            //no_preview = (ImageView) itemView.findViewById(R.id.no_preview);
             file_name = (TextView) itemView.findViewById(R.id.filename);

            refLayout = (LinearLayout) itemView.findViewById(R.id.ref_layout);

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


    public void setOnItemClickListener(final VisualAidsDocumentAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }



    public boolean generateImageFromPdf(Uri pdfUri, String pdfThumbnailName) {


        Log.i("Doc Adapter", "URI is " + pdfUri + " and name of file is " + pdfThumbnailName + ".png");


        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(context);
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = context.getApplicationContext().getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);

            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            saveImage(bmp, pdfThumbnailName);
            pdfiumCore.closeDocument(pdfDocument); // important!
            Log.i("Doc Adapter", "Thumbnail Generated!");
            return true;
        } catch(Exception e) {
            //todo with exception
            e.printStackTrace();
            Log.i("Doc Adapter", "Something happened!");
            return false;
        }
    }

    private void saveImage(Bitmap bmp, String thumbnailName) {
        FileOutputStream out = null;

        String FOLDER = MobiHealth.ROOT_DIR + File.separator + MobiHealth.VA_DOC_DIR + File.separator + "thumbnails";


        Log.i("Doc Adapter", "Thumbnail folder location is " + FOLDER);

        try {
            File folder = new File(FOLDER);
            if(!folder.exists())
                folder.mkdirs();
            File file = new File(folder + File.separator , thumbnailName + ".png");

            Log.i("Doc Adapter", "Thumbnail will be saved as  " + file);
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance

            Log.i("Doc Adapter", "Thumbnail successfully saved! with name " + thumbnailName);
        } catch (Exception e) {
            //todo with exception
            e.printStackTrace();
            Log.i("Doc Adapter", "Thumbnail cound not save :(" );
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                //todo with exception
            }
        }
    }



}