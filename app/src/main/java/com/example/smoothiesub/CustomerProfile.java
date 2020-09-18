package com.example.smoothiesub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class CustomerProfile extends AppCompatActivity {

    TextView name , email , mobile;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        name = findViewById(R.id.txtprofileName);
        email = findViewById(R.id.txtPorfEmail);
        mobile = findViewById(R.id.txtProfMobile);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);//retrieve data from database
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name.setText(documentSnapshot.getString("Name"));
                mobile.setText(documentSnapshot.getString("Mobile"));
                email.setText(documentSnapshot.getString("Email"));

            }
        });


    }

//    public void logout(View view){
//        FirebaseAuth.getInstance().signOut(); //logout
//        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//        finish();
//    }


}