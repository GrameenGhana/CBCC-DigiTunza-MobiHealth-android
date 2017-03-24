package org.cbccessence.mobihealth.application;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by aangjnr on 28/02/2017.
 */
public class GridSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int mSpace;

    public GridSpacesItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


        //outRect.bottom = mSpace/2;

        // Add top margin only for the first item to avoid double space between items

        if (parent.getChildAdapterPosition(view) % 3 == 0) {
            outRect.left = mSpace;
            outRect.top = mSpace;
            outRect.right = mSpace;


        }else{

            outRect.top = mSpace;
            outRect.right = mSpace;

        }


    }

}