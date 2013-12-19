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

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
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

    // Callback methods
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
