package com.example.sarthak.thermoassist;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.sarthak.thermoassist.m_FireBase.FirebaseHelper;
import com.example.sarthak.thermoassist.m_Model.Room;
import com.example.sarthak.thermoassist.m_UI.MyAdapter;


/*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA INPUT
 */
public class RoomActivity extends AppCompatActivity {
    DatabaseReference db;
    FirebaseHelper helper;
    MyAdapter adapter;
    RecyclerView rv;
    EditText nameEditTxt,propTxt,descTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SETUP RECYCLER
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //INITIALIZE FIREBASE DB

        db= FirebaseDatabase.getInstance().getReference("User");
        helper=new FirebaseHelper(db);

        //ADAPTER
        adapter=new MyAdapter(this,helper.retrieve());
        rv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();
            }
        });
    }

    //DISPLAY INPUT DIALOG
    private void displayInputDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.input_dialog);

        nameEditTxt= (EditText) d.findViewById(R.id.nameEditText);
        propTxt= (EditText) d.findViewById(R.id.propellantEditText);
        descTxt= (EditText) d.findViewById(R.id.descEditText);
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);

        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                String name=nameEditTxt.getText().toString();
                String propellant=propTxt.getText().toString();
                String desc=descTxt.getText().toString();

                //SET DATA
                Room s=new Room();
                s.setTemp(name);
                s.setFlag(propellant);
                s.setHum(desc);


                //SIMPLE VALIDATION
                if(name != null && name.length()>0)
                {
                    //THEN SAVE
                    if(helper.save(s))
                    {
                        //IF SAVED CLEAR EDITXT
                        nameEditTxt.setText("");
                        propTxt.setText("");
                        descTxt.setText("");


                        adapter=new MyAdapter(RoomActivity.this,helper.retrieve());
                        rv.setAdapter(adapter);


                    }
                }else
                {
                    Toast.makeText(RoomActivity.this, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        d.show();
    }


}
