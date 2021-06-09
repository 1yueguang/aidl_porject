package com.example.aidl_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class RemoteService extends Service {

    private static List<ContactBO> contacts = new ArrayList<>();

    private IRemoteService.Stub binder = new IRemoteService.Stub() {

        @Override
        public List<ContactBO> transfer(){
            Log.e(getClass().getName(),"数据传输");
            synchronized (this) {
                if (contacts != null) {
                    return contacts;
                }
                return new ArrayList<>();
            }
        }

        @Override
        public void addContact(ContactBO contact){
            synchronized (this) {
                if (contacts == null) {
                    contacts = new ArrayList<>();
                }
                if (contact == null) {
                    Log.e(TAG, "Book is null in In");
                    contact = new ContactBO("asdas","12312312");
                }
                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                if (!contacts.contains(contact)) {
                    contacts.add(contact);
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.e(TAG, "invoking addBooks() method , now the list is : " + contacts.toString());
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }
    
    public static void setContacts(List<ContactBO> list){
        contacts = list;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
