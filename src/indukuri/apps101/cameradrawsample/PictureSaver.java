/**
 * @author Vishnu Indukuri
 * @since 12/18/2013
 * @version 0.01
 * Licensed under the MIT License 
 */
package indukuri.apps101.cameradrawsample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class PictureSaver {
	private static final String TAG = "CameraDrawingSample";
	private static final String STORAGE_PROBLEM = "Sorry.  Problem saving picture.";
	private static final String SDCARD_PROBLEM = "Sorry.  SD Card unmounted.";
	private static final int MEDIA_TYPE_IMAGE = 1;
	private Context mContext;
	
	
	public PictureSaver(Context context){
		mContext = context;
	}
	public PictureCallback getSaver(){
		return mPicture;
	}
	
	private PictureCallback mPicture = new PictureCallback() {
	    @Override
	    public void onPictureTaken(byte[] data, Camera camera) {
	    	// Get a file somewhere on SD Card where we can share 
	    	// our picture with other apps
	        File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
	        if (pictureFile == null){
	            Log.d(TAG, "Error creating media file, check storage permissions: ");
	            return;
	        }

	        // write the picture data to the file
	        try {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            fos.write(data);
	            fos.close();
	        } catch (FileNotFoundException e) {
	            Log.d(TAG, "File not found: " + e.getMessage());
	        } catch (IOException e) {
	            Log.d(TAG, "Error accessing file: " + e.getMessage());
	        }
	        
	        // Share our picture with the device gallery 
	        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	        Uri contentUri = Uri.fromFile(pictureFile);
	        mediaScanIntent.setData(contentUri);
	        mContext.sendBroadcast(mediaScanIntent);
	    }
	};
	
	/** Create a File for saving an image or video */
	@SuppressLint("SimpleDateFormat")
	private File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			Toast.makeText(mContext, SDCARD_PROBLEM, Toast.LENGTH_LONG);
			return null;
		}

		// This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.
	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "DrawCameraSample");

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	        	Toast.makeText(mContext, STORAGE_PROBLEM , Toast.LENGTH_LONG).show();
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    return new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
	}

}
