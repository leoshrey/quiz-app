package com.leoshrey.testquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.leoshrey.testquiz.databinding.ActivityQuizBinding;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    ActivityQuizBinding binding;
    ArrayList<Questions> questions;
    int index = 0;
    Questions question;
    CountDownTimer timer;
    FirebaseFirestore data;
    int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        questions = new ArrayList<>();
        data = FirebaseFirestore.getInstance();

        final String subjectId = getIntent().getStringExtra("subjectId");

        Random random = new Random();
        final int rand = random.nextInt(5);



        data.collection("SubjectCategories")
                .document(subjectId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index", rand)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.getDocuments().size() < 5){
                    data.collection("SubjectCategories")
                            .document(subjectId)
                            .collection("questions")
                            .whereLessThanOrEqualTo("index", rand)
                            .orderBy("index")
                            .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                                    Questions question = snapshot.toObject(Questions.class);
                                    questions.add(question);
                                }
                                setNextQuestion();
                        }
                    });
                }
                else {
                    for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                        Questions question = snapshot.toObject(Questions.class);
                        questions.add(question);
                    }
                    setNextQuestion();
                }
            }
        });


//        questions.add(new Questions("The correct sequence of reproductive stages seen in flowering plants is:",
//                "gametes, zygote, embryo, seedling",
//                "zygote, gametes, embryo, seedling",
//                "seedling, embryo, zygote, gametes",
//                "gametes, embryo, zygote, seedling",
//                "gametes, zygote, embryo, seedling"));
//        questions.add(new Questions("Keeping the potential difference constant, the resistance of the circuit is halved. The current will become:",
//                "One-fourth",
//                "Four times",
//                "Half",
//                "Double",
//                "Double"));
//        questions.add(new Questions("It is necessary to balance a chemical equation in order to satisfy the law of:\n",
//                "Conservation of motion",
//                "Conservation of momentum",
//                "Conservation of energy",
//                "Conservation of mass",
//                "Conservation of mass"));
//        questions.add(new Questions("In human males, the testes lie in the scrotum, because it helps in the",
//                "process of mating",
//                "formation of sperm",
//                "easy transfer of gametes",
//                "all of these",
//                "formation of sperm"));
//        questions.add(new Questions("The filament of bulb is made of which of the following materials?",
//                "Gold",
//                "Silver",
//                "Platinum",
//                "Tungsten",
//                "Tungsten"));

        resetTimer();
        setNextQuestion();

        binding.exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizActivity.this, MainActivity.class));
                finishAffinity();
            }
        });
    }

    void resetTimer(){
        timer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    void setNextQuestion(){
        if (timer != null){
            timer.cancel();
        }
        timer.start();

        if(index < questions.size()){
            binding.questionNumber.setText(String.format("%d/%d", (index+1), questions.size()));
            question = questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOpt1());
            binding.option2.setText(question.getOpt2());
            binding.option3.setText(question.getOpt3());
            binding.option4.setText(question.getOpt4());

        }
    }

    void checkAnswer(TextView textView){
        String selectedAnswer = textView.getText().toString();
        if(selectedAnswer.equals(question.getAnswer())){
            correctAnswers++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }

    void reset(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

    void showAnswer(){
        if(question.getAnswer().equals(binding.option1.getText().toString())){
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if(question.getAnswer().equals(binding.option2.getText().toString())){
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if(question.getAnswer().equals(binding.option3.getText().toString())){
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if(question.getAnswer().equals(binding.option4.getText().toString())){
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
    }

    public void onClickOfNext(View view){
        switch (view.getId()){

            case R.id.option1:
            case R.id.option2:
            case R.id.option3:
            case R.id.option4:
                if (timer != null){
                    timer.cancel();
                }
                TextView selected = (TextView) view;
                checkAnswer(selected);
                break;

            case R.id.nextButton:
                reset();
                if(index <= questions.size()) {
                    index++;
                    setNextQuestion();
                    break;
                }
                else {
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("correct", correctAnswers);
                    intent.putExtra("total", questions.size());
                    startActivity(intent);
                }
        }
    }
}