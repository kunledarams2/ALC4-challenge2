package com.e.alcchallenge2.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.e.alcchallenge2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mpassword, memail;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        memail=findViewById(R.id.email);
        mpassword=findViewById(R.id.password);
        dialog=new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();
    }

    public void mLogin(View view) {

        String email= memail.getText().toString();
        String password= mpassword.getText().toString();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            dialog= ProgressDialog.show(this, "Login","Please wait.....",false,false);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Intent intent = new Intent(SignIn.this, UserView.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }

            }).addOnFailureListener(error->{
                dialog.dismiss();
                Toast.makeText(this, error.getMessage(),Toast.LENGTH_LONG).show();
            });
        }
        else Toast.makeText(this, "Please Enter Your Details",Toast.LENGTH_LONG).show();


    }

    public void createNewUser(View view) {

        Intent intent = new Intent(this,Signup.class);
        startActivity(intent);
    }
}
