package com.example.newproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {
    private TextView a,b,c;
    private FirebaseDatabase database;
    private DatabaseReference myref;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        a=(TextView)findViewById(R.id.name);
        b=(TextView)findViewById(R.id.email);
        c=(TextView)findViewById(R.id.phoneno);
        mAuth=FirebaseAuth.getInstance();
        myref=FirebaseDatabase.getInstance().getReference().child("User").child(mAuth.getCurrentUser().getUid());



        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String emailid = snapshot.child("email").getValue().toString();
                String  nameid = snapshot.child("name").getValue().toString();
                String phonenoid= snapshot.child("phoneno").getValue().toString();
                a.setText(nameid);
                b.setText(emailid);
                c.setText(phonenoid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(profile.this,"error",Toast.LENGTH_LONG).show();
            }
        });

    }
}
