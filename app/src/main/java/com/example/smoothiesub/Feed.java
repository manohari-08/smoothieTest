package com.example.smoothiesub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Feed extends AppCompatActivity {
    Button btnProf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        btnProf = findViewById(R.id.btnProf);

        btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCusprofile();
            }
        });

    }

    public void openCusprofile(){
        Intent  i = new Intent(this,CustomerProfile.class);
        startActivity(i);
    }
}