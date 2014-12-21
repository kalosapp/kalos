package com.vid.lantern;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button btnSwitch;
	TextView flashError;
	RelativeLayout relativeLayout;
	boolean isFlashOn;
	Camera camera;
	Torch torch = new Torch();

	public static final String FLASH_ERROR_MESSAGE = "Flashlight not found";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// flash switch button
		btnSwitch = (Button) findViewById(R.id.btnSwitch);
		flashError = (TextView) findViewById(R.id.flashError);
		relativeLayout = (RelativeLayout) findViewById(R.id.rlmain);
		
		if (!torch.checkFlash(getApplicationContext())) {
			flashError.setText(FLASH_ERROR_MESSAGE);
			btnSwitch.setEnabled(false);
		}

		btnSwitch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isFlashOn) {
					v.setSelected(false);
					isFlashOn = false;
					turnOffFlash();
				} else {
					v.setSelected(true);
					turnOnFlash();
					isFlashOn = true;
				}
			}
		});
	}

	public void turnOffFlash() {
		torch.turnFlashOff(camera);
	}

	public void turnOnFlash() {

		camera = torch.getCamera();
		torch.turnFlashOn(camera);
	}

	@Override
	protected void onPause() {
		super.onPause();
		torch.releaseCamera(camera);
		if(isFlashOn)
		btnSwitch.performClick();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
		torch.releaseCamera(camera);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		torch.releaseCamera(camera);
	}

}
