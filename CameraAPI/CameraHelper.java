package com.janfranco.cameraapiexample.helpers;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;

import java.util.Objects;

public class CameraHelper {

    public static boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public static Camera getFrontCameraInstance() {
        Camera camera = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int cameraCount = Camera.getNumberOfCameras();

        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    camera = Camera.open(i);
                } catch (Exception e) {
                    Log.d("CAM_ACT", Objects.requireNonNull(e.getMessage()));
                }
            }
        }

        return camera;
    }

}
