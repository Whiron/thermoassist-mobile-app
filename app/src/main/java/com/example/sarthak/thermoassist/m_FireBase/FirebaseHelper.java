package com.example.sarthak.thermoassist.m_FireBase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.example.sarthak.thermoassist.m_Model.Room;



import java.util.ArrayList;

/**
 * Created by Oclemy on 6/21/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 * 1.SAVE DATA TO FIREBASE
 * 2. RETRIEVE
 * 3.RETURN AN ARRAYLIST
 */
public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved=null;
    ArrayList<Room> room=new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //WRITE IF NOT NULL
    public Boolean save(Room room)
    {
        if(room==null)
        {
            saved=false;
        }else
        {
            try
            {
                FirebaseUser currentuser =  FirebaseAuth.getInstance().getCurrentUser();
                String userId=currentuser.getUid();
                db.child(userId).child("Room").setValue(room);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        room.clear();
        Room spacecraft=dataSnapshot.getValue(Room.class);
            room.add(spacecraft);
    }

    //READ THEN RETURN ARRAYLIST
    public ArrayList<Room> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return room;
    }
}



















