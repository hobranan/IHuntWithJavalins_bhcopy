package com.example.ihuntwithjavalins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Remove extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove);

        TextView user_name = findViewById(R.id.code_name);
        TextView hash_code = findViewById(R.id.hash_code);
        TextView user_points = findViewById(R.id.points_view);
        TextView user_date = findViewById(R.id.date_caught);
        Button rem_code = findViewById(R.id.remove_code);



        // Convert QRCodeListJsonString to an ArrayList<QRCode> using Gson
        Intent intent = getIntent();
        Bundle args= intent.getBundleExtra("Bundle");
        ArrayList<QRCode> myList = (ArrayList<QRCode>)args.getSerializable("datalist");

        // Get the position of the item to be removed from the intent
        Integer get_pos = getIntent().getIntExtra("Pos",0);

        // Set the text views with the details of the QR code to be removed
        user_name.setText(getIntent().getStringExtra("Name"));
        hash_code.setText(getIntent().getStringExtra("HashCode"));
        user_points.setText(getIntent().getStringExtra("Points"));
        user_date.setText(getIntent().getStringExtra("DateCaught"));


        // Set an OnClickListener on the remove button
        rem_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a confirmation dialog before deleting the item
                AlertDialog.Builder builder = new AlertDialog.Builder(Remove.this);
                builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Remove the item from the list
                                myList.remove(myList.get(get_pos));
                                // Create a new intent with the updated list and set the result to RESULT_OK
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("updatedList", myList);
                                setResult(Activity.RESULT_OK,resultIntent);
                                // Finish the activity
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }
}






