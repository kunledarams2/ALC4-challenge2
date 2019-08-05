package com.e.alcchallenge2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.e.alcchallenge2.View.SignIn;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void emailSignIn(View view) {

        Intent intent =  new Intent(this, SignIn.class);
        startActivity(intent);

    }
}
