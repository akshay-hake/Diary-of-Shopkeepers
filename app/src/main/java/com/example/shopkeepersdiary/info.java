package com.example.shopkeepersdiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class info extends AppCompatActivity {

    private CircleImageView prof;
    private ImageView Rs;
    private TextView name,mob,amount,cadd;
    private Button addm,recievem,history;
    private ImageButton uploadphoto;
    private static final int GALLERY_INTENT = 2;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    private DatabaseReference mref,mref2;
    private StorageReference mstorageref;
    private ProgressDialog progressDialog;
    Intent i;
    String uid;
    int tc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        getSupportActionBar().setTitle("  Customer Profile");

        prof=findViewById(R.id.cprof);
        name=findViewById(R.id.cname);
        mob=findViewById(R.id.cmob);
        amount=findViewById(R.id.ammount);
        cadd=findViewById(R.id.editText3);
        addm=findViewById(R.id.btnaddm);
        recievem=findViewById(R.id.btnrecieve);
        history=findViewById(R.id.btnhistory);
        uploadphoto=(ImageButton) findViewById(R.id.btnuploadphoto);
        Rs=findViewById(R.id.imageView7);

        mAuth=FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference();
        mref2=FirebaseDatabase.getInstance().getReference();
        mstorageref= FirebaseStorage.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        progressDialog=new ProgressDialog(info.this);
        i=getIntent();
        uid=i.getStringExtra("userid");
        mref=mref.child("users").child(mAuth.getUid().toString()).child("customers").child(uid);

        mref2=mref2.child("users").child(mAuth.getUid().toString());
        mref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tc=dataSnapshot.child("tc").getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(info.this,addmoney.class);
                intent.putExtra("userid",uid);
                startActivity(intent);
               // finish();
            }
        });

        recievem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(info.this,recievemoney.class);
                intent.putExtra("userid",uid);
                startActivity(intent);
               // finish();

            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(info.this,ShowHistory.class);
                intent.putExtra("userid",uid);
                startActivity(intent);

            }
        });

        uploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);


            }
        });



        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String img=dataSnapshot.child("prof").getValue(String.class);
                String smoney;
                Uri u= Uri.parse(img);
                Picasso.with(info.this).load(u.toString()).into(prof);
                name.setText(dataSnapshot.child("username").getValue(String.class));
                mob.setText(dataSnapshot.child("mob").getValue(String.class));
                cadd.setText(dataSnapshot.child("address").getValue(String.class));
                int t=dataSnapshot.child("total").getValue(Integer.class);
                if(t==0)
                {
                    amount.setText("NIL");
                    amount.setTextColor(Color.rgb(0,0,255));


                }
                else  amount.setText(String.valueOf(t));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        progressDialog.setMessage("Uploading");
        progressDialog.show();

        if(requestCode== GALLERY_INTENT && resultCode== RESULT_OK)
        {




            Uri uri=data.getData();

            UploadTask file=mstorageref.child(uid).child("profile.jpg").putFile(uri);

            StorageReference filepath = mstorageref.child(uid).child("profile.jpg");



            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'

                    Picasso.with(info.this).load(uri.toString()).fit().centerCrop().into(prof);
                   // Toast.makeText(info.this,uri.toString(),Toast.LENGTH_LONG).show();

                    mref=FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid().toString()).child("customers").child(uid);
                    mref.child("prof").setValue(uri.toString());
                    //  imageView.setImageURI(uri);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Toast.makeText(info.this,"Oops...Something went wrong",Toast.LENGTH_LONG).show();


                }
            });




        }
        progressDialog.cancel();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.id_delcustomer) {

            DatabaseReference ref=FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid().toString()).child("customers").child(uid);
            ref.removeValue();
            ref=FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid().toString());
            tc=tc-1;
            ref.child("tc").setValue(tc);
            StorageReference storageReference=FirebaseStorage.getInstance().getReference(uid);
            storageReference.delete();

            startActivity(new Intent(info.this,page.class));
            finish();


        } else if(id==R.id.id_edit) {

            Intent intent=new Intent(info.this,Customer_Profile.class);
            intent.putExtra("userid",uid);
            startActivity(intent);
        }



        return true;
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(info.this,page.class);
        startActivity(a);
        finish();
    }
}
