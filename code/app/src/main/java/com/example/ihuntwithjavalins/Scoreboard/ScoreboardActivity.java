package com.example.ihuntwithjavalins.Scoreboard;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.R;

import java.util.ArrayList;

public class ScoreboardActivity extends AppCompatActivity {

    ArrayList<Player> PlayerCodeList;
    ArrayAdapter<Player> PlayerCodeAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_main);
        ListView codeList;


        codeList = findViewById(R.id.user_code_list);
        PlayerCodeList = new ArrayList<>();

        QRCode q1 = new QRCode("10");
        QRCode q2 = new QRCode("2_432100");
        QRCode q3 = new QRCode("3_4323405-Jan-0");
        QRCode q4 = new QRCode("4_43234168");
        QRCode q5 = new QRCode("1_4352");
        QRCode q6 = new QRCode("2_491");
        QRCode q7 = new QRCode("3_4353");
        QRCode q8 = new QRCode("4_432344118");
        QRCode q9 = new QRCode("445");
        QRCode q10 = new QRCode("683");
        QRCode q11 = new QRCode("3_451");
        QRCode q12 = new QRCode("4_44568");
        QRCode q13 = new QRCode("1_2352");
        QRCode q14 = new QRCode("2_432344591");
        QRCode q15 = new QRCode("3_43234483");
        QRCode q16 = new QRCode("4_43234408");

        Player me = new Player("Viraj","test@gmail.com","Alberta");
        me.addCode(q1);
        me.addCode(q2);
        me.addCode(q3);



        Player player1 = new Player("John Doe", "john.doe@example.com", "New York");
        player1.addCode(q1);
        player1.addCode(q4);
        player1.addCode(q7);

        Player player2 = new Player("Jane Smith", "jane.smith@example.com","Los Angeles");
        player2.addCode(q2);
        player2.addCode(q5);
        player2.addCode(q8);

        Player player3 = new Player("Bob Johnson", "bob.johnson@example.com", "Chicago");
        player3.addCode(q3);
        player3.addCode(q6);
        player3.addCode(q9);

        Player player4 = new Player("Alice Lee", "alice.lee@example.com",  "San Francisco");
        player4.addCode(q1);
        player4.addCode(q5);
        player4.addCode(q9);

        Player player5 = new Player("David Kim", "david.kim@example.com", "Seattle");
        player5.addCode(q2);
        player5.addCode(q6);
        player5.addCode(q10);

        PlayerCodeList.add(me);
        PlayerCodeList.add(player1);
        PlayerCodeList.add(player2);
        PlayerCodeList.add(player3);
        PlayerCodeList.add(player4);
        PlayerCodeList.add(player5);

        Toast.makeText(this, Integer.toString(me.getTotalCodes()), Toast.LENGTH_SHORT).show();


        PlayerCodeAdaptor = new CustomListScoreBoard(this,PlayerCodeList);
        codeList.setAdapter(PlayerCodeAdaptor);



    }
}