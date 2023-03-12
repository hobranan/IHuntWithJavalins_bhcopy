package com.example.ihuntwithjavalins.Camera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ihuntwithjavalins.QRCode.QRCodeLibraryActivity;
import com.example.ihuntwithjavalins.QRCode.QRCodeViewActivity;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;

public class CameraScanActivity extends AppCompatActivity {

        private SurfaceView surfaceView; // box of live camera overlay
        private BarcodeDetector barcodeDetector;
        private CameraSource cameraSource;
        private static final int REQUEST_CAMERA_PERMISSION = 201;
        private ToneGenerator toneGen1;//This class provides methods to play DTMF tones
        private TextView barcodeText;
        private String barcodeData;
        public static int cameraFlag;


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
                                        if (ActivityCompat.checkSelfPermission(CameraScanActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                                cameraSource.start(surfaceView.getHolder());
                                        } else {
                                                ActivityCompat.requestPermissions(CameraScanActivity.this, new
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
//                                        Intent intent = new Intent(CameraScanActivity.this, CameraAnalyzeScannedActivity.class);
                                        Intent intent = new Intent(CameraScanActivity.this, CameraCaughtNewActivity.class); //testing
                                        intent.putExtra("cameraSavedCodeHash", thisCode.getCodeHash());
                                        intent.putExtra("cameraSavedCodeName", thisCode.getCodeName());
                                        intent.putExtra("cameraSavedCodePoints", thisCode.getCodePoints());
                                        intent.putExtra("cameraSavedCodeImageRef", thisCode.getCodeGendImageRef());

//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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

    /*
    (<-BACK)
    (Camera app open and scanning for barcodes and qr codes)
    "Via Camera...Scan the code"
     */

    /*
    "Hmm... a familiar face"
    Name: Shimmering Rock Reptile
    Points: 445
    "You have this one. Keep trying for new ones!"
    Total Points: 6,058
    Total Codes: 4
    (Continue Button)
     */

    /*
    "Hmm... a familiar face"
    Name: Shimmering Rock Reptile
    (Picture)
    Points: 445
    "You have this one. Keep trying for new ones!"
    Total Points: 6,058
    Total Codes: 4
    Continue Button
    */

    /*
    "Gotcha!"
    Hashcode: dfgsdgadfg324131421412s3e44r23e42r
    Name: Shimmering Rock Reptile
    (Picture)
    Points: "+" 445 "!!!"
    "You have this one. Keep trying for new ones!"
    Total Points: 6,058
    Total Codes: 4
    (Photo ask checkbox)    (Photo Button)
    (Geolocation ask checkbox)    (Geolocation Button)
    (Continue Button)
    */

    /*
    (<-BACK)
    (Camera app open to take photo)
    "Via Camera...Take photo"
    */

    /*
    (Quick Nav Button)
    */