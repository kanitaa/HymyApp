package fi.hymyapp;

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

    public static String DATAPATH ="" ;
    //init database and references to parts you want to access
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://hymyapp-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference op1Counter;
    DatabaseReference op2Counter;
    DatabaseReference op3Counter;
    DatabaseReference totalCounter;
    DatabaseReference statement;




    //init values for path
    String dbpath;

    int pathNumber=1;

    //init values for data you want to access, make their value = database value later
    public int op1Value;
    public int op2Value;
    public int op3Value;
    public int totalValue;
    String statementText;

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

        //give db references path to database
        dbpath =Singleton.getInstance().getThemes().get(i).getDatapath();
        DATAPATH = dbpath;
        op1Counter = database.getReference(dbpath+"/question1/zOp1Count");
        op2Counter = database.getReference(dbpath+"/question1/zOp2Count");
        op3Counter = database.getReference(dbpath+"/question1/zOp3Count");
        totalCounter = database.getReference(dbpath+"/question1/zTotalCount");
        statement = database.getReference(dbpath+"/question1/aStatement");

        statementView = (TextView) findViewById(R.id.statementText);
        readDataFromFirebase();





    }

    public void option1(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        //when option is clicked, increase value by 1
        int newValue = op1Value+1;
        //and set database value to match this new value
        op1Counter.setValue(newValue);
        newValue = totalValue+1;
        totalCounter.setValue(newValue);

        score.increasePoints(2);

        //after increasing database value, show new question
        changeQuestion();
    }
    public void option2(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        int newValue =op2Value+1;
        op2Counter.setValue(newValue);
        newValue = totalValue+1;
        totalCounter.setValue(newValue);

        changeQuestion();
    }
    public void option3(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        int newValue = op3Value+1;
        op3Counter.setValue(newValue);
        newValue = totalValue+1;
        totalCounter.setValue(newValue);

        changeQuestion();
    }
    public void changeQuestion(){
        if(pathNumber!=2)
        {
            //increase pathnumber by one, to access next question in database
            pathNumber+=1;
            //change db references to match correct question number
            dbpath = dbpath+"/question"+pathNumber;
            statement =database.getReference(dbpath+"/aStatement");
            op1Counter = database.getReference(dbpath+"/zOp1Count");
            op2Counter = database.getReference(dbpath+"/zOp2Count");
            op3Counter = database.getReference(dbpath+"/zOp3Count");
            totalCounter = database.getReference(dbpath+"/zTotalCount");
            readDataFromFirebase();

        }else{
            //last question answered, change activity
            Intent lastActivity = new Intent(GameActivity.this,ChartActivity.class);
            lastActivity.putExtra(EXTRA,score.toString());

            startActivity(lastActivity);

        }






    }
    public void readDataFromFirebase(){

        //QUESTION TEXT, add listener which triggers when data is changed
        statement.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //after first time when data is changed, variable gets new value from database
                statementText = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Question is: " + statementText);
                //set new text to UI
                statementView.setText(statementText);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        // Read from the database OPTION1
        op1Counter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                op1Value = dataSnapshot.getValue(Integer.class);
                Log.d(TAG, "Option1 Value is: " + op1Value);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Read from the database OPTION2
        op2Counter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                op2Value = dataSnapshot.getValue(Integer.class);
                Log.d(TAG, "Option2 Value is: " + op2Value);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // Read from the database OPTION3
        op3Counter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                op3Value = dataSnapshot.getValue(Integer.class);
                Log.d(TAG, "Option3 Value is: " + op3Value);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Read from the database TOTAL COUNTER
        totalCounter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                totalValue = dataSnapshot.getValue(Integer.class);
                Log.d(TAG, "Value is: " + totalValue);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    }
}