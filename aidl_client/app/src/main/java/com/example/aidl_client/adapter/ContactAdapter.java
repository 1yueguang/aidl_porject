package com.example.aidl_client.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidl_client.R;
import com.example.aidl_client.utils.ContactUtils;
import com.example.aidl_server.ContactBO;

import java.util.List;

public class ContactAdapter extends BaseAdapter {

    private List<ContactBO> contacts;

    public ContactAdapter() {
    }

    public ContactAdapter(List<ContactBO> contacts) {
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.name);
            viewHolder.phone = view.findViewById(R.id.phone);
            Button saveButton = view.findViewById(R.id.save);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), i+"保存", Toast.LENGTH_SHORT).show();
                }
            });
            Button delButton = view.findViewById(R.id.del);
            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContactUtils.deleteContact(contacts.get(i).getName());
                    contacts.remove(i);
                    notifyDataSetChanged();
                }
            });
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.name.setText(contacts.get(i).getName());
        viewHolder.phone.setText(contacts.get(i).getPhone());
        return view;
    }

    public static class ViewHolder{
        TextView name;
        TextView phone;
    }
}
