package com.leoshrey.testquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.leoshrey.testquiz.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {


    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore data;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        data = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);

        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, name;
                email = binding.yourEmail.getText().toString();
                password = binding.yourPassword.getText().toString();
                name = binding.yourName.getText().toString();

                dialog.show();
                dialog.setMessage("Please wait, Your Account is being created...");

                final UserClass user = new UserClass(name, email, password);

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             String uid = task.getResult().getUser().getUid();
                             dialog.dismiss();
                             data.collection("user")
                                     .document(uid)
                                     .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful()){
                                         startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                         finish();
                                     }
                                     else {
                                         Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
                             Toast.makeText(SignUpActivity.this, "success", Toast.LENGTH_SHORT).show();
                         }
                         else{
                             dialog.setMessage("Your account cannot be created, please try Again");
                             dialog.dismiss();
                             Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                         }
                    }
                });
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}