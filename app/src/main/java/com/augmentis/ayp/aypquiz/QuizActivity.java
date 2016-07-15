package com.augmentis.ayp.aypquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final int REQUEST_CHEATED = 7628 ;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button previousButton;
    private TextView questionText;
    private Button cheatButton;
    private boolean isCheated;

    Question[] questions = new Question[]{
            new Question(R.string.question_1_nile, true),
            new Question(R.string.question_2_Pro, true),
            new Question(R.string.question_3_math, false),
            new Question(R.string.question_4_mars, false)
    };

    private int currentIndex;

    private static final String TAG = "AYPQUIZ";
    private static final String INDEX = "INDEX";

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "On Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"On destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"On pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"On resume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"On start");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "State is saving");
        outState.putInt(INDEX, currentIndex);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        trueButton = (Button) findViewById(R.id.true_button);
        falseButton = (Button) findViewById(R.id.false_button);
        nextButton = (Button) findViewById(R.id.next_button);
        previousButton = (Button) findViewById(R.id.previous_button);
        cheatButton = (Button) findViewById(R.id.cheat_button);


        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(INDEX, 0);
        }else{
            currentIndex = 0;
        }

        questionText = (TextView) findViewById(R.id.text_question);
        resetCheated();
        updateQuestion();
//        questionText.setText(questions[currentIndex].getQuestionId());

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //next question
//                if(currentIndex < questions.length - 1){
//                    currentIndex ++;
//                }else {
//                    currentIndex = 0;
//                }
                currentIndex = (currentIndex + 1) % questions.length;
                resetCheated();
                updateQuestion();
//                questionText.setText(questions[currentIndex].getQuestionId());
            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show answer แบบ Toast ขึ้นมา
                //Toast.makeText(QuizActivity.this, String.valueOf(questions[currentIndex].isAnswer()) , Toast.LENGTH_SHORT).show();

//                //ต้องอ้างอิงตัวเอง, คลาสที่จะเรียก
//                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
//
//                //ยัดข้อมูลลงไปใน intent
//                intent.putExtra("NAME", questions[currentIndex].isAnswer());
//                //ต้องใช้ startActivity เวลาสร้าง intent ขึ้นมาใหม่
//                startActivity(intent);

                Intent intent = CheatActivity.createIntent(QuizActivity.this, getCurrentAnswer());
                startActivityForResult(intent, REQUEST_CHEATED);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentIndex == 0){
                    currentIndex = 3;
                }else{
                    currentIndex = (currentIndex-1) % questions.length;
                }

//                //or check like
//                currentIndex = (currentIndex-1) % questions.length;
//                if(currentIndex < 0){
//                    currentIndex = currentIndex + 4;
//                }r
                resetCheated();
                updateQuestion();
            }
        });

        Log.d(TAG,"On create");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CHEATED){
            if(dataIntent == null){
                return;
            }
            isCheated = CheatActivity.wasCheated(dataIntent);
        }
    }

    public boolean getCurrentAnswer(){
        return questions[currentIndex].getAnswer();
    }

    private void resetCheated(){
        isCheated = false;
    }
    private void updateQuestion(){
        questionText.setText(questions[currentIndex].getQuestionId());
    }


    private void checkAnswer(boolean answer){
        boolean correctAnswer = questions[currentIndex].isAnswer();

        int result;

        //------------------------------------------------------------------------------------------
        //เช็คแบบสั้น
        // int result = (answer == correctAnswer) ? R.string.correct_text : R.string.incorrect_text;
        //------------------------------------------------------------------------------------------

        if(isCheated){
            result = R.string.cheater_text;
        }else {
            if(answer == correctAnswer){
                result = R.string.correct_text;
//            Toast.makeText(QuizActivity.this, R.string.correct_text, Toast.LENGTH_SHORT).show();
            } else {
                result = R.string.wrong_text;
//            Toast.makeText(QuizActivity.this, R.string.incorrect_text, Toast.LENGTH_SHORT).show();
            }
        }

        Toast.makeText(QuizActivity.this, result, Toast.LENGTH_SHORT).show();
    }
}
