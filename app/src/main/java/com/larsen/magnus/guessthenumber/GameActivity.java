package com.larsen.magnus.guessthenumber;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    ImageView imgLevel;
    TextView txtAttempts, txtTime, txtIntervall, txtFeedback;
    Button btnGuess;
    EditText txtUserInput;

    //Variables that controls the final scoring
    long timeScoring = 100000;
    long finalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Intent musicIntent = new Intent(GameActivity.this, MusicService.class);
        startService(musicIntent);

        imgLevel = (ImageView) findViewById(R.id.imgLevel);
        txtAttempts = (TextView) findViewById(R.id.txtAttempts);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtIntervall = (TextView) findViewById(R.id.txtIntervall);
        btnGuess = (Button) findViewById(R.id.btnConfirm);
        txtUserInput = (EditText) findViewById(R.id.txtUserInput);
        txtFeedback = (TextView) findViewById(R.id.txtFeedback);

        Intent intent = getIntent();
        final Level selectedLevel = (Level) intent.getSerializableExtra("theLevelSelected");

        final String levelName = selectedLevel.getLevelName();

        //Setting the correct picture as header
        if(levelName.equals("Easy")) {
            imgLevel.setImageResource(R.drawable.easy);
        } else if(levelName.equals("Medium")) {
            imgLevel.setImageResource(R.drawable.medium);
        } else if(levelName.equals("Hard")) {
            imgLevel.setImageResource(R.drawable.hard);
        }

        final int attemptsLeft = selectedLevel.getNumberOfGuesses();
        txtAttempts.setText("You have\n" + attemptsLeft + "\nattempts left.");

        //Generating new number here so that players cannot cheat
        final int highestNumber = selectedLevel.getHighestNum();
        Random random = new Random();
        final int theSecretNumber = random.nextInt(highestNumber - 1 + 1) + 1;

        txtIntervall.setText("Find the secret number between\n 1 and " + highestNumber);

        //CountDownTimer for stress level
        final CountDownTimer tmrTimer = new CountDownTimer(selectedLevel.getTimePressure(), 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //Calculating time so that it displays correctly in the app
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long correctSeconds = seconds % 60;
                String correctString = "" + correctSeconds;

                if(correctString.length() == 1) {
                    correctString = "0" + correctString;
                }

                txtTime.setText("Time: " + minutes + ":" + correctString);

                //Subtracts score for each second passing
               if(levelName.equals("Easy")) {
                   timeScoring -= 1000;
               } else if(levelName.equals("Medium")) {
                   timeScoring -= 100;
               } else if(levelName.equals("Hard")) {
                   timeScoring -= 10;
               }
            }

            @Override
            public void onFinish() {
                txtTime.setText("Time's up! Better luck next time.\nThe secret number was " + theSecretNumber);
                txtTime.setTextColor(Color.RED);
                txtTime.setTextSize(20);
                btnGuess.setEnabled(false);
                stopService(musicIntent);
            }
        }.start();

        btnGuess.setOnClickListener(new View.OnClickListener() {
            int attemptsLeft = selectedLevel.getNumberOfGuesses();

            @Override
            public void onClick(View v) {

                //Try-Catch to prevent crash when user forget to enter valid number
                try {
                    int userInput = Integer.parseInt(txtUserInput.getText().toString());

                    //Checks userInput and compares to secret number
                    if(userInput == theSecretNumber) {
                        tmrTimer.cancel();

                        //Calculating final score
                        finalScore = timeScoring * attemptsLeft;

                        stopService(musicIntent);
                        Intent regActivity = new Intent(GameActivity.this, RegActivity.class);
                        regActivity.putExtra("theScore", finalScore);
                        regActivity.putExtra("theNumber", theSecretNumber);
                        regActivity.putExtra("theLevelName", selectedLevel.getLevelName());
                        startActivity(regActivity);
                    } else if (theSecretNumber > userInput) {
                        txtFeedback.setText("The number is larger than " + userInput);
                        attemptsLeft--;
                        txtAttempts.setText("You have\n" + attemptsLeft + "\nattempts left.");
                    } else if (theSecretNumber < userInput) {
                        txtFeedback.setText("The number is lower than " + userInput);
                        attemptsLeft--;
                        txtAttempts.setText("You have\n" + attemptsLeft + "\nattempts left.");
                    }

                    //Gives feedback on no attempts left
                    if(attemptsLeft == 0) {
                        txtAttempts.setTextColor(Color.RED);
                        txtAttempts.setText("Sorry. Better luck next time.\nThe secret number was " + theSecretNumber);
                        tmrTimer.cancel();
                        btnGuess.setEnabled(false);
                        stopService(musicIntent);
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid number.", Toast.LENGTH_LONG).show();
                }

                txtUserInput.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(getApplicationContext(), MusicService.class));
    }
}
