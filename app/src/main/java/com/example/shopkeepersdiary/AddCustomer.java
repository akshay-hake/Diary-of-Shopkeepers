package com.example.shopkeepersdiary;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AddCustomer extends AppCompatActivity {

    private EditText name,address,mob;
    private Button add;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mref, mref2;
    private StorageReference mstorageRef;
    int n;
    String uname,em,mb,pf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        getSupportActionBar().setTitle("  Add Customer");

        name=findViewById(R.id.editname);
        address=findViewById(R.id.editaddress);
        mob=findViewById(R.id.editmob);
        add=findViewById(R.id.btnadd);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        mref=FirebaseDatabase.getInstance().getReference();
        mref2=mref.child("users");
        mstorageRef= FirebaseStorage.getInstance().getReference();

        firebaseDatabase=FirebaseDatabase.getInstance();


        mref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DataSnapshot dsp=dataSnapshot.child(mAuth.getUid().toString());
                n=dsp.child("tc").getValue(Integer.class);
                uname=dsp.child("username").getValue(String.class);
                em=dsp.child("email1").getValue(String.class);
                mb=dsp.child("mob").getValue(String.class);
                pf=dsp.child("prof").getValue(String.class);
              //  Toast.makeText(AddCustomer.this, uname, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1,s2,s3,s4;
                s1=name.getText().toString();
                s2=address.getText().toString();
                s3=mob.getText().toString();
                s4="https://firebasestorage.googleapis.com/v0/b/shopkeeper-sdiary.appspot.com/o/default%2Findex.png?alt=media&token=a5c487f2-ba9f-420a-9e50-cb2662fd3eb4";
                int t=0;
                Customer c=new Customer(s1,s2,s3,s4,t,0);
                String id=mAuth.getUid();
                if(n==0) {
                    mref.child("users").child(id).child("customers").push();
                }
                int s=n+1;
                mref.child("users").child(id).child("tc").setValue(s);
               // User new1=new User(uname,em,mb,pf,s);
               // mref.child("users").child(id).setValue(new1);
                mref.child("users").child(id).child("customers").push().setValue(c);
                Toast.makeText(AddCustomer.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                finish();




            }
        });


    }


}
