package com.example.sarthak.thermoassist.m_UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sarthak.thermoassist.DetailActivity;
import com.example.sarthak.thermoassist.R;
import com.example.sarthak.thermoassist.m_Model.Room;

import java.util.ArrayList;

/**
 * Created by Oclemy on 7/3/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context c;
    ArrayList<Room> rooms;

    public MyAdapter(Context c, ArrayList<Room> rooms) {
        this.c = c;
        this.rooms = rooms;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.model,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final  Room s=rooms.get(position);

        holder.nameTxt.setText(s.getTemp());
        holder.propTxt.setText(s.getFlag());
        holder.descTxt.setText(s.getHum());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //OPEN DETAI ACTIVITY
                openDetailActivity(s.getTemp(),s.getFlag(),s.getHum());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    //OPEN DETAIL ACTIVITY
    private void openDetailActivity(String...details)
    {
        Intent i=new Intent(c,DetailActivity.class);

        i.putExtra("NAME_KEY",details[0]);
        i.putExtra("DESC_KEY",details[1]);
        i.putExtra("PROP_KEY",details[2]);

        c.startActivity(i);
    }
}




















