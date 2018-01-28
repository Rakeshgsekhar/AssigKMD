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
 * Created by rakesh on 28/1/18.
 */

public class ResultAdaptor extends ArrayAdapter {
    Context context;

    List<Resultdata> resultdataList;
    public ResultAdaptor(@NonNull Context context, int resource,List<Resultdata> resultdataList) {
        super(context, resource,resultdataList);
        this.context=context;
        this.resultdataList = resultdataList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView !=null){
            holder = (ViewHolder)convertView.getTag();
        }else {

            convertView = inflater.inflate(R.layout.result_view, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        }

        holder.heading.setText(resultdataList.get(position).getHeading());
        holder.data1.setText(resultdataList.get(position).getData1());
        holder.data2.setText(resultdataList.get(position).getData2());
        holder.data3.setText(resultdataList.get(position).getData3());

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder{
        TextView heading,data1,data2,data3;
        public ViewHolder(View view){

            heading = (TextView)view.findViewById(R.id.resultheading);
            data1= (TextView)view.findViewById(R.id.resultdata1);
            data2 = (TextView)view.findViewById(R.id.resultdata2);
            data3 = (TextView)view.findViewById(R.id.resultdata3);


        }
    }
}
