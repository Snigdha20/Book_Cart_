package com.example.newproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private EditText memail,mpassword;
    private Button mlogin;
    private FirebaseAuth mAuth;
    private TextView msignin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        memail=findViewById(R.id.Lemail);
        mpassword=findViewById(R.id.Lpassword);
        mlogin=findViewById(R.id.Login);
        mAuth=FirebaseAuth.getInstance();
        msignin =findViewById(R.id.textView);
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=memail.getText().toString();
                String password=mpassword.getText().toString();
                if(email.isEmpty()|| password.isEmpty() )
                {
                    Toast.makeText(login.this,"fields are empty",Toast.LENGTH_LONG).show();
                }
                else if(!(email.isEmpty()|| password.isEmpty()))
                {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Intent intent = new Intent(login.this,home.class);
                                        startActivity(intent);

                                    }
                                    else
                                    {
                                        Toast.makeText(login.this,"login error",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });
        msignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent id =new Intent(login.this,MainActivity.class);
                startActivity(id);
            }
        });
    }
}
