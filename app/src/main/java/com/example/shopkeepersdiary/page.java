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
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class page extends AppCompatActivity {


    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<CustomerView> mUsers;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        getSupportActionBar().setTitle("  Customer List");

        searchView=findViewById(R.id.searchView);



        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUsers=new ArrayList<>();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        mAuth = FirebaseAuth.getInstance();

        readUsers();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                userAdapter.getFilter().filter(newText);

                return false;
            }
        });




    }

    private void readUsers() {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid().toString()).child("customers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {

                    Customer u=snapshot.getValue(Customer.class);
                    CustomerView user=new CustomerView(snapshot.getKey(),u.getUsername(),u.getMob(),u.getProf());


                    mUsers.add(user);
                }

                userAdapter=new UserAdapter(page.this,mUsers);
                recyclerView.setAdapter(userAdapter);
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.id_logout) {

            progressDialog.setMessage("Signing Out");
            progressDialog.show();
            mAuth.signOut();
            finish();
            startActivity(new Intent(page.this, login.class));
            return true;

        }
        else if(id==R.id.id_customer) {
            startActivity(new Intent(page.this,AddCustomer.class));
            return true;
        }
        else if(id==R.id.id_profile) {
            startActivity(new Intent(page.this,MyProfile.class));
            return true;
        }

        return true;
    }




    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
