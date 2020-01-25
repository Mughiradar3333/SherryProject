package com.example.sherryproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {
    List<Notes> innerList = new ArrayList<>();

    public Adapter(ReadNotesActivity readNotesActivity, int read_notes_item, List<Notes> innerList) {
        this.innerList = innerList;
    }

    @Override
    public int getCount() {
        return innerList.size();
    }

    @Override
    public Object getItem(int position) {
        return innerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return innerList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.read_notes_item,parent,false);

        Notes notee = innerList.get(position);
        ((TextView)view.findViewById(R.id.txt_id)).setText(notee.id);
        ((TextView)view.findViewById(R.id.txt_data)).setText(notee.note);

        return view;    }
}
