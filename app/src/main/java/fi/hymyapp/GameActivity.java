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
import android.widget.Button;
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

    //set statement text here for ui
    TextView statementView;
    Button aText;
    Button op1button;
    Button op2button;
    Button op3button;

    private static final String TAG ="Firebase" ;

    //score counter for end results
    Score score = new Score();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle b = getIntent().getExtras();
        int i =b.getInt(EXTRA,0);

        aText=(Button) findViewById(R.id.answerButton);
        statementView = (TextView) findViewById(R.id.statementText);
        op1button=(Button) findViewById(R.id.option1Button);
        op2button=(Button)findViewById(R.id.option2Button);
        op3button=(Button)findViewById(R.id.option3Button);


        dbpath =Singleton.getInstance().getThemes().get(i).getDatapath()+"/question"+pathNumber;
        //save og dbpath info to be able to access it later again
        dbTemp = Singleton.getInstance().getThemes().get(i).getDatapath()+"/question";
        base = new GetFirebase();
        base.setButtons(op1button, op2button, op3button);
        base.setCounters(statementView);
        base.setDataPath(Singleton.getInstance().getThemes().get(i).getDatapath()+"/question");
        aText.setVisibility(View.INVISIBLE);



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



        //Increase points by 2 if correct answer is right
        if(base.getCorrectAnswer().equals("op1")){
            //give score after right answer WIP
            score.increasePoints(2);
        }

        //after increasing database value, show new question
        answerView();
    }
    public void option2(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        int newValue =base.getOp2Value()+1;
        base.getOp2Counter().setValue(newValue);
        newValue = base.getTotalValue()+1;
        base.getTotalCounter().setValue(newValue);



        //Get correct answer from getFirebase class.
        //Increase points by 2 if correct answer is right
        if(base.getCorrectAnswer().equals("op2")){
            score.increasePoints(2);
        }
        answerView();
    }
    public void option3(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        int newValue = base.getOp3Value()+1;
        base.getOp3Counter().setValue(newValue);
        newValue = base.getTotalValue()+1;
        base.getTotalCounter().setValue(newValue);



        //Get correct answer from getFirebase class.
        //Increase points by 2 if correct answer is right
        if(base.getCorrectAnswer().equals("op3")){
            score.increasePoints(2);
        }

        answerView();
    }
    private void changeQuestion(){
        if(pathNumber!=2)
        {

            //increase pathnumber by one, to access next question in database
            pathNumber+=1;
            //change db references to match correct question number
            dbpath = dbTemp+pathNumber;
            base = new GetFirebase();
            base.setButtons(op1button, op2button, op3button);
            base.setCounters(statementView);

        }else{
            //last question answered, change activity
            Intent lastActivity = new Intent(GameActivity.this,ChartActivity.class);
            lastActivity.putExtra(EXTRA,score.toString());
            startActivity(lastActivity);
        }
    }
    private void answerView(){
        statementView.setVisibility(View.INVISIBLE);
        op1button.setVisibility(View.INVISIBLE);
        op2button.setVisibility(View.INVISIBLE);
        op3button.setVisibility(View.INVISIBLE);
        aText.setVisibility(View.VISIBLE);
    }
    public void nextQuestion(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        changeQuestion();
        aText.setVisibility(View.INVISIBLE);
        statementView.setVisibility(View.VISIBLE);
        op1button.setVisibility(View.VISIBLE);
        op2button.setVisibility(View.VISIBLE);
        op3button.setVisibility(View.VISIBLE);

    }
}