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

public class NetworkAdaptor extends ArrayAdapter {
    Context context;
    List<NetworkINFO> infos;

    public NetworkAdaptor(@NonNull Context context, int resource,List<NetworkINFO>infos) {
        super(context, resource,infos);
        this.context=context;
        this.infos=infos;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView !=null){
            holder = (ViewHolder)convertView.getTag();
        }else {

            convertView = inflater.inflate(R.layout.networkview, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        }


        holder.connection.setText(infos.get(position).getConnecttiontype());
        holder.carrie.setText(infos.get(position).getCarriername());

        return convertView;
    }

    public class ViewHolder{

        TextView connection,carrie;

        public ViewHolder(View view) {
            connection = (TextView)view.findViewById(R.id.netconnection);
            carrie = (TextView)view.findViewById(R.id.netcarrier);
        }

    }
}
