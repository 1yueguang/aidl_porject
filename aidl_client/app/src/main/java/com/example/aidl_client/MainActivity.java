package com.example.aidl_client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aidl_client.adapter.ContactAdapter;
import com.example.aidl_client.utils.ContactUtils;
import com.example.aidl_server.ContactBO;
import com.example.aidl_server.IRemoteService;

import java.util.List;

import static android.view.View.INVISIBLE;

public class MainActivity extends Activity {

    private IRemoteService iRemoteService = null;

    private ListView listView;

    private ImageView pgbar;

    private ImageButton addButton;

    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;

    private List<ContactBO> contacts;

    private AlertDialog insertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        ContactUtils.init(this);
        addButton = findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDialog.show();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加联系人");
        builder.setView(View.inflate(this,R.layout.dialog_insert,null));
        insertDialog = builder.create();
        insertDialog.setButton("OK", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                EditText name = insertDialog.findViewById(R.id.name);
                EditText phone = insertDialog.findViewById(R.id.phone);
                ContactBO contactBO = new ContactBO(name.getText().toString(), phone.getText().toString());
                contacts.add(contactBO);
                ContactUtils.addContact(contactBO);
                Log.e(this.getClass().getSimpleName(),contactBO.toString());
                Toast.makeText(insertDialog.getContext(),"添加联系人==" + name + "==成功",Toast.LENGTH_SHORT);
            }
        });

        insertDialog.setButton2("Cancel", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        pgbar = findViewById(R.id.pgbar);
    }


    /**
     * 尝试与服务端建立连接
     */
    private void attemptToBindService() {
        Log.e(getLocalClassName(),"尝试与服务端建立连接");
        Intent intent = new Intent();
        intent.setAction("com.example.aidl_server");
        intent.setPackage("com.example.aidl_server");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        Log.e(getLocalClassName(), "start");
        super.onStart();
        if (!mBound) {
            attemptToBindService();
        }
        System.out.println(iRemoteService);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(getLocalClassName(), "service connected");
            iRemoteService = IRemoteService.Stub.asInterface(service);
            mBound = true;
            if (iRemoteService != null) {
                try {
                    contacts = iRemoteService.transfer();
                    Log.e(getLocalClassName(), contacts.toString());
                    listView.setAdapter(new ContactAdapter(contacts));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            if (contacts == null || contacts.isEmpty()){
                pgbar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((AnimationDrawable) pgbar.getDrawable()).start();
                    }
                },100);
            }else {
                pgbar.setVisibility(INVISIBLE);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(getLocalClassName(), "service disconnected");
            mBound = false;
        }
    };
}
