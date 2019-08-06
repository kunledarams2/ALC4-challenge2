package com.e.alcchallenge2.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.alcchallenge2.Model.ItemModel;
import com.e.alcchallenge2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

public class AdminView extends AppCompatActivity {

    private EditText productname, productdescrip, price;
    private ProgressDialog dialog;
    private ImageView itemimage;
    private Toolbar toolbar;

    private DatabaseReference db;
    private StorageReference mStorageReference;

    private int Request_code = 1000;
    private Uri imageUri = null;
    private String uriImage;

    private ItemModel itemModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        productname = findViewById(R.id.pname);
        price = findViewById(R.id.pprice);
        productdescrip = findViewById(R.id.pdescription);
        itemimage = findViewById(R.id.productimage);
        toolbar = findViewById(R.id.toolbaseSave);
        setSupportActionBar(toolbar);

        db = FirebaseDatabase.getInstance().getReference().child("ALC4").child("Product").push();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        itemModel = new ItemModel();
        dialog = new ProgressDialog(this);
    }


    public void selectImage(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent, "Select Product Image"), Request_code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Request_code) {
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                itemimage.setImageURI(imageUri);

                StorageReference mstorageRef = mStorageReference.child("ACLImage").child(imageUri.getLastPathSegment());
                mstorageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {

                    uriImage = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                    itemModel.setImageuri(uriImage);
//                    Picasso.get().load(uriImage)
//                            .into(itemimage);
                });

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                saveProduct();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void saveProduct() {

        String pName = productname.getText().toString();
        String pPrice = price.getText().toString();
        String pDescription = productdescrip.getText().toString();

        itemModel.setProductdscrip(pDescription);
        itemModel.setProductname(pName);
        itemModel.setProductprice(pPrice);

        if(!TextUtils.isEmpty(pName) && !TextUtils.isEmpty(pPrice) && !TextUtils.isEmpty(pDescription) && imageUri !=null ){

            dialog=ProgressDialog.show(this,"Uploading ","Please Wait......",false,false);

            db.setValue(itemModel).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Intent intent= new Intent(AdminView.this, UserView.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(error->{
                Toast.makeText(AdminView.this, error.getMessage(),Toast.LENGTH_LONG).show();
            });
        }else Toast.makeText(this,"Please Enter All The Details",Toast.LENGTH_LONG).show();

    }
}
