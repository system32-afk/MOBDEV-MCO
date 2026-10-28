package com.example.awake_mobdev_mco;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DailyMath extends AppCompatActivity {

    private List<mathProblem> problemList;
    private TextView probNumTv;
    private android.view.View progressSegment1;
    private android.view.View progressSegment2;
    private android.view.View progressSegment3;
    private int questionIndex = 0;

    private static class mathProblem {
        String equationText;
        int correctAnswer;

        mathProblem(String text, int answer){
            this.equationText = text;
            this.correctAnswer = answer;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily_math);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView backBtn = findViewById(R.id.backBtn);
        Button checkAnswerBtn = findViewById(R.id.checkAnswerBtn);
        EditText answerField = findViewById(R.id.answerField);
        TextView problemTv = findViewById(R.id.problemTv);

        probNumTv = findViewById(R.id.probNumTv);
        progressSegment1 = findViewById(R.id.progressSegment1);
        progressSegment2 = findViewById(R.id.progressSegment2);
        progressSegment3 = findViewById(R.id.progressSegment3);

        problemList = new ArrayList<>();
        problemList.add(new mathProblem("12 * 12 + 64",208));
        problemList.add(new mathProblem("15 × 4 - 10",60));
        problemList.add(new mathProblem("9 × 9 - 14",67));
        problemList.add(new mathProblem("120 ÷ 4 + 15", 45));
        problemList.add(new mathProblem("90 ÷ 3 + 20", 50));

        //randomizes questions
        Collections.shuffle(problemList);
        if (problemList.size() > 3){
            problemList = new ArrayList<>(problemList.subList(0,3));
        }
        else{
        }

        backBtn.setOnClickListener(v -> {
            finish();
        });

        loadCurrentQuestion(problemTv, answerField);

        checkAnswerBtn.setOnClickListener(v ->{
            String userAnswerStr = answerField.getText().toString().trim();
                if (userAnswerStr.isEmpty()){
                    Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                }
                else{
                    int userAnswer = Integer.parseInt(userAnswerStr);
                    int expectedAnswer = problemList.get(questionIndex).correctAnswer;

                    if (userAnswer == expectedAnswer){
                        updateProgressBarUI();
                        questionIndex = questionIndex +1;

                        if (questionIndex < problemList.size()){
                            loadCurrentQuestion(problemTv, answerField);
                            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "You have answered all the questions!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else{
                        Toast.makeText(this, "Incorrect! Try again.", Toast.LENGTH_SHORT).show();
                        answerField.setText("");
                    }
                }
        });
    }
        private void loadCurrentQuestion(TextView problemTv, EditText answerField){
            if (problemTv != null && !problemList.isEmpty()){
                problemTv.setText(problemList.get(questionIndex).equationText);
                answerField.setText("");

                int displayNum = questionIndex +1;
                if (probNumTv != null){
                    probNumTv.setText("Problem " + displayNum + " of " + problemList.size() + " ~ warm up your brain.");
                }
            }
    }

    private void updateProgressBarUI(){
        if (questionIndex == 0){
            if (progressSegment1!=null){
                progressSegment1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#FF7A00")));
            }
        }else if (questionIndex == 1){
            if (progressSegment2!=null){
                progressSegment2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#FF7A00")));
            }
        }else if (questionIndex == 2){
            if (progressSegment3!=null){
                progressSegment3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#FF7A00")));
            }
        }else {
            Toast.makeText(this, "You have answered all the questions!", Toast.LENGTH_SHORT).show();
        }
    }

}