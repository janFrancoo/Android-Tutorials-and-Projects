package com.janfranco.voicerecordexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private String fileName;
    private MediaPlayer player;
    private MediaRecorder recorder;
    private boolean isRecording, isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();
    }

    private void checkPermissions() {
        List<String> permissionsNeeded = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            permissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (!permissionsNeeded.isEmpty())
            ActivityCompat.requestPermissions(this,
                    permissionsNeeded.toArray(new String[permissionsNeeded.size()]), 1);
        else
            init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0) {
                for (int i=0; i<grantResults.length; i++)
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "Permission error", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                init();
            }
        }
    }

    private void init() {
        isRecording = false;
        isPlaying = false;

        final Button recordButton = findViewById(R.id.record_button);
        final Button playButton = findViewById(R.id.play_button);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) {
                    startRecording();
                    recordButton.setText(R.string.button_stop);
                }
                else {
                    stopRecording();
                    recordButton.setText(R.string.button_start);
                    playButton.setEnabled(true);
                }

                isRecording = !isRecording;
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    playRecord();
                    playButton.setText(R.string.button_stop);

                } else {
                    stopRecord();
                    playButton.setText(R.string.button_play);
                }

                isPlaying = !isPlaying;
            }
        });
    }

    private void startRecording() {
        fileName = Objects.requireNonNull(getExternalCacheDir()).getAbsolutePath();
        fileName += "/janfranco_test.mp3";

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(fileName);

        try {
            recorder.prepare();
        } catch (IOException exception) {
            Log.d("RECORD_ACT", Objects.requireNonNull(exception.getLocalizedMessage()));
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
    }

    private void playRecord() {
        player = new MediaPlayer();

        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException exception) {
            Log.e("RECORD_ACT", Objects.requireNonNull(exception.getLocalizedMessage()));
        }
    }

    private void stopRecord() {
        player.release();
    }

}
