/**
 * @author Vishnu Indukuri
 * @since 12/18/2013
 * @version 0.01
 * Licensed under the MIT License 
 */
package indukuri.apps101.cameradrawsample;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/** 
 * I encourage you to check out the full documentation for a SurfaceView at TODO
 * but I shall provide a quick non-exhaustive explanation here.  Basically a 
 * SurfaceView is a View that allows us to draw on it however we like.  So 
 * our approach to showing a preview of the camera on the screen is to put a 
 * custom SurfaceView called CameraPreview (See TODO for creating custom view.  
 * TL;DR Just subclass a view and override methods whose behavior you want to 
 * customize) in our layout xml file, getting a reference to it, and connecting 
 * it to the camera via camera.setPreviewDisplay(holder) (line 54).  The holder
 * is a SurfaceHolder.  You see, managing a SurfaceView is hard tedious work so we
 * let the OS do that via a SurfaceHolder.  SurfaceHolders come automatically with
 * SurfaceViews and we do not need to configure it in anyway.  We need only 
 * implement the SurfaceHolder.Callback interface which will notify us when the 
 * SurfaceView has been created, changed (user switched screen orientation etc.), 
 * and destroyed.
*/
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback  {
    private SurfaceHolder mHolder;
    private final String TAG = "CameraDrawingSampleApp" ;

    @SuppressWarnings("deprecation")
	public CameraPreview(Context context) {
        super(context);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    // Callback methods.  These get called when specific events happen in the
    // the lifecycle of the preview surface.
    // ------------------------------------------------------------------
    public void surfaceCreated(SurfaceHolder holder) {
    	// Open camera 
		CameraActivity.mCamera = Camera.open();            
		CameraActivity.mCamera.setDisplayOrientation(90);
		
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
			CameraActivity.mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
        CameraActivity.mCamera.startPreview();
    }
    public void surfaceDestroyed(SurfaceHolder holder) {
    	// When user leaves the screen, the camera MUST be released.  If
    	// not it will not be available to any other app on the phone.
    	// User no like camera no work.  User like take selfies.
    	if(CameraActivity.mCamera == null) return; 
    	CameraActivity.mCamera.release();
    	CameraActivity.mCamera = null;
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
        	CameraActivity.mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
        	CameraActivity.mCamera.setPreviewDisplay(mHolder);
        	CameraActivity.mCamera.startPreview();
        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}
