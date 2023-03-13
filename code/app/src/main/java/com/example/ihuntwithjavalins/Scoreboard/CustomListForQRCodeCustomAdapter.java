package com.example.ihuntwithjavalins.Scoreboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ihuntwithjavalins.R;

import java.util.ArrayList;

/*
Adapter (customized) for linking/showing the backend-datalist (of objects) with
the UI-content-list (in content.xml)
 */
public class CustomListForQRCodeCustomAdapter extends ArrayAdapter<StoreNamePoints> {

    private ArrayList<StoreNamePoints> codes;
    private Context context;

    public CustomListForQRCodeCustomAdapter(Context context, ArrayList<StoreNamePoints> codes){
        super(context,0, codes);
        this.codes = codes;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.scoreboard_content_individual, parent,false);
        }
        StoreNamePoints tempCode = codes.get(position);
        TextView codeName = view.findViewById(R.id.codeName);
        TextView codePoints = view.findViewById(R.id.code_points);
        TextView isScannedCode = view.findViewById(R.id.isScanned);
        codeName.setText(tempCode.getCodeName());
        codePoints.setText(tempCode.getCodePoints());
        isScannedCode.setText("1");
        return view;
    }
}
