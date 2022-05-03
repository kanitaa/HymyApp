package fi.hymyapp;

import static fi.hymyapp.MainActivity.EXTRA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ThanksForAnsweringActivity extends AppCompatActivity {

    //variables for score class
    Score score;
    TextView scoreText;
    TextView thankYouForAnsweringText;
    boolean scoreChecked;

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
        String scoreAmount =b.getString(EXTRA,"");
        // and set it to final screen
        score = new Score(Integer.parseInt(scoreAmount));
        scoreText.setText(score.checkResult());
    }

    public void backToMainActivity(View view) {

        if(scoreChecked == false) {
            scoreText.setVisibility(View.GONE);
            thankYouForAnsweringText.setVisibility(View.VISIBLE);
            scoreChecked = true;
        } else {
            Intent backToMainActivity = new Intent(ThanksForAnsweringActivity.this, MainActivity.class);
            startActivity(backToMainActivity);
        }
    }

    public void onBackPressed(){
        Intent nextActivity = new Intent(ThanksForAnsweringActivity.this, MainActivity.class);
        startActivity(nextActivity);
    }
}