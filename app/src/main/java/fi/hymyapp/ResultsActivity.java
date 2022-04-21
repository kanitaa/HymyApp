package fi.hymyapp;

import static fi.hymyapp.MainActivity.EXTRA;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    Score score;
    TextView scoreView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        scoreView = (TextView) findViewById(R.id.scoreText);

        //get score from previous activity
        Bundle b = getIntent().getExtras();
        String scoreAmount =b.getString(EXTRA,"");
        // and set it to final screen
        score = new Score(Integer.parseInt(scoreAmount));
        scoreView.setText(score.checkResult());


    }


}