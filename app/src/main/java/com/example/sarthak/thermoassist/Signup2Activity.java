package com.example.sarthak.thermoassist;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup2Activity extends AppCompatActivity {

    private static final String TAG = Signup2Activity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputFname, inputContact,inputLname,inputGender,inputTotrooms;
    private Button btnSignup,btnSignin;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String userId;
    private String contact;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //txtDetails = (TextView) findViewById(R.id.txt_user);
        inputFname = (EditText) findViewById(R.id.f_name);
        inputContact = (EditText) findViewById(R.id.contact_no);
        inputLname = (EditText) findViewById(R.id.l_name);
        inputGender = (EditText) findViewById(R.id.gender);
        inputTotrooms = (EditText) findViewById(R.id.tot_rooms);
        btnSignup = (Button) findViewById(R.id.sign_up_button);
        btnSignin = (Button) findViewById(R.id.sign_in_button);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("User");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Thermo Assist");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                movelogin();

            }
        });
        // Save / update the user
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fname = inputFname.getText().toString();
                contact = inputContact.getText().toString();
                String Lname=inputLname.getText().toString();
                String Tot_rooms=inputTotrooms.getText().toString();
                String Gender=inputGender.getText().toString();

                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                    createUser(Fname, Lname,contact,Gender,Tot_rooms);

                } else {
                    updateUser(Fname, Lname,contact,Gender,Tot_rooms);

                }
            }
        });

        toggleButton();
    }

    private void movelogin(){

        Intent intent=new Intent(Signup2Activity.this,LoginActivity.class);
        intent.putExtra("uemail",email);//passes text to next page
        startActivity(intent);
    }

    // Changing button text
    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            btnSignup.setText("Save");
        } else {
            btnSignup.setText("Update");
        }
    }

    /**
     * Creating new user node under 'users'
     */
    private void createUser(String Fname, String Lname, String contact, String Gender, String Tot_rooms) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
             //userId = mFirebaseDatabase.push().getKey();
            Bundle bundle = getIntent().getExtras();
            final String email = bundle.getString("uemail");
            final String UsrName = email.substring(0, email.lastIndexOf("."));
            //userId=UsrName;
            FirebaseUser currentuser =  FirebaseAuth.getInstance().getCurrentUser();
            userId=currentuser.getUid();
        }

        User user = new User(Fname, Lname,contact,Gender,Tot_rooms);

        mFirebaseDatabase.child(userId).setValue(user);
        int i,t_rooms;
        t_rooms=Integer.parseInt(Tot_rooms);
        for(i=1;i<=t_rooms;i++)
        {
            mFirebaseDatabase.child(userId).child("Room"+i).child("hum").setValue("0");
            mFirebaseDatabase.child(userId).child("Room"+i).child("flag").setValue("0");
            mFirebaseDatabase.child(userId).child("Room"+i).child("temp").setValue("0");
        }

        addUserChangeListener();

        Intent intent=new Intent(Signup2Activity.this,LoginActivity.class);
        intent.putExtra("ucontact",contact);//passes text to next page
        startActivity(intent);
    }



    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.f_name + ", " + user.contact_no);

                // Display newly updated name and email
                // txtDetails.setText(user.name + ", " + user.contact);
                Toast.makeText(getApplicationContext(), "Data Entered", Toast.LENGTH_SHORT).show();

                // clear edit text
                inputLname.setText("");
                inputGender.setText("");
                inputContact.setText("");
                inputTotrooms.setText("");
                //inputPassword.setText("");
                inputFname.setText("");


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String name, String contact,String password, String dlno, String np) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("name").setValue(name);

        if (!TextUtils.isEmpty(contact))
            mFirebaseDatabase.child(userId).child("contact").setValue(contact);
        if (!TextUtils.isEmpty(password))
            mFirebaseDatabase.child(userId).child("password").setValue(password);
        if (!TextUtils.isEmpty(dlno))
            mFirebaseDatabase.child(userId).child("dlno").setValue(dlno);
        if (!TextUtils.isEmpty(np))
            mFirebaseDatabase.child(userId).child("email").setValue(np);

    }
}
