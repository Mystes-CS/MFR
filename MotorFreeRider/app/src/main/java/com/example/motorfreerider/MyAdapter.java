package com.example.motorfreerider;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<user> users;
    Context context;
    public static String ID;

    public MyAdapter(Context context, List<user> user) {
        myInflater = LayoutInflater.from(context);
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
        TextView txtFinish;
        TextView txtDescription;
        Button btnSend, btnRefuse;

        public ViewHolder(TextView txtName, TextView txtPlace, TextView txtScore, Button send, Button refuse, TextView Finish,TextView txtDescription) {
            this.txtName = txtName;
            this.txtPlace = txtPlace;
            this.txtScore = txtScore;
            this.btnSend = send;
            this.btnRefuse = refuse;
            this.txtFinish = Finish;
            this.txtDescription =txtDescription;
        }

    }

    public String getPassengerID() {
        return ID;
    }

    public void setPassengerID(String ID) {
        this.ID = ID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.List_Name),
                    (TextView) convertView.findViewById(R.id.List_Place),
                    (TextView) convertView.findViewById(R.id.List_Score),
                    (Button) convertView.findViewById(R.id.List_Send),
                    (Button) convertView.findViewById(R.id.List_Refuse),
                    (TextView) convertView.findViewById(R.id.List_Finish),
                    (TextView) convertView.findViewById(R.id.List_PassDes)
            );
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final user _user = (user) getItem(position);
        String Name = _user.getLastName() + _user.getFirstName();
        holder.txtName.setText(Name);
        holder.txtScore.setText(_user.getScore());
        holder.txtPlace.setText(_user.getCarryPlace());
        holder.txtDescription.setText(_user.getDescription());
        holder.txtFinish.setText(_user.getFinishPlace());
        holder.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accept accept = new Accept(_user);
                try {
                    user us = accept.accept();
                    ID = us.getID();
                    Log.w("IDDDD", us.getOwnerID() + "  " + us.getID());
                    ChangeProfile changeProfile = new ChangeProfile("multipleAgree", us.getID(), us.getOwnerID());
                    String goDrive = changeProfile.Change();
                    Intent intent=new Intent();
                    intent.setClass(context, MapsActivity.class);
                    context.startActivity(intent);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Refuse refuse = new Refuse(_user);
                try {
                    String IsSuccess = refuse.refuse();
                    if (IsSuccess.equals("true")) {
                        Intent intent=new Intent();
                        intent.setClass(context, MapsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("refuse","OK");
                        context.startActivity(intent);
                    }
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
