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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Adapter (customized) for linking/showing the backend-datalist (of objects) with the UI-content-list (in content.xml)
 */
public class LibraryListForLibraryAdapter extends ArrayAdapter<QRCode> {

    private ArrayList<QRCode> codes;
    private Context context;

    public LibraryListForLibraryAdapter(Context context, ArrayList<QRCode> codes) {
        super(context, 0, codes);
        this.codes = codes;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_library_item, parent, false);
        }
        QRCode tempCode = codes.get(position);
        TextView codeName = view.findViewById(R.id.code_name_text);
        TextView codePoints = view.findViewById(R.id.code_points_text);
        TextView codeHash = view.findViewById(R.id.code_hash_text);
        TextView codeDate = view.findViewById(R.id.code_date_text);
        codeName.setText(tempCode.getCodeName());
        codePoints.setText(tempCode.getCodePoints() + " pts");
//        codeHash.setText(tempCode.getCodeHash());
        codeHash.setText("");

        // https://www.baeldung.com/java-string-to-date
        // https://stackoverflow.com/questions/5683728/convert-java-util-date-to-string
        String dateInString = tempCode.getCodeDate(); //"19590709";
        Date thisdate = null;
        String codeAsDate = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
            try {
                thisdate = formatter.parse(dateInString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String pattern = "dd-MMM-yyyy";
            DateFormat df = new SimpleDateFormat(pattern);
            codeAsDate = df.format(thisdate);
        }
        if (!codeAsDate.equals("")) {
            codeDate.setText(codeAsDate);
        } else {
            codeDate.setText(dateInString);
        }

        return view;
    }
}
