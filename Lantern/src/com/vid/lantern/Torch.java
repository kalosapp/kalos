package com.vid.lantern;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;

public class Torch {

	public boolean checkFlash(Context ctx) {

		boolean hasFlash;

		hasFlash = ctx.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA_FLASH);

		if (hasFlash)
			return true;

		return false;
	}

	public Camera getCamera() {

		Camera camera = null;

		if (camera == null) {
			try {
				camera = Camera.open();
			} catch (RuntimeException e) {
				Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
				return null;
			}
		}
		return camera;
	}

	public boolean turnFlashOn(Camera camera) {

		Parameters params;

		try {
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(params);
			camera.startPreview();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean turnFlashOff(Camera camera) {

		Parameters params;

		try {
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(params);
			camera.stopPreview();
			releaseCamera(camera);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	protected void releaseCamera(Camera camera) {

		if (camera != null) {
			camera.release();
			camera = null;
		}
	}
}