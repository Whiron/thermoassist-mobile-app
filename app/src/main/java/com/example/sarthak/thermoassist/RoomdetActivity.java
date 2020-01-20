package com.example.sarthak.thermoassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class RoomdetActivity extends AppCompatActivity implements
 AdapterView.OnItemSelectedListener {

    String build=new String();
    //String[] country = new String[50];
    String[] country={"Select Room", "Room1","Room2","Room3","Room4","Room5"};
    String[] mood = {"Select Mood", "Hot", "Comfort", "Cold"};

    private static final String TAG = RoomdetActivity.class.getSimpleName();
    private TextView TxtTemp,TxtHum,TxtFlag,ValTemp,ValHum,ValFlag;
    private Button btnUpdate;
    int t_rooms;
    int c_room=1;

    private RadioGroup moodRadioGroup;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomdet);






        //configure FireBase

        mFirebaseInstance = FirebaseDatabase.getInstance();

        FirebaseUser currentuser =  FirebaseAuth.getInstance().getCurrentUser();
        String userId=currentuser.getUid();

        mFirebaseDatabase=mFirebaseInstance.getReference("User").child(userId);

        ValTemp = (TextView) findViewById(R.id.val_temp);
        ValHum = (TextView) findViewById(R.id.val_hum);
        ValFlag = (TextView) findViewById(R.id.val_flag);
        moodRadioGroup = (RadioGroup) findViewById(R.id.mood_radio_group);
        btnUpdate = (Button) findViewById(R.id.btn_update);

        /*btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //change_mood();
            }
        });*/

        //Start Fire Base

        mFirebaseDatabase.child("tot_rooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);
                //Toast.makeText(getApplicationContext(), "change:"+value, Toast.LENGTH_SHORT).show();
                //for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                t_rooms=Integer.parseInt(value);


                // }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       /* country[0]="Select Room";
        country[1]="Room1";

        /*for(int i=1;i<=t_rooms;i++){
            country[i]="Room"+i;
        }*/
        /*for(int i=t_rooms;i<=50;i++){
            country[i]=null;
        }*/



        //End Firebase

        Spinner spin = (Spinner) findViewById(R.id.spinner);

        spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);






    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),country[position] ,Toast.LENGTH_LONG).show();

        pos=Integer.toString(position);
        build=country[position];

        //Temp


        mFirebaseDatabase.child("Room"+pos).child("temp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);
                //Toast.makeText(getApplicationContext(), "change:"+value, Toast.LENGTH_SHORT).show();
                //for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                ValTemp.setText(value);

                // }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Hum

        mFirebaseDatabase.child("Room"+pos).child("hum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);
                //Toast.makeText(getApplicationContext(), "change:"+value, Toast.LENGTH_SHORT).show();
                //for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                ValHum.setText(value);

                // }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Flag

        mFirebaseDatabase.child("Room"+pos).child("flag").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);
                //Toast.makeText(getApplicationContext(), "change:"+value, Toast.LENGTH_SHORT).show();
                //for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                ValFlag.setText(value);

                // }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /*public void change_mood()
    {
        int selectedId = moodRadioGroup.getCheckedRadioButtonId();
        String mood;
        if(selectedId == R.id.mood_hot)
            mood = "Hot";
        else if (selectedId == R.id.mood_comfort)
            mood = "Comfort";
        else
            mood="cold"; mFirebaseDatabase.child("Room"+pos).child("flag").setValue(mood);
    }*/


}
