package com.example.shopkeepersdiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.shopkeepersdiary.R.layout.activity_my_profile;

public class MyProfile extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseStorage storage;
    private DatabaseReference mref,mref2;
    private StorageReference mstorageref;
    private TextView tname;
    private TextView temail;
    private TextView tmob;
    private TextView mtc,mtb;

    private ImageButton uploadphoto,editb;
    private static final int GALLERY_INTENT = 2;
    private CircleImageView imageView;
    private ProgressDialog progressDialog;
    private boolean flag=false;
    int mytotal;
    String name,email,mob,t1,t2,img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_my_profile);

        getSupportActionBar().setTitle("  My Profile");

        mAuth=FirebaseAuth.getInstance();
        mref=FirebaseDatabase.getInstance().getReference();
        tname=(TextView) findViewById(R.id.name);
        temail=(TextView) findViewById(R.id.email);
        tmob=(TextView) findViewById(R.id.mob);
        mtc=findViewById(R.id.mtc);
        mtb=findViewById(R.id.mtb);
        // t8=(TextView) findViewById(R.id.textView8);
        uploadphoto=(ImageButton) findViewById(R.id.buttonupload);

        imageView=(CircleImageView) findViewById(R.id.mprof) ;
        mstorageref= FirebaseStorage.getInstance().getReference();
        progressDialog=new ProgressDialog(this);
        storage = FirebaseStorage.getInstance();

        uploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
                String uid=mAuth.getUid();

            }
        });



       mref2=FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid().toString()).child("customers");
       mref2.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               mytotal=0;

               for(DataSnapshot snapshot:dataSnapshot.getChildren()) {


                   Customer customer=snapshot.getValue(Customer.class);
                   mytotal+=customer.getTotal();
               }
               mtb.setText("Total Borrowing : Rs. ".concat(String.valueOf(mytotal)));

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                progressDialog.setMessage("Uploading");
                progressDialog.show();

                String uid=mAuth.getUid().toString();
                name=dataSnapshot.child("users").child(uid).child("username").getValue(String.class);

                email=dataSnapshot.child("users").child(uid).child("email1").getValue(String.class);
                mob=dataSnapshot.child("users").child(uid).child("mob").getValue(String.class);
                img=dataSnapshot.child("users").child(uid).child("prof").getValue(String.class);
                t1=String.valueOf(dataSnapshot.child("users").child(uid).child("tc").getValue(Integer.class));

                tname.setText(name);
                temail.setText(email);
                tmob.setText(mob);
                mtc.setText("Total Customers : ".concat(t1));

                Picasso.with(MyProfile.this).load(img).fit().centerCrop().into(imageView);


                progressDialog.cancel();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // tname.setText(name);
       // temail.setText(email);
      //  tmob.setText(mob);
      //  mtc.setText("Total Customers : ".concat(t1));
     //   mtb.setText("Total Borrowing : ".concat(String.valueOf(mytotal)));
     //   Picasso.with(MyProfile.this).load(img).fit().centerCrop().into(imageView);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== GALLERY_INTENT && resultCode== RESULT_OK)
        {

            progressDialog.setMessage("Uploading");
            progressDialog.show();


            Uri uri=data.getData();

            UploadTask file=mstorageref.child(mAuth.getUid()).child("profile.jpg").putFile(uri);

            StorageReference filepath = mstorageref.child(mAuth.getUid()).child("profile.jpg");



            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'

                    Picasso.with(MyProfile.this).load(uri.toString()).fit().centerCrop().into(imageView);
                    mref=FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid().toString());
                    mref.child("prof").setValue(uri.toString());
                    //  imageView.setImageURI(uri);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors

                    Toast.makeText(MyProfile.this,"Oops...Something went wrong",Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
            });

            progressDialog.cancel();


        }
    }
}
