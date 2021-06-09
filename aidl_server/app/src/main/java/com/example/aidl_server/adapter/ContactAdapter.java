package com.example.aidl_server.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aidl_server.ContactBO;
import com.example.aidl_server.MainActivity;
import com.example.aidl_server.R;

import java.util.LinkedList;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.name);
            viewHolder.phone = view.findViewById(R.id.phone);
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
