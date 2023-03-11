package com.example.ihuntwithjavalins.QRCode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class QRCodeImageViewActivity extends AppCompatActivity {

    ImageButton backButton;
    TextView codeName;
    TextView codeHash;
    TextView codePoints;
    TextView BACK_2_string; // testing
    String codePicRef = new String();
    ImageView codePicImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.code_image_view);

        backButton = findViewById(R.id.civ_go_back);

        codeName = findViewById(R.id.civ_qr_code_name);
        codeHash = findViewById(R.id.civ_hash_code_);
        codePoints = findViewById(R.id.civ_total_points);
        codePicImage = findViewById(R.id.civ_display_img);

        BACK_2_string = findViewById(R.id.civ_back_text); // testing

        Bundle extras = getIntent().getExtras();
        String savedCodeName = extras.getString("cameraSavedCodeName");//The key argument here must match that used in the other activity
        String savedCodeHash = extras.getString("cameraSavedCodeHash");//The key argument here must match that used in the other activity
        String savedCodePoints = extras.getString("cameraSavedCodePoints");//The key argument here must match that used in the other activity
        String savedCodeImageRef = extras.getString("cameraSavedCodeImageRef");//The key argument here must match that used in the other activity
        codeName.setText(savedCodeName);
        codeHash.setText(savedCodeHash);
        codePoints.setText(savedCodePoints);

        BACK_2_string.setText(savedCodeImageRef); // testing
        codePicRef = savedCodeImageRef;

        // Get a non-default Storage bucket (https://console.firebase.google.com/u/1/project/ihuntwithjavalins-22de3/storage/ihuntwithjavalins-22de3.appspot.com/files/~2F)
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://ihuntwithjavalins-22de3.appspot.com/");
        // Create a storage reference from our app (https://firebase.google.com/docs/storage/android/download-files)
        StorageReference storageRef = storage.getReference();
        // Create a reference with an initial file path and name // use QRcode-object's imgRef string to ref storage
        StorageReference pathReference_pic = storageRef.child("picture_1-min.png");
        if (!codePicRef.equals("")) {
            pathReference_pic = storageRef.child(savedCodeImageRef);
        }


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


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
