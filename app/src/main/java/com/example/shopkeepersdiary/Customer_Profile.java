package com.example.shopkeepersdiary;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Customer_Profile extends AppCompatActivity {

    private EditText name,mob,add;
    private Button done;
    private ImageButton uploadphoto;
    private FirebaseAuth mAuth;
    private FirebaseUser u;
    private DatabaseReference mref;
    private CircleImageView imageView;
    private static final int GALLERY_INTENT = 2;
    Intent i;
    String uid;
    private StorageReference mstorageref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__profile);

        getSupportActionBar().setTitle("  Edit Customer Profile");

        name=findViewById(R.id.cname);
        mob=findViewById(R.id.editText);
        add=findViewById(R.id.editText2);
        done=findViewById(R.id.cdone);
        imageView=findViewById(R.id.cprof);
        uploadphoto=findViewById(R.id.buttonupload);
        i=getIntent();
        uid=i.getStringExtra("userid");
        mAuth=FirebaseAuth.getInstance();
        mstorageref= FirebaseStorage.getInstance().getReference();

        mref=FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid().toString()).child("customers").child(uid);
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("username").getValue(String.class));
                mob.setText(dataSnapshot.child("mob").getValue(String.class));
                add.setText(dataSnapshot.child("address").getValue(String.class));
                String img=dataSnapshot.child("prof").getValue(String.class);
                Picasso.with(Customer_Profile.this).load(img).fit().centerCrop().into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mref.child("username").setValue(name.getText().toString());
                mref.child("mob").setValue(mob.getText().toString());
                mref.child("address").setValue(add.getText().toString());
                Intent intent=new Intent(Customer_Profile.this,info.class);
                intent.putExtra("userid",uid);
                startActivity(intent);
                finish();
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode== GALLERY_INTENT && resultCode== RESULT_OK)
        {




            Uri uri=data.getData();

            UploadTask file=mstorageref.child(uid).child("profile.jpg").putFile(uri);

            StorageReference filepath = mstorageref.child(uid).child("profile.jpg");



            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'

                    Picasso.with(Customer_Profile.this).load(uri.toString()).fit().centerCrop().into(imageView);
                    // Toast.makeText(info.this,uri.toString(),Toast.LENGTH_LONG).show();

                    mref=FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid().toString()).child("customers").child(uid);
                    mref.child("prof").setValue(uri.toString());
                    //  imageView.setImageURI(uri);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Toast.makeText(Customer_Profile.this,"Oops...Something went wrong",Toast.LENGTH_LONG).show();


                }
            });




        }

    }
}
