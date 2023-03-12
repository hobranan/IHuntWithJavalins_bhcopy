package com.example.ihuntwithjavalins.QRCode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Map.CodeRefOpenStreetMapActivity;
import com.example.ihuntwithjavalins.Camera.PhotoViewActivity;
import com.example.ihuntwithjavalins.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class QRCodeImageViewActivity extends AppCompatActivity {
    ImageButton backButton;
    Button photoButton;
    Button geoButton;
    TextView codeName;
    TextView codeHash;
    TextView codePoints;

    ImageView codePicImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.code_image_view);

        backButton = findViewById(R.id.civ_go_back);
        photoButton = findViewById(R.id.civ_show_attached);
        geoButton = findViewById(R.id.civ_no_geo_btn);

        codeName = findViewById(R.id.civ_qr_code_name);
        codeHash = findViewById(R.id.civ_hash_code_);
        codePoints = findViewById(R.id.civ_total_points);
        codePicImage = findViewById(R.id.civ_display_img);

        Bundle extras = getIntent().getExtras();
        String savedCodeName = extras.getString("imageSavedCodeName");//The key argument here must match that used in the other activity
        String savedCodeHash = extras.getString("imageSavedCodeHash");//The key argument here must match that used in the other activity
        String savedCodePoints = extras.getString("imageSavedCodePoints");//The key argument here must match that used in the other activity
        String savedCodeImageRef = extras.getString("imageSavedCodeImageRef");//The key argument here must match that used in the other activity
        String savedCodeLat = extras.getString("imageSavedCodeLat");//The key argument here must match that used in the other activity
        String savedCodeLon = extras.getString("imageSavedCodeLon");//The key argument here must match that used in the other activity
        String savedCodePhotoRef = extras.getString("imageSavedCodePhotoRef");//The key argument here must match that used in the other activity
        codeName.setText(savedCodeName);
        codeHash.setText(savedCodeHash);
        codePoints.setText(savedCodePoints);

        // Get a non-default Storage bucket (https://console.firebase.google.com/u/1/project/ihuntwithjavalins-22de3/storage/ihuntwithjavalins-22de3.appspot.com/files/~2F)
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://ihuntwithjavalins-22de3.appspot.com/");
        // Create a storage reference from our app (https://firebase.google.com/docs/storage/android/download-files)
        StorageReference storageRef = storage.getReference();
        // Create a reference with an initial file path and name // use QRcode-object's imgRef string to ref storage
        String codePicRef = "GendImages/" + savedCodeImageRef;
        StorageReference pathReference_pic = storageRef.child(codePicRef);


        // convert pathRef_pic to bytes, then set image bitmap via bytes (https://firebase.google.com/docs/storage/android/download-files)
        //final long ONE_MEGABYTE = 1024 * 1024;
        final long ONE_POINT_FIVE_MEGABYTE = 1536 * 1536; // made this to get the .getBytes() limit larger (all pics are less than 1.5MB)
        pathReference_pic.getBytes(ONE_POINT_FIVE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                codePicImage.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!savedCodePhotoRef.equals("")){
                    Intent intent = new Intent(QRCodeImageViewActivity.this, PhotoViewActivity.class);
                    intent.putExtra("imageSavedCodePhotoRef", savedCodePhotoRef);
                    startActivity(intent);
                }
            }
        });

        geoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!savedCodeLat.equals("") && !savedCodeLon.equals("") ){
                    Intent intent = new Intent(QRCodeImageViewActivity.this, CodeRefOpenStreetMapActivity.class);
                    intent.putExtra("imageSavedCodeLat", savedCodeLat);
                    intent.putExtra("imageSavedCodeLon", savedCodeLon);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(QRCodeImageViewActivity.this, CodeRefOpenStreetMapActivity.class);
                    intent.putExtra("imageSavedCodeLat", String.valueOf(53.52670));
                    intent.putExtra("imageSavedCodeLon", String.valueOf(-113.52895));
                    startActivity(intent);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

}
