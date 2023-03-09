package com.example.ihuntwithjavalins;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ihuntwithjavalins.Player.CustomListScoreBoard;
import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.QRCode.CustomList;
import com.example.ihuntwithjavalins.QRCode.QRCode;

import java.util.ArrayList;

public class Scoreboard extends AppCompatActivity {

    ArrayList<Player> PlayerCodeList;
    ArrayAdapter<Player> PlayerCodeAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_main);
        ListView codeList;


        codeList = findViewById(R.id.user_code_list);
        PlayerCodeList = new ArrayList<>();

        QRCode q1 = new QRCode("##1_43234","Rock1","01-Jan-2023","10");
        QRCode q2 = new QRCode("##2_43234","Rock2","04-Jan-2023","100");
        QRCode q3 = new QRCode("##3_43234","Rock3","05-Jan-2023","20");
        QRCode q4 = new QRCode("##4_43234","Rock4","03-Jan-2023","4168");
        QRCode q5 = new QRCode("##1_43234","Rock5","01-Jan-2023","4452");
        QRCode q6 = new QRCode("##2_43234","Rock6","04-Jan-2023","6891");
        QRCode q7 = new QRCode("##3_43234","Rock7","05-Jan-2023","453");
        QRCode q8 = new QRCode("##4_43234","Rock8","03-Jan-2023","4118");
        QRCode q9 = new QRCode("##1_43234","Rock9","01-Jan-2023","445");
        QRCode q10 = new QRCode("##2_43234","Rock10","04-Jan-2023","683");
        QRCode q11 = new QRCode("##3_43234","Rock11","05-Jan-2023","451");
        QRCode q12 = new QRCode("##4_43234","Rock12","06-Jan-2023","4568");
        QRCode q13 = new QRCode("##1_43234","Rock13","07-Jan-2023","2352");
        QRCode q14 = new QRCode("##2_43234","Rock14","08-Jan-2023","4591");
        QRCode q15 = new QRCode("##3_43234","Rock15","09-Jan-2023","483");
        QRCode q16 = new QRCode("##4_43234","Rock16","10-Jan-2023","408");

        Player me = new Player("Viraj","test@gmail.com","5879372653","Alberta");
        me.addCode(q1);
        me.addCode(q2);
        me.addCode(q3);



        Player player1 = new Player("John Doe", "john.doe@example.com", "555-1234", "New York");
        player1.addCode(q1);
        player1.addCode(q4);
        player1.addCode(q7);

        Player player2 = new Player("Jane Smith", "jane.smith@example.com", "555-5678", "Los Angeles");
        player2.addCode(q2);
        player2.addCode(q5);
        player2.addCode(q8);

        Player player3 = new Player("Bob Johnson", "bob.johnson@example.com", "555-9876", "Chicago");
        player3.addCode(q3);
        player3.addCode(q6);
        player3.addCode(q9);

        Player player4 = new Player("Alice Lee", "alice.lee@example.com", "555-4321", "San Francisco");
        player4.addCode(q1);
        player4.addCode(q5);
        player4.addCode(q9);

        Player player5 = new Player("David Kim", "david.kim@example.com", "555-2468", "Seattle");
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