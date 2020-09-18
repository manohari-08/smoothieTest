package com.example.smoothiesub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CustomerRegister extends AppCompatActivity {
    public static final String TAG = "TAG";
    Button btnRegister;
    EditText txtName , txtEmail,txtMobile,txtPass;
    TextView txtAsaShop , loginAlternative;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        btnRegister = findViewById(R.id.btnReg);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtMobile = findViewById(R.id.txtProfMobile);
        txtPass = findViewById(R.id.txtPass);
        txtAsaShop= findViewById(R.id.txtAsaShop);
        loginAlternative = findViewById(R.id.loginAlternative);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = txtEmail.getText().toString().trim();
                String password = txtPass.getText().toString().trim();
                final String name = txtName.getText().toString();
                final String mobile = txtMobile.getText().toString();

                if(TextUtils.isEmpty(email)){
                    txtEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    txtPass.setError("Password is required");
                    return;
                }

                //Register user

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CustomerRegister.this,"user created",Toast.LENGTH_SHORT).show();

                            userID = fAuth.getCurrentUser().getUid(); //to retrieve uid of the current user
                            DocumentReference documentReference = fStore.collection("users").document(userID);  //create collection as users and create document to user
                            Map<String,Object>  user = new HashMap<>();
                            user.put("Name",name);
                            user.put("Mobile",mobile);
                            user.put("Email",email);

                            //insert data into cloud database
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess: user profile is created for " +userID);


                                }
                            });





                            startActivity(new Intent(getApplicationContext(),Feed.class)); // should navigate to feed or profile

                        }
                        else{
                            Toast.makeText(CustomerRegister.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        loginAlternative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));   //navigation to the login activity
            }
        });


        //need to implement navigation to the shop register


    }
}