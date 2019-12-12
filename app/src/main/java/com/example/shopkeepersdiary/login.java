package com.example.shopkeepersdiary;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;

public class login extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private EditText email;
    private EditText pword;
    private Button login,signup;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("  Login and Register");

        email=findViewById(R.id.email);
        pword=findViewById(R.id.pword);
        login=findViewById(R.id.btnlogin);
        signup=findViewById(R.id.btncreate);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,Signup.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = email.getText().toString();
                String pass1 = pword.getText().toString();

                if (TextUtils.isEmpty(email1)) {

                    Toast.makeText(login.this, "Enter Email", Toast.LENGTH_LONG).show();
                    email.setBackgroundResource(R.drawable.redborder);
                    pword.setBackgroundResource(R.drawable.border);

                } else if(TextUtils.isEmpty(pass1)) {
                    Toast.makeText(login.this, "Enter Password", Toast.LENGTH_LONG).show();

                    pword.setBackgroundResource(R.drawable.redborder);
                    email.setBackgroundResource(R.drawable.border);


                }
                else {

                    pword.setBackgroundResource(R.drawable.border);
                    email.setBackgroundResource(R.drawable.border);



                    progressDialog.setMessage("Logging In");
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email1, pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (!task.isSuccessful()) {
                                Toast.makeText(login.this, "Sign In Problem", Toast.LENGTH_LONG).show();
                                progressDialog.cancel();
                            }





                        }


                    });


                }
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {



                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    Intent b;
                    b = new Intent(login.this, page.class);
                    startActivity(b);
                }
            }
        };
    }





    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}
