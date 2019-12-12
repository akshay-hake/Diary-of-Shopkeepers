package com.example.shopkeepersdiary;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class addmoney extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView name,date,timed;
    private EditText reason,money;
    Button confirm;
    Intent i;
    String uid;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    private DatabaseReference mref,mref2;
    private StorageReference mstorageref;
    private ProgressDialog progressDialog;
    String d,a,tm;
    int x,ht,t,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmoney);

        getSupportActionBar().setTitle("  Add Money");

        name = findViewById(R.id.aname);
        date = findViewById(R.id.date);
        money = findViewById(R.id.addamount);
        confirm=findViewById(R.id.btnaddm);
        timed=findViewById(R.id.time);
        reason=findViewById(R.id.areason);

        i = getIntent();
        uid = i.getStringExtra("userid");

        mAuth=FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference();
        mref=mref.child("users").child(mAuth.getUid().toString()).child("customers").child(uid);

        i = getIntent();
        uid = i.getStringExtra("userid");

        Calendar m=Calendar.getInstance();
        String cd=DateFormat.getDateInstance().format(m.getTime());
        date.setText(cd);

        Thread th=new Thread() {

            @Override
            public void run() {
                long date=System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm a");
                tm=simpleDateFormat.format(date);
                timed.setText(tm);

            }
        };
        th.start();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker=new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(),"date picker");
            }
        });



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d=date.getText().toString();
                a=money.getText().toString();

                if(TextUtils.isEmpty(d))
                {
                    Toast.makeText(addmoney.this,"Please Enter Date ", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(a))
                {
                    Toast.makeText(addmoney.this,"Enter Amount",Toast.LENGTH_LONG).show();
                    return;
                }




                y =Integer.parseInt(a);
                x=t+y;
                ht+=1;

                mref.child("total").setValue(x);
                mref.child("historytotal").setValue(ht);
                history h=new history(d,Integer.parseInt(a),"Added",tm,x,reason.getText().toString());
                mref.child("history").child(String.valueOf(ht)).setValue(h);

                Toast.makeText(addmoney.this,String.valueOf(x),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(addmoney.this,info.class);
                intent.putExtra("userid",uid);

                startActivity(intent);
                finish();

            }
        });

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                name.setText(dataSnapshot.child("username").getValue(String.class));

                t = dataSnapshot.child("total").getValue(Integer.class);


                ht=dataSnapshot.child("historytotal").getValue(Integer.class);


             //   int n=Integer.parseInt(money.getText().toString());
              //  int m=t+n;
              //  mref.child("total").setValue(m);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }


        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        String curdate= DateFormat.getDateInstance().format(c.getTime());
        date.setText(curdate);

    }
}
