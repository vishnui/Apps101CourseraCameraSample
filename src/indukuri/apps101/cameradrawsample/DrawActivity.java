/**
 * @author Vishnu Indukuri
 * @since 12/20/2013
 * @version 0.02
 * Licensed under the MIT License.  See the LICENSE file for more details.
 */
package indukuri.apps101.cameradrawsample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;

/**
 * This is the drawing Activity.  This Activity lets
 * the user draw whatever he or she likes on top of the 
 * picture that was just taken.
 */
public class DrawActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_draw);
		
		// Get the raw picture data and path to file on External
		// storage.
		byte[] picture = getIntent().getByteArrayExtra("picture");
		String picureFilePath = getIntent().getStringExtra("pictureFileName");
		
//		Bitmap picture_taken = Bitmap.createBitmap(picture, width, height, config)
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, CameraActivity.class);
		startActivity(intent);
	}
}
