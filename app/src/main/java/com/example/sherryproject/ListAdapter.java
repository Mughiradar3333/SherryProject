package com.example.sherryproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<SmsFormat> {
    private final Context context;
    private final List<SmsFormat> smslist;

    public ListAdapter(Context context, List<SmsFormat> smsList) {
        super(context, R.layout.activity_inbox, smsList);
        this.context = context;
        this.smslist = smsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_inbox, parent, false);

        TextView senderNumber = (TextView) rowView.findViewById(R.id.textView1);

        senderNumber.setText(smslist.get(position).getName() + "\n"
                + smslist.get(position).getNumber() + "\n" + "\n"
                + smslist.get(position).getBody());

        return rowView;
    }
}
