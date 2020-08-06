package com.example.newproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {
    private Toolbar toolbar;
    private List<book> mbooks ;
    private RecyclerView mrecyclerview;

    TextView mtitle,mauthor,mdescription;
    ImageView mimageview;
    FirebaseDatabase database;
    DatabaseReference myref;
    ImageAdapter adapter;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mrecyclerview=findViewById(R.id.recycleviewid);
        mtitle=findViewById(R.id.textView4);
        mdescription=findViewById(R.id.textView5);
        mauthor=findViewById(R.id.textView6);
        mimageview = findViewById(R.id.imageView);
        database=FirebaseDatabase.getInstance();
        myref= database.getReference().child("Books");

        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mbooks =new ArrayList<book>();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsanpshot :snapshot.getChildren())
                {
                    String a=postsanpshot.child("author").getValue().toString();

                    String t=postsanpshot.child("title").getValue().toString();
                    String d=postsanpshot.child("description").getValue().toString();
                    String i=postsanpshot.child("url").getValue().toString();
                    Log.i("name",a);
                    book books = new book(a,t,d,i);
                    mbooks.add(books);
                }
                adapter = new ImageAdapter(getApplicationContext(),mbooks);
                mrecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(home.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.user)
        {
            Intent i = new Intent(getApplicationContext(),profile.class);
            startActivity(i);
        }
        else if(id == R.id.add_item) {
            Intent i = new Intent(getApplicationContext(),post.class);
            startActivity(i);
            Toast.makeText(this, "Add an item", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.search)
            Toast.makeText(getApplicationContext(),"Search here",Toast.LENGTH_LONG).show();
        else if(id == R.id.settings)
            Toast.makeText(getApplicationContext(),"Setting here!",Toast.LENGTH_LONG).show();
        else if(id == R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent isign = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(isign);
        }


        return true;
    }

}
