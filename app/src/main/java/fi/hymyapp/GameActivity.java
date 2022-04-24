package fi.hymyapp;

import static android.content.ContentValues.TAG;
import static fi.hymyapp.MainActivity.EXTRA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {

    GetFirebase base;

    //init values for path
    public static String dbpath;
    public static String dbTemp;
    private int pathNumber=1;
    public String correctA;

    //set statement text here for ui
    TextView statementView;

    private static final String TAG ="Firebase" ;

    //score counter for end results
    Score score = new Score();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle b = getIntent().getExtras();
        int i =b.getInt(EXTRA,0);


        statementView = (TextView) findViewById(R.id.statementText);

        dbpath =Singleton.getInstance().getThemes().get(i).getDatapath()+"/question"+pathNumber;
        //save og dbpath info to be able to access it later again
        dbTemp = Singleton.getInstance().getThemes().get(i).getDatapath()+"/question";
        base = new GetFirebase();
        base.setCounters(statementView);
        base.setDataPath(Singleton.getInstance().getThemes().get(i).getDatapath()+"/question");

    }

    //onclick functions for option buttons
    public void option1(View view){
        //Sound for button click
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        //when option is clicked, increase value by 1
        int newValue = base.getOp1Value() + 1;
        //and set database value to match this new value
        base.getOp1Counter().setValue(newValue);
        newValue = base.getTotalValue()+1;
        base.getTotalCounter().setValue(newValue);


        correctA=base.getCorrectAnswer();
        Log.d("asd",correctA);
        //Increase points by 2 if correct answer is right
        if(correctA.equals("op1")){
            //give score after right answer WIP
            score.increasePoints(2);
        }

        //after increasing database value, show new question
        changeQuestion();
    }
    public void option2(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        int newValue =base.getOp2Value()+1;
        base.getOp2Counter().setValue(newValue);
        newValue = base.getTotalValue()+1;
        base.getTotalCounter().setValue(newValue);

        //Get correct answer from getFirebase class.
        correctA=base.getCorrectAnswer();
        //Increase points by 2 if correct answer is right
        if(correctA.equals("op2")){
            score.increasePoints(2);
        }
        changeQuestion();
    }
    public void option3(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        int newValue = base.getOp3Value()+1;
        base.getOp3Counter().setValue(newValue);
        newValue = base.getTotalValue()+1;
        base.getTotalCounter().setValue(newValue);

        //Get correct answer from getFirebase class.
        correctA=base.getCorrectAnswer();
        //Increase points by 2 if correct answer is right
        if(correctA.equals("op3")){
            score.increasePoints(2);
        }
        changeQuestion();
    }
    private void changeQuestion(){
        if(pathNumber!=2)
        {
            //increase pathnumber by one, to access next question in database
            pathNumber+=1;
            //change db references to match correct question number
            dbpath = dbTemp+pathNumber;
            base = new GetFirebase();
            base.setCounters(statementView);
        }else{
            //last question answered, change activity
            Intent lastActivity = new Intent(GameActivity.this,ChartActivity.class);
            lastActivity.putExtra(EXTRA,score.toString());
            startActivity(lastActivity);
        }
    }
}