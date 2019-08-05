package com.e.alcchallenge2.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.e.alcchallenge2.Model.UserDetails;
import com.e.alcchallenge2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    private EditText fname, lname, email, password, confrimpassword;
    private ImageButton revbtn;
    private TextView indicator;
    private ProgressDialog dialog;

    private DatabaseReference db;
    private UserDetails user;
    private FirebaseAuth mAuth;

    private boolean reviewpassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confrimpassword = findViewById(R.id.comfirmP);
        revbtn = findViewById(R.id.reviewpass);
        indicator = findViewById(R.id.check);
        dialog = new ProgressDialog(this);

        db = FirebaseDatabase.getInstance().getReference("ALC4").child("Users");
        mAuth = FirebaseAuth.getInstance();
        user = new UserDetails();

        setupPassword();
    }


    public void newRegistr(View view) {

        String mfirstname = fname.getText().toString().intern();
        String mlastname = lname.getText().toString().intern();
        String memail = email.getText().toString();
        String mpassword = password.getText().toString();
        String mconfirmpass = confrimpassword.getText().toString();

        if (TextUtils.isEmpty(mfirstname)) {
            toastMessage("Please Enter Your Firstname");
        } else if (TextUtils.isEmpty(mlastname)) {
            toastMessage("Please Enter Your Lastname");
        } else if (TextUtils.isEmpty(memail)) {
            toastMessage("Please Enter Your Email ");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(memail).matches()) {
            toastMessage("Invalid Email");
        }

        if (!TextUtils.isEmpty(mfirstname) && !TextUtils.isEmpty(mfirstname) &&
                !TextUtils.isEmpty(mfirstname) && Patterns.EMAIL_ADDRESS.matcher(memail).matches()
                && !TextUtils.isEmpty(mpassword) && !TextUtils.isEmpty(mconfirmpass)) {
            user.setMemail(memail);
            String name = mfirstname + mlastname;
            user.setMname(name);
            user.setMpassword(mpassword);
            dialog=ProgressDialog.show(this, "Sign Up", "Please Wait......",false,false);
            mAuth.createUserWithEmailAndPassword(memail, mpassword).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {

                    String currentUser = mAuth.getCurrentUser().getUid();
                    DatabaseReference dbUser = db.child(currentUser);
                    dbUser.setValue(user);

                    dialog.dismiss();
                    Intent intent= new Intent(this, UserView.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(e -> {
                dialog.dismiss();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            });


        }
    }

    private void setupPassword() {

        revbtn.setOnClickListener(view -> {
            if (reviewpassword) {
                password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                password.setSelection(password.length());
                reviewpassword = false;
                revbtn.setImageResource(R.drawable.ic_eye_black_24dp);

            } else {
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                password.setSelection(password.length());
                reviewpassword = true;
                revbtn.setImageResource(R.drawable.ic_green_eye_black_24dp);
            }
        });

        confrimpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String confirm = editable.toString();
                String passwor = password.getText().toString();
                if (!TextUtils.isEmpty(confirm) && !TextUtils.isEmpty(passwor)) {
                    if (passwor.equals(confirm)) {
                        indicator.setBackgroundResource(R.drawable.ic_correct_green_24dp);
                    } else {
                        indicator.setBackgroundResource(R.drawable.ic_wrong_black_24dp);
                    }
                }


            }
        });
    }


    private void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
