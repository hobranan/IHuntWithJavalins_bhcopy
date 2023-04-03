package com.example.ihuntwithjavalins.Scoreboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QRCode.QRCodeViewOtherActivity;
import com.example.ihuntwithjavalins.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * ShowIndividualCodes is responsible for showing the details of each QRCode
 */
public class ShowIndividualCodes extends AppCompatActivity {
    boolean sortPointAmountAscend = false;
    boolean sortHaveAscend = false;
    boolean sortNameAscend = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scoreboard_main_individual);

        TextView user_name = findViewById(R.id.userName);

        Button see_user_profile = findViewById(R.id.player_profile_btn);

        Button btn_have = findViewById(R.id.have_btn);
        Button btn_points = findViewById(R.id.ou_points);
        Button btn_name = findViewById(R.id.ou_name);

        // Get the intent from the previous activity
        Intent myIntent = getIntent();
        Player thisPlayer = (Player) myIntent.getSerializableExtra("focusedPlayer");
        ArrayList<Player> playerList = (ArrayList<Player>) myIntent.getSerializableExtra("playerList");

        ArrayList<StoreNamePoints> storageList = (ArrayList<StoreNamePoints>) getIntent().getSerializableExtra("StorageList");
        CustomListForQRCodeCustomAdapter storageAdapter = new CustomListForQRCodeCustomAdapter(this, storageList);
        ListView listView = findViewById(R.id.code_list);
        listView.setAdapter(storageAdapter);

        user_name.setText(thisPlayer.getUsername());

        String btn_txt = "See " + thisPlayer.getUsername() + "'s Profile";

        Button go_back = findViewById(R.id.go_back_btn);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sort the player list by num of codes
                Collections.sort(storageList, new Comparator<StoreNamePoints>() {
                    @Override
                    public int compare(StoreNamePoints c1, StoreNamePoints c2) {
                        return (c1.getCodeName()).compareTo(c2.getCodeName());
                    }
                });
                if (sortNameAscend) {
                    Collections.reverse(storageList);
                }
                sortNameAscend = !sortNameAscend;
                // Update the adapter with the sorted list
                storageAdapter.notifyDataSetChanged();
            }
        });

        btn_have.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sort the player list by num of codes
                Collections.sort(storageList, new Comparator<StoreNamePoints>() {
                    @Override
                    public int compare(StoreNamePoints c1, StoreNamePoints c2) {

                        return Boolean.compare(c1.isScanned(), c2.isScanned());
                    }
                });
                if (sortHaveAscend) {
                    Collections.reverse(storageList);
                }
                sortHaveAscend = !sortHaveAscend;
                // Update the adapter with the sorted list
                storageAdapter.notifyDataSetChanged();
            }
        });

        btn_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sort the player list by num of codes
                Collections.sort(storageList, new Comparator<StoreNamePoints>() {
                    @Override
                    public int compare(StoreNamePoints c1, StoreNamePoints c2) {
                        return Integer.compare(Integer.parseInt(c1.getCodePoints()), Integer.parseInt(c2.getCodePoints()));
                    }
                });
                if (sortPointAmountAscend) {
                    Collections.reverse(storageList);
                }
                sortPointAmountAscend = !sortPointAmountAscend;
                // Update the adapter with the sorted list
                storageAdapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoreNamePoints thisCodeTemp = storageAdapter.getItem(position);
                QRCode thisCode = null;
                for (QRCode mycode : thisPlayer.getCodes()) {
                    if ((mycode.getCodeName()).equals(thisCodeTemp.getCodeName())) {
                        thisCode = mycode;
                        break;
                    }
                }
                Intent intent = new Intent(ShowIndividualCodes.this, QRCodeViewOtherActivity.class);
                intent.putExtra("focusedCode", (Serializable) thisCode);
                startActivity(intent);
            }
        });

        see_user_profile.setText(btn_txt);
        see_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start_view_user_activity = new Intent(ShowIndividualCodes.this, ShowUserProfile.class);
                start_view_user_activity.putExtra("PlayerDetails", (Serializable) thisPlayer);
                start_view_user_activity.putExtra("playerList", (Serializable) playerList);
                startActivity(start_view_user_activity);
            }
        });

    }

}
