package com.example.ihuntwithjavalins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ihuntwithjavalins.QRCode.CustomList;
import com.example.ihuntwithjavalins.QRCode.QRCode;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private static final int REMOVE_QR_CODE_REQUEST = 1;
    ArrayList<QRCode> QRCodeList;
    ArrayAdapter<QRCode> QRCodeAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView codeList;


        codeList = findViewById(R.id.player_code_list);
        QRCodeList = new ArrayList<>();
        Button scoreboard = findViewById(R.id.scoreboard);
        scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Scoreboard.class);
                startActivity(intent);
            }
        });




        // Create players and add them to the ArrayList
        QRCode q1 = new QRCode("##1_43234","Rock1","01-Jan-2023","445");
        QRCode q2 = new QRCode("##2_43234","Rock2","04-Jan-2023","689");
        QRCode q3 = new QRCode("##3_43234","Rock3","05-Jan-2023","450");
        QRCode q4 = new QRCode("##4_43234","Rock4","03-Jan-2023","4168");

        QRCodeList.add(q1);
        QRCodeList.add(q2);
        QRCodeList.add(q3);
        QRCodeList.add(q4);
//        QRCodeList.remove(QRCodeList.get(getIntent().getExtras("Name")))
//        int pos =Integer.parseInt(getIntent().getStringExtra("Position"));
//        QRCodeList.remove(QRCodeList.get(pos));'
        TextView total_codes_text = findViewById(R.id.tot_codes);

        total_codes_text.setText(Integer.toString(QRCodeList.size()));
        QRCodeAdapter = new CustomList(this,QRCodeList);
        codeList.setAdapter(QRCodeAdapter);

        Intent intent = new Intent(MainActivity.this,Remove.class);
        Bundle args = new Bundle();
        args.putSerializable("datalist",(Serializable) QRCodeList);
        intent.putExtra("Bundle",args);





        codeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                intent.putExtra("Name",QRCodeList.get(i).getCodeName());
                intent.putExtra("HashCode",QRCodeList.get(i).getCode());
                intent.putExtra("Points",QRCodeList.get(i).getPoints());
                intent.putExtra("DateCaught",QRCodeList.get(i).getDate_scanned());
                intent.putExtra("Pos",i);
                startActivityForResult(intent,1);
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


//        Toast.makeText(getApplicationContext(),"Yes")
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REMOVE_QR_CODE_REQUEST && resultCode == RESULT_OK) {

            // Get updated QRCodeList from Remove activity
            ArrayList<QRCode> updatedList = (ArrayList<QRCode>) data.getSerializableExtra("updatedList");
            // Update QRCodeList and adapter
            QRCodeList.clear();
            QRCodeList.addAll(updatedList);
            TextView total_codes_text = findViewById(R.id.tot_codes);
            total_codes_text.setText(Integer.toString(QRCodeList.size()));
            QRCodeAdapter.notifyDataSetChanged();
        }
    }


}




