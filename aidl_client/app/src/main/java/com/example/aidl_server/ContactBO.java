package com.example.aidl_server;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactBO implements Parcelable {

    private String name;
    private String phone;

    public ContactBO(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    protected ContactBO(Parcel in) {
        name = in.readString();
        phone = in.readString();
    }

    public static final Creator<ContactBO> CREATOR = new Creator<ContactBO>() {
        @Override
        public ContactBO createFromParcel(Parcel in) {
            return new ContactBO(in);
        }

        @Override
        public ContactBO[] newArray(int size) {
            return new ContactBO[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phone);
    }

    public void readFromParcel(Parcel dest) {
        name = dest.readString();
        phone = dest.readString();
    }

    @Override
    public String toString() {
        return "ContactBO{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
