package com.augmentis.ayp.aypquiz;

/**
 * Created by Chayanit on 7/14/2016.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = "CheatActivity";
    private static final String INDEX = "INDEX";
    private boolean answer;
    private Button confirmButton;
    private TextView answerText;
    private static final String ANSWER_EXTRA_KEY = "ANSWER";
    private static final String CHEATED_EXTRA_KEY = "CHEATED";
    private boolean isCheated;
    private int currentAnswer;
    private int ans = 5;


    public static Intent createIntent(Context context, boolean answer) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(ANSWER_EXTRA_KEY, answer);
        return intent;
    }

    public static boolean wasCheated(Intent intent) {
        return intent.getExtras().getBoolean(CHEATED_EXTRA_KEY);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "State is saving");
        outState.putInt(INDEX, currentAnswer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        answer = getIntent().getExtras().getBoolean(ANSWER_EXTRA_KEY);
        isCheated = false;

//        Intent intent = getIntent();
//        Bundle extra = intent.getExtras();
//        answer = extra.getBoolean("NAME");

        confirmButton = (Button) findViewById(R.id.confirm_button);
        answerText = (TextView) findViewById(R.id.text_answer);

        if (answer) {
            ans = 1;
        } else {
            ans = 0;
        }

        if (savedInstanceState != null) {
            currentAnswer = ans;
        } else {
            currentAnswer = 5;
        }
        if (currentAnswer == 5) {
            answerText.setText("");
        } else if (currentAnswer == 1) {
            answerText.setText(R.string.answer_is_true);
        } else {
            answerText.setText(R.string.answer_is_false);
        }


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                //
                if (answer) {
                    answerText.setText(R.string.answer_is_true);
                } else {
                    answerText.setText(R.string.answer_is_false);
                }

                isCheated = true;

                returnResult();

                int cx = answerText.getWidth() / 2;
                int cy = answerText.getHeight() / 2;

                float radius = answerText.getWidth();
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(answerText, cx, cy, radius, 0);

                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        answerText.setVisibility(View.VISIBLE);
                        confirmButton.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();
            }

        });
    }

    private void returnResult() {
        Intent newIntent = new Intent();
        newIntent.putExtra(CHEATED_EXTRA_KEY, isCheated);
        setResult(RESULT_OK, newIntent);
    }
}
