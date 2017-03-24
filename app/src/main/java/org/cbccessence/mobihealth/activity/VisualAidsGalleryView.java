package org.cbccessence.mobihealth.activity;

import java.io.File;

import org.cbccessence.mobihealth.R;






import org.cbccessence.mobihealth.adapter.PinchZoom;
import org.cbccessence.mobihealth.application.BaseActivity;
import org.cbccessence.mobihealth.application.TouchImageView;
import org.cbccessence.mobihealth.application.Utils;
import org.cbccessence.mobihealth.database.MobiHealthDatabaseHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class VisualAidsGalleryView extends BaseActivity {

	private int position;
	private TouchImageView imageView;
	private Bitmap bitmap;

	String image_loc;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

		image_loc = getIntent().getExtras().getString("image_location");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window w = getWindow(); // in Activity's onCreate() for instance
			w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

		if (getSupportActionBar()!= null) getSupportActionBar().hide();

	    setContentView(R.layout.visual_aids_gallery_view);

	    imageView = (TouchImageView) findViewById(R.id.imageView1);



		bitmap = getScaledBitmap(image_loc,
				Utils.getScreenWidth(this), Utils.getScreenHeight(this));

		imageView.setImageBitmap(bitmap);






	    
	}



	private Bitmap getScaledBitmap(String picturePath, int width, int height) {
		BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
		sizeOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(picturePath, sizeOptions);

		int inSampleSize = calculateInSampleSize(sizeOptions, width, height);

		sizeOptions.inJustDecodeBounds = false;
		sizeOptions.inSampleSize = inSampleSize;

		return BitmapFactory.decodeFile(picturePath, sizeOptions);
	}

	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
}
