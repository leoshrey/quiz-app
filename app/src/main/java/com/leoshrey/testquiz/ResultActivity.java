package com.leoshrey.testquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.leoshrey.testquiz.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    int reward = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correctAnswers = getIntent().getIntExtra("correct", 0);
        int totalQuestions = getIntent().getIntExtra("total", 0);

        int points = correctAnswers * reward;

        binding.score.setText(String.format("%d/%d", correctAnswers, totalQuestions));

        if(correctAnswers < 3 && correctAnswers > 0){
            binding.congoText.setText("Good, but you need to work hard");
        }
        else if(correctAnswers == 0){
            binding.congoText.setText("Oops!, you need to work on your science");
        }

        FirebaseFirestore data = FirebaseFirestore.getInstance();

        data.collection("user")
                .document(FirebaseAuth.getInstance().getUid())
                .update("points", FieldValue.increment(points));

        binding.playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finishAffinity();
            }
        });

        binding.leaderboardButton.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.resultLayout, new LeaderboardFragment());
                transaction.commit();
//                finishAffinity();
            }
        }));

    }
}