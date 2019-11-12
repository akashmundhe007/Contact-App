package com.test.test.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class IncomingCalls implements Parcelable {

    private String name;
    private String number;
    private Date date;
    private String duration;

    public IncomingCalls() {
    }

    private IncomingCalls(Parcel in) {
        name = in.readString();
        number = in.readString();
        duration = in.readString();
    }

    public static final Creator<IncomingCalls> CREATOR = new Creator<IncomingCalls>() {
        @Override
        public IncomingCalls createFromParcel(Parcel in) {
            return new IncomingCalls(in);
        }

        @Override
        public IncomingCalls[] newArray(int size) {
            return new IncomingCalls[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(number);
        parcel.writeString(duration);
    }
}
