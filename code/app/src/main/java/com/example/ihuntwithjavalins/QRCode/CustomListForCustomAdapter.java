package com.example.ihuntwithjavalins.QRCode;

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

/**
 * Adapter (customized) for linking/showing the backend-datalist (of objects) with the UI-content-list (in content.xml)
 */
public class CustomListForCustomAdapter extends ArrayAdapter<QRCode> {

    private ArrayList<QRCode> codes;
    private Context context;

    public CustomListForCustomAdapter(Context context, ArrayList<QRCode> codes){
        super(context,0, codes);
        this.codes = codes;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content_library_item, parent,false);
        }
        QRCode tempCode = codes.get(position);
        TextView codeName = view.findViewById(R.id.code_name_text);
        TextView codePoints = view.findViewById(R.id.code_points_text);
        TextView codeHash = view.findViewById(R.id.code_hash_text);
        codeName.setText(tempCode.getCodeName());
        codePoints.setText(tempCode.getCodePoints());
        codeHash.setText(tempCode.getCodeHash());
        return view;
    }
}
