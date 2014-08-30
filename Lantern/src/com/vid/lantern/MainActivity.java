package com.vid.lantern;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
