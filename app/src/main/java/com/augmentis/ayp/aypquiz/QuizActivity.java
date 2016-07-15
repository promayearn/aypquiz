package com.augmentis.ayp.aypquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    Button trueButton;
    Button falseButton;
    Button nextButton;
    Button previousButton;
    TextView questionText;

    Question[] questions = new Question[]{
            new Question(R.string.question_1_nile, true)
            ,new Question(R.string.question_2_Pro, true)
            ,new Question(R.string.question_3_math, false)
            ,new Question(R.string.question_4_mars, false)
    };

    int currentIndex;

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
        Log.d(TAG, "On Destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "On Pause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "On Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "On Resume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d(TAG, "State is saving ");
        outState.putInt(INDEX, currentIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        trueButton = (Button) findViewById(R.id.true_button);
        falseButton = (Button) findViewById(R.id.false_button);
        nextButton = (Button) findViewById(R.id.next_button);
        questionText = (TextView) findViewById(R.id.text_question);
        previousButton = (Button) findViewById(R.id.previous_button);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(INDEX);
        } else {
            currentIndex = 0;
        }
        questionText.setText(questions[currentIndex].getQuestionId());

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Click True
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Click False
                checkAnswer(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Click Next
                if(currentIndex == 0) {
                    currentIndex = 3;
                } else {
                    currentIndex = currentIndex - 1;
                }

                questionText.setText(questions[currentIndex].getQuestionId());
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Click Previous
                if(currentIndex < questions.length - 1) {
                    currentIndex++;
                } else {
                    currentIndex = 0;
                }

                questionText.setText(questions[currentIndex].getQuestionId());
            }
        });
    }

    public void checkAnswer(boolean answer) {

        boolean currentAnswer = questions[currentIndex].isAnswer();
        if (answer == currentAnswer) {
            Toast.makeText(QuizActivity.this, R.string.correct_text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(QuizActivity.this, R.string.wrong_text, Toast.LENGTH_SHORT).show();
        }
    }

}