package com.ss.orthologs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class VisitsListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;

    public VisitsListAdapter(Context context, List<String> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.visits_list_view, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(values.get(position));
        return rowView;
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}