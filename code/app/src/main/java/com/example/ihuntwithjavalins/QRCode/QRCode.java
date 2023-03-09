package com.example.ihuntwithjavalins.QRCode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents a QRCode scanned by Players in the application.
 * @version 1.0
 */
public class QRCode implements Parcelable {
    // putting making QRCode on hold until we figure out visual representation and QR Code scanning functionality and how we store those values
    String code_value;
    String code_name;
    String date_scanned;
    String points;

    public QRCode(String code,String name,String date_scanned,String points){
        this.code_value=code;
        this.code_name = name;
        this.date_scanned = date_scanned;
        this.points = points;
    }

    public String getCode() {
        return code_value;
    }
    public String getCodeName() {
        return code_name;
    }

    public String getDate_scanned() {
        return date_scanned;
    }

    public String getPoints() {
        return points;
    }

    // Parcelable implementation
    protected QRCode(Parcel in) {
        code_value = in.readString();
        code_name = in.readString();
        date_scanned = in.readString();
        points = in.readString();
    }

    public static final Creator<QRCode> CREATOR = new Creator<QRCode>() {
        @Override
        public QRCode createFromParcel(Parcel in) {
            return new QRCode(in);
        }

        @Override
        public QRCode[] newArray(int size) {
            return new QRCode[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code_value);
        dest.writeString(code_name);
        dest.writeString(date_scanned);
        dest.writeString(points);
    }
}

