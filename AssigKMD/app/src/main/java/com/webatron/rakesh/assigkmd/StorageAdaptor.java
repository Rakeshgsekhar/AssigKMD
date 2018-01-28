package com.webatron.rakesh.assigkmd;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rakesh on 25/1/18.
 */

public class StorageAdaptor extends ArrayAdapter {

    Context context;
    List<StorageDetails> details;
    public StorageAdaptor(@NonNull Context context, int resource,List<StorageDetails> details) {
        super(context, resource,details);
        this.context = context;
        this.details = details;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView !=null){
            holder = (ViewHolder)convertView.getTag();
        }else {

            convertView = inflater.inflate(R.layout.storageview, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        }

        holder.heading.setText(details.get(position).getTittle());
        holder.total.setText(details.get(position).getTotalspace());
        holder.used.setText(details.get(position).getUsedspace());
        holder.available.setText(details.get(position).getFreespace());


        return convertView;
    }


    public class ViewHolder{
        TextView heading,total,used,available;

        public ViewHolder(View view){

            heading = (TextView)view.findViewById(R.id.heading);
            total = (TextView)view.findViewById(R.id.totalstorage);
            used = (TextView)view.findViewById(R.id.used);
            available = (TextView)view.findViewById(R.id.free);


        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
}
