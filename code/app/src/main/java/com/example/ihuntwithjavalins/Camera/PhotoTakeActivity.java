package com.example.ihuntwithjavalins.Camera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QRCode.QRCodeViewActivity;
import com.example.ihuntwithjavalins.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
/**
 * Responsible for capturing an image from the camera and detecting any barcodes or QR codes in the image.
 * Uses the cameraSource from the Google Vision API and the BarcodeDetector.
 * Extracts the code information and launches another activity to display it.
 * Includes a private method to initialise the camera and detector.
 */
public class PhotoTakeActivity extends AppCompatActivity {

        private SurfaceView surfaceView; // box of live camera overlay
        private BarcodeDetector barcodeDetector;
        private CameraSource cameraSource;
        private static final int REQUEST_CAMERA_PERMISSION = 201;
        private ToneGenerator toneGen1;//This class provides methods to play DTMF tones
        private TextView barcodeText;
        private String barcodeData;
        public static int cameraFlag;

        /**
         * Overrides the onCreate method of AppCompatActivity to set the layout and initialise the detectors and sources.
         * Also sets the tone generator and camera flag variables.
         * @param savedInstanceState saved instance state bundle
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.camera_barcode);
                toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 50);
                surfaceView = findViewById(R.id.surface_view);
                barcodeText = findViewById(R.id.barcode_text);
                cameraFlag = 0;
                initialiseDetectorsAndSources();
        }
        /**
         * Initialises the barcode detector and camera source by creating new instances of them and setting their properties.
         * Sets the callback for the surface view to start and stop the camera preview.
         */
        private void initialiseDetectorsAndSources() {
                //Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
                barcodeDetector = new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.ALL_FORMATS)
                        .build();
                cameraSource = new CameraSource.Builder(this, barcodeDetector)
                        .setRequestedPreviewSize(1920, 1080)
                        .setAutoFocusEnabled(true) //you should add this feature
                        .build();
                surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                        @Override
                        public void surfaceCreated(SurfaceHolder holder) {
                                try {
                                        if (ActivityCompat.checkSelfPermission(PhotoTakeActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                                cameraSource.start(surfaceView.getHolder());
                                        } else {
                                                ActivityCompat.requestPermissions(PhotoTakeActivity.this, new
                                                        String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                                        }
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }

                        }
                        @Override
                        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                        }
                        @Override
                        public void surfaceDestroyed(SurfaceHolder holder) {
                                cameraSource.stop();
                        }
                });

                barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                        @Override
                        public void release() {
                                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void receiveDetections(Detector.Detections<Barcode> detections) {
                                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                                if (barcodes.size() != 0) {
                                        barcodeText.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                        if (barcodes.valueAt(0).email != null) {
                                                                barcodeText.removeCallbacks(null);
                                                                barcodeData = barcodes.valueAt(0).email.address;
                                                                barcodeText.setText(barcodeData);
                                                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                                        } else {
                                                                barcodeData = barcodes.valueAt(0).displayValue;
                                                                barcodeText.setText(barcodeData);
                                                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                                        }
                                                }
                                        });
                                        QRCode thisCode = new QRCode(barcodeText.getText().toString());
//                                        Intent intent = new Intent(CameraActivity.this, MainActivity.class);
                                        Intent intent = new Intent(PhotoTakeActivity.this, QRCodeViewActivity.class);
                                        intent.putExtra("cameraSavedCodeText",barcodeText.getText().toString());
                                        intent.putExtra("cameraSavedCodeHash",thisCode.getCodeHash());
                                        intent.putExtra("cameraSavedCodeName",thisCode.getCodeName());
                                        intent.putExtra("cameraSavedCodePoints",thisCode.getCodePoints());
                                        intent.putExtra("cameraSavedCodeImageRef",thisCode.getCodeGendImageRef());
                                        cameraFlag = 1;
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                }
                        }
                });
        }

        @Override
        protected void onPause() {
                super.onPause();
                getSupportActionBar().hide();
                cameraSource.release();
        }
        @Override
        protected void onResume() {
                super.onResume();
                getSupportActionBar().hide();
                initialiseDetectorsAndSources();
        }


}