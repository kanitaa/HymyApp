package fi.hymyapp;

import static fi.hymyapp.MainActivity.EXTRA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author Taru Vikström
 * @version 1
 * ThanksForAnsweringActivity shows users the score from previous quiz, gives a feedback and thanks for participating.
 */

public class ThanksForAnsweringActivity extends AppCompatActivity {

    Score score;
    TextView scoreText;
    TextView thankYouForAnsweringText;
    boolean scoreChecked; // This is used to activate and inactivate different text elements and finally, take the user back to main activity

    /**
     * When created, all the variables corresponding UI elements are passed as values.
     * scoreChecked is given "false" value by default so that each time a quiz is finished, the user sees first the score.
     * Extras, sent from previous activity are given as parameter, when calling the function from score class to get the score feedback.
     * @param savedInstanceState a reference to a Bundle object that is passed into the onCreate method of every Android Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks_for_answering);

        scoreChecked = false;
        scoreText = findViewById(R.id.scoreInformationText);
        thankYouForAnsweringText = findViewById(R.id.thanksForAnsweringText);
        thankYouForAnsweringText.setVisibility(View.GONE);

        //get score from previous activity
        Bundle b = getIntent().getExtras();
        String scoreAmount = b.getString(EXTRA,"");
        // and set the result text from score class it to a text element
        score = new Score(Integer.parseInt(scoreAmount));
        scoreText.setText(score.checkResult());
    }

    /**
     * backToMainActivity function is bound to an on-click of a button "takaisin" and is called when it's clicked.
     * the function checks if score has been viewed, if false, the button swaps the score feedback into thank you for answering text and sets scoreChecked to true.
     * Then if button is pressed again, the user is taken back to MainActivity.
     * @param view a visual building block that responds to user inputs
     */
    public void backToMainActivity(View view) { // This function checks if score has been checked and swaps text elements accordingly.
        // After checking score and getting a thank you message, the user is taken back to Main Activity with the next button press.

        if(scoreChecked == false) {
            scoreText.setVisibility(View.GONE);
            thankYouForAnsweringText.setVisibility(View.VISIBLE);
            scoreChecked = true;
        } else {
            Intent backToMainActivity = new Intent(ThanksForAnsweringActivity.this, MainActivity.class);
            startActivity(backToMainActivity);
        }
    }

    /**
     * If user presses phones "back" button on this activity, it takes them back to Main Activity, instead of previous activity.
     */
    public void onBackPressed() { // If user presses phones "back" button on this activity, it takes them back to Main Activity, instead of previous activity.
        Intent nextActivity = new Intent(ThanksForAnsweringActivity.this, MainActivity.class);
        startActivity(nextActivity);
    }
}