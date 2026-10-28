package com.example.awake_mobdev_mco;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class activity_search_object extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 101;
    private PreviewView cameraPreview;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private Button takePicBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_object);

        cameraPreview = findViewById(R.id.cameraPreview);
        takePicBtn = findViewById(R.id.takePicBtn);

        takePicBtn.setOnClickListener(v -> {
            Intent intent = new Intent(activity_search_object.this, activity_shake_challenge.class); //CHANGE THIS IF NAGAWA NA NI THEO PART NIYA
            startActivity(intent);
        });


        // Check for runtime camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }


    }

    private void startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Error starting camera: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        // Unbind any previous use cases before binding new ones
        cameraProvider.unbindAll();

        // Configure the Preview component
        Preview preview = new Preview.Builder().build();

        // Set the surface provider on the UI component
        preview.setSurfaceProvider(cameraPreview.getSurfaceProvider());

        // Select the back camera as default
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        // Bind the preview use case to the lifecycle of this Activity
        cameraProvider.bindToLifecycle(this, cameraSelector, preview);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
