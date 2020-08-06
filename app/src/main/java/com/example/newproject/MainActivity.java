package com.example.newproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
     private EditText memail,mpassword,mphoneno,mname;
     private Button msignin;
     private FirebaseAuth mAuth;
     private FirebaseDatabase database;
     private DatabaseReference myRef;
     private TextView mlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        memail=findViewById(R.id.Rmail);
        mpassword=findViewById(R.id.Rpassword);
        mname=findViewById(R.id.Rname);
        mphoneno=findViewById(R.id.Rphone);
        msignin=findViewById(R.id.Rsignin);
        mAuth=FirebaseAuth.getInstance();
        mlogin=findViewById(R.id.textView2);
//        database = FirebaseDatabase.getInstance();
//         myRef = database.getReference();

        msignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String email=memail.getText().toString();
               final String password=mpassword.getText().toString();
               final String phoneno =mphoneno.getText().toString();
               final String name=mname.getText().toString();
                if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password))
                {
                    Toast.makeText(MainActivity.this,"fields are required",Toast.LENGTH_LONG).show();
                }
                else if(password.length()<6)
                {
                    Toast.makeText(MainActivity.this,"Length should be greater tham 6",Toast.LENGTH_LONG).show();
                }
                else if(!(email.isEmpty()&&password.isEmpty()))
                {
//                    Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_LONG).show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                String userid= firebaseUser.getUid();
                                myRef =FirebaseDatabase.getInstance().getReference().child("User").child(userid);
                                Toast.makeText(MainActivity.this,"Registred",Toast.LENGTH_LONG).show();
//                                User user = new User();
//                                user.setEmail(email);
//                                user.setName(name);
//                                user.setPassword(password);
//                                user.setPhoneno(phoneno);
                                HashMap<String,Object> map= new HashMap<>();

                                map.put("name",name);
                                map.put("email",email);
                                map.put("phoneno",phoneno);
                                map.put("password",password);

                                myRef.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Intent i =new Intent(MainActivity.this,home.class);
                                            startActivity(i);
                                        }
                                        else
                                        {
                                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });


//                                FirebaseDatabase.getInstance().getReference("Users")
//                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                        .setValue(map).addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if(task.isSuccessful())
//                                        {
//                                            Toast.makeText(MainActivity.this,"Added success",Toast.LENGTH_LONG).show();
//                                        }
//                                        else
//                                        {
//                                            Toast.makeText(MainActivity.this,"Unsuccessful",Toast.LENGTH_LONG).show();
//                                        }
//                                    }
//                                });



                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Fail",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inti = new Intent(MainActivity.this,login.class);
                startActivity(inti);
            }
        });
    }
}
