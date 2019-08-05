package com.e.alcchallenge2.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.e.alcchallenge2.Adapter.ItemAdapter;
import com.e.alcchallenge2.Model.ItemModel;
import com.e.alcchallenge2.Model.ProductList;
import com.e.alcchallenge2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserView extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private UserView userView;

    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        toolbar=findViewById(R.id.toolbarv);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerview);

        LinearLayoutManager llm= new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        db= FirebaseDatabase.getInstance().getReference("ALC4");

        populateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.additems,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addItem:
                Intent intent= new Intent(this,AdminView.class);
                startActivity(intent);
                return true;

                default:
                    return super.onOptionsItemSelected(item);

        }
    }

    private void populateView() throws RuntimeException{
        db.child("Product").addValueEventListener(new ValueEventListener() {
            final   ArrayList<ProductList> productLists= new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                        String key=   snapshot.getKey();
                        ProductList productList= snapshot.getValue(ProductList.class);
                        productLists.add(productList);

                        Toast.makeText(UserView.this,key,Toast.LENGTH_LONG).show();
                    }
                    if(productLists.size()>0){
                        ItemAdapter itemAdapter= new ItemAdapter(productLists,UserView.this);
                        recyclerView.setAdapter(itemAdapter);
                    }


                }catch (Exception e){
                    Toast.makeText(UserView.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(UserView.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
