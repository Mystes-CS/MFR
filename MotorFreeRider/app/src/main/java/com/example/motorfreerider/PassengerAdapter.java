package com.example.motorfreerider;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PassengerAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<user> users;
    Context context;

    public PassengerAdapter(Context context, List<user> user) {
        //myInflater = LayoutInflater.from(context);
        this.context = context;
        this.users = user;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.indexOf(getItem(position));
    }

    private class ViewHolder {
        TextView txtName;
        TextView txtPlace;
        TextView txtScore;
        TextView txtTime;
        TextView txtDescription;
        Button btnCancel;

        public ViewHolder(TextView txtName, TextView txtPlace, TextView txtScore,TextView txtTime, Button Cancel,TextView txtDescription) {
            this.txtName = txtName;
            this.txtPlace = txtPlace;
            this.txtScore = txtScore;
            this.txtTime = txtTime;
            this.txtDescription = txtDescription;
            this.btnCancel = Cancel;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.passenger_list_item, null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.List_Name),
                    (TextView) convertView.findViewById(R.id.List_Place),
                    (TextView) convertView.findViewById(R.id.List_Score),
                    (TextView)convertView.findViewById(R.id.List_Time),
                    (Button) convertView.findViewById(R.id.List_Cancel),
                    (TextView)convertView.findViewById(R.id.List_Descript_P)
            );
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final user _user = (user) getItem(position);
        String Name = _user.getLastName() + _user.getFirstName();
        holder.txtTime.setText(_user.getTime());
        holder.txtName.setText(Name);
        holder.txtScore.setText(_user.getScore());
        holder.txtPlace.setText(_user.getStart());
        holder.txtDescription.setText(_user.getDescription());
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel cancel =new Cancel(_user);
                try {
                    cancel.cancel();
                    Intent intent=new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setClass(context, MapsActivity.class);
                    context.startActivity(intent);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return convertView;
    }
}
