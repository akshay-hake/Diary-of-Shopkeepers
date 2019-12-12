package com.example.shopkeepersdiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowHistory extends AppCompatActivity {

    private RecyclerView recyclerView;

    private HistoryAdapter historyAdapter;
    private List<HistoryView> mUsers;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    Intent i;
    String uid;
    int hn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);

        getSupportActionBar().setTitle("  History");
        i=getIntent();
        uid=i.getStringExtra("userid");

        recyclerView=findViewById(R.id.history_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUsers=new ArrayList<>();


        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

      /*  DatabaseReference ref=FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid().toString()).child("customers").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hn=dataSnapshot.child("historytotal").getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        readUsers();
    }

    private void readUsers() {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid().toString()).child("customers").child(uid).child("history");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                /*for(int i=hn;i>0;i--) {
                    dataSnapshot.ge
                   history u= dataSnapshot.child(String.valueOf(i)).getValue(history.class);
                    HistoryView user=new HistoryView("Rs : ".concat(String.valueOf(u.getAmount())),u.getStatus(),"Date : ".concat(u.getDate()),"Time : ".concat(u.getTime()),"Pending Money : ".concat(String.valueOf(u.getRemaining())),"Reason : ".concat(u.getReason()));

                    mUsers.add(user);
                }*/

                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {

                    history u=snapshot.getValue(history.class);

                    HistoryView user=new HistoryView("Rs : ".concat(String.valueOf(u.getAmount())),u.getStatus(),"Date : ".concat(u.getDate()),"Time : ".concat(u.getTime()),"Pending Money : ".concat(String.valueOf(u.getRemaining())),"Reason : ".concat(u.getReason()));


                    mUsers.add(user);
                }

                historyAdapter=new HistoryAdapter(ShowHistory.this,mUsers);
                recyclerView.setAdapter(historyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delhistory,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.delhistory) {

            databaseReference=FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid().toString()).child("customers").child(uid).child("history");
            databaseReference.removeValue();
            int zero=0;
            databaseReference=FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid().toString()).child("customers").child(uid);
            databaseReference.child("historytotal").setValue(zero);


            return true;

        }


        return true;
    }
}
