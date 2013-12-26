/**
 * @author Vishnu Indukuri
 * @since 12/18/2013
 * @version 0.01
 * Licensed under the MIT License.  See the LICENSE file for more details.
 */
package indukuri.apps101.cameradrawsample;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
/**
 * The main activity of this project.  
 */
public class CameraActivity extends Activity {
	private FrameLayout layout ;
	private PictureSaver saver ;
	
	// May seem like bad programming practice but we highly recommend
	// (by us and Google) that only a single instance of this object 
	// ever exist and all classes use that specific instance.  Using 
	// the camera is a very strict state machine.
	public static Camera mCamera ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Ask to hide the Action Bar. This request must be made before setting 
		// the content of the activity or else an exception
		// will be thrown at runtime
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Dim the system UI 
		hideSystemNavigation();
		// Set the content
		setContentView(R.layout.activity_camera);
		
		// To get rid of the status bar, we use a xml feature
		// in the AndroidManifest.xml file.  Check out the 
		// android:theme under <application>
		
		// Get a reference to the layout so that we can add the 
		// preview
		layout = (FrameLayout) findViewById(R.id.camera_preview);
		
		// Add the camera preview
		layout.addView(new CameraPreview(this));
		
		// Instantiate our PictureSaver helper class
		saver = new PictureSaver(this);
	}
	
	// This is a neat little Android trick. In the XML layout file for this Activity,
	// we added an attribute called android:onClick="capturePicture" to the capture_button.  
	// That is all android needs to call this method when that button is pressed.  All you
	// need to do is make sure the method names match and the signature is 
	// public void whateveryouputinquotes(View view).  The View that is passed along to this
	// method is the one that was touched/clicked.  This saves us the trouble of creating
	// onClickListeners
	// ------------------------------------------------------------------------------------
	public void capturePicture(View view){
		mCamera.takePicture(null, null, saver.getSaver());
	}
	
	// Utility methods dealing with the camera
	// ------------------------------------------------------------------------------------
	private void hideSystemNavigation(){
		// This example uses decor view, but you can use any visible view.
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
		decorView.setSystemUiVisibility(uiOptions);
	}
}
