package com.example.android.avengersnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity
{

    private TextView username, userProfName, userStatus, userCountry, userGender, userRelation, userDob;
    private CircleImageView userProfileImage;

    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    private  String current_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth  =FirebaseAuth.getInstance();
        current_user_id =mAuth.getCurrentUser().getUid();
        profileUserRef= FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id);



        username = (TextView) findViewById(R.id.my_profile_user_name);
        userCountry = (TextView) findViewById(R.id.my_profile_country);
        userStatus = (TextView) findViewById(R.id.my_profile_status);
        userProfName = (TextView) findViewById(R.id.my_profile_full_name);
        userGender = (TextView) findViewById(R.id.my_profile_gender);
        userRelation = (TextView) findViewById(R.id.my_profile_relationshipstatus);
        userDob = (TextView) findViewById(R.id.my_profile_dob);
        userProfileImage = (CircleImageView) findViewById(R.id.myProfile_pic);


        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String myProfileImage = dataSnapshot.child("Profileimage").getValue().toString();
                    String myUsername = dataSnapshot.child("username").getValue().toString();
                    String myProfileName = dataSnapshot.child("fullname").getValue().toString();
                    String myProfileStatus = dataSnapshot.child("status").getValue().toString();
                    String myDOB = dataSnapshot.child("dob").getValue().toString();
                    String myCountry = dataSnapshot.child("Country").getValue().toString();
                    String myGender = dataSnapshot.child("Gender").getValue().toString();
                    String myRelationshipStatus = dataSnapshot.child("Relationship Status").getValue().toString();

                    Picasso.with(ProfileActivity.this).load(myProfileImage).placeholder(R.drawable.profile).into(userProfileImage);

                    username.setText("@ "+myUsername);
                    userProfName.setText(myProfileName);
                    userStatus.setText(myProfileStatus);
                    userDob.setText("DOB: "+myDOB);
                    userCountry.setText("Country: "+myCountry);
                    userGender.setText("Gender: "+myGender);
                    userRelation.setText("Relationship Status: "+myRelationshipStatus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
