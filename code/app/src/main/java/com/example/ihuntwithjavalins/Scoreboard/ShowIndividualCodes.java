package com.example.ihuntwithjavalins.Scoreboard;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.R;

import java.util.ArrayList;
/**
 * ShowIndividualCodes is responsible for showing the details of each QRCode
 */
public class ShowIndividualCodes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_main_individual);

        TextView user_name = findViewById(R.id.userName);
        String UserName = getIntent().getStringExtra("USER");
        Toast.makeText(this, UserName, Toast.LENGTH_SHORT).show();

        ArrayList<StoreNamePoints> storageList = (ArrayList<StoreNamePoints>) getIntent().getSerializableExtra("codes");
        CustomListForQRCodeCustomAdapter storageAdapter = new CustomListForQRCodeCustomAdapter(this, storageList);

        ListView listView = findViewById(R.id.code_list);
        listView.setAdapter(storageAdapter);

        user_name.setText(UserName);

        ImageButton go_back = findViewById(R.id.go_back_btn);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
