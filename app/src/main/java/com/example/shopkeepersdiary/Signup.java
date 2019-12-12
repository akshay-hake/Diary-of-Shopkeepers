package com.example.shopkeepersdiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Signup extends AppCompatActivity {

    EditText email;
    EditText pass;
    EditText mob;
    EditText name1;
    Button signup;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mref;
    private StorageReference mstorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setTitle("  SignUp");

        email=(EditText) findViewById(R.id.editTextemail);
        pass=(EditText) findViewById(R.id.editTextpass);
        mob=(EditText) findViewById(R.id.editTextmob);
        name1=(EditText) findViewById(R.id.editTextname);
        signup=(Button) findViewById(R.id.button);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        mref=FirebaseDatabase.getInstance().getReference();
        mstorageRef= FirebaseStorage.getInstance().getReference();

        firebaseDatabase=FirebaseDatabase.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email1=email.getText().toString();
                String pass1=pass.getText().toString();

                if(TextUtils.isEmpty(email1))
                {
                    Toast.makeText(Signup.this,"Please Enter Email ", Toast.LENGTH_LONG).show();
                    email.setBackgroundResource(R.drawable.redborder);
                    pass.setBackgroundResource(R.drawable.border);

                    return;
                }
                if(TextUtils.isEmpty(pass1))
                {
                    Toast.makeText(Signup.this,"Enter Password",Toast.LENGTH_LONG).show();
                    pass.setBackgroundResource(R.drawable.redborder);
                    email.setBackgroundResource(R.drawable.border);
                    return;
                }

                pass.setBackgroundResource(R.drawable.border);
                email.setBackgroundResource(R.drawable.border);

                progressDialog.setMessage("Please Wait");
                progressDialog.show();


                mAuth.createUserWithEmailAndPassword(email1,pass1).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(Signup.this,"SignUp failed",Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                            return;

                        }
                        else
                        {
                            Toast.makeText(Signup.this,"Successfull",Toast.LENGTH_LONG).show();
                            String s1=name1.getText().toString();
                            String s2=mob.getText().toString();
                            String prof="https://firebasestorage.googleapis.com/v0/b/shopkeeper-sdiary.appspot.com/o/default%2Findex.png?alt=media&token=a5c487f2-ba9f-420a-9e50-cb2662fd3eb4";

                            //   writeNewUser("2",s1,email1,s2);
                            User user = new User(s1, email1,s2,prof,0);


                            String mGroupId=mAuth.getUid();

                            mref.child("users").child(mGroupId).setValue(user);





                        }


                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Signup.this,login.class);
        startActivity(a);
        finish();
    }

}

