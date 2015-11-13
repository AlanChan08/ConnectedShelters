package com.ac.alan.connectedshelters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alan on 13/11/2015.
 */
public class ListAdapter extends ArrayAdapter {

    List<Record> records;
    LayoutInflater mInflater;
    Context context;

    public ListAdapter(Context context, List<Record> list) {
        super(context, 0, list);
        this.records = list;
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.list_view__shelter_item,parent,false);
            holder = new ViewHolder();

            // ** CHANGE HERE ** //
            // we do the mapping with our xml textviews already define in part 2)
            holder.element1 = (TextView) convertView.findViewById(R.id.shelterAdress);
            holder.element2 =(TextView) convertView.findViewById(R.id.shelterPostalCode);

            // we set a tag to our view to re-use it
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        Record record = records.get(position);
        Record.Fields fields = record.getFields();

        holder.element1.setText(fields.getAdresse());
        holder.element2.setText(fields.getCode_postal());
        return convertView;

    }

    static class ViewHolder
    {
        TextView element1;
        TextView element2;
    }
}