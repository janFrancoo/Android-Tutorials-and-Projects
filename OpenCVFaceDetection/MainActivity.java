package com.janfranco.opencvcamview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private final int CAM_PERM = 9;

    private Mat mRgba, mGray;
    private CameraBridgeViewBase camView;
    private CascadeClassifier faceDetector;
    private int absoluteFaceSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @SuppressWarnings("all")
    private void init() {
        OpenCVLoader.initDebug();
        camView = (CameraBridgeViewBase) findViewById(R.id.camView);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.CAMERA }, CAM_PERM);
        } else
            accessCamera();

        try {
            InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            File cascadeFile = new File(cascadeDir, "lpbcascade_frontalface.xml");
            FileOutputStream os = new FileOutputStream(cascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while((bytesRead = is.read(buffer)) != -1)
                os.write(buffer, 0, bytesRead);

            is.close();
            os.close();

            faceDetector = new CascadeClassifier(cascadeFile.getAbsolutePath());
            cascadeDir.delete();
        } catch(IOException e) { e.printStackTrace(); }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAM_PERM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                accessCamera();
            else
                finish();
        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void accessCamera() {
        camView.enableView();
        camView.setCameraPermissionGranted();
        camView.setCameraIndex(1);
        camView.setVisibility(CameraBridgeViewBase.VISIBLE);
        camView.setCvCameraViewListener(this);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
        mGray = new Mat();
        absoluteFaceSize = Math.round(height * 0.2f);
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
        mGray.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        Core.flip(mGray, mGray, 1);
        Core.flip(mRgba, mRgba, 1);

        MatOfRect faces = new MatOfRect();
        faceDetector.detectMultiScale(mGray, faces, 1.1, 2, 2,
                new Size(absoluteFaceSize, absoluteFaceSize), new Size());

        Rect[] facesArr = faces.toArray();
        for (Rect rect : facesArr)
            Imgproc.rectangle(mRgba, rect.tl(), rect.br(), new Scalar(0, 255, 0, 255), 3);

        return mRgba;
    }
}
