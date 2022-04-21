package fi.hymyapp;

import androidx.appcompat.app.AppCompatActivity;

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



    String questionText;
    TextView questionView;

    String dbpath;
    int pathNumber=1;

    private static final String TAG ="asd" ;
    public int op1Value;
    public int op2Value;
    public int op3Value;
    public int totalValue;



    FirebaseDatabase database = FirebaseDatabase.getInstance("https://hymyapp-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference op1Counter;
    DatabaseReference op2Counter;
    DatabaseReference op3Counter;
    DatabaseReference totalCounter;

    DatabaseReference statement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle b = getIntent().getExtras();
        int i =b.getInt(MainActivity.EXTRA,0);

        dbpath ="se teksti mikä tuodaan intentillä";
        op1Counter = database.getReference(dbpath+"/question1/zOp1Count");
        op2Counter = database.getReference(dbpath+"/question1/zOp2Count");
        op3Counter = database.getReference(dbpath+"/question1/zOp3Count");
        totalCounter = database.getReference(dbpath+"/question1/zTotalCount");
        statement = database.getReference(dbpath+"question1/aStatement");

        questionView = (TextView) findViewById(R.id.questionText);
        ReadDataFromFirebase();









    }

    public void Option1(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        int newValue = op1Value+1;
        op1Counter.setValue(newValue);
        newValue = totalValue+1;
        totalCounter.setValue(newValue);


        ChangeQuestion();
    }
    public void Option2(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        int newValue =op2Value+1;
        op2Counter.setValue(newValue);
        newValue = totalValue+1;
        totalCounter.setValue(newValue);

        ChangeQuestion();
    }
    public void Option3(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        int newValue = op3Value+1;
        op3Counter.setValue(newValue);
        newValue = totalValue+1;
        totalCounter.setValue(newValue);

        ChangeQuestion();
    }
    public void ChangeQuestion(){
        if(pathNumber!=2)
        {
            pathNumber+=1;
        }else{
            Log.d(TAG, "Se oli vika kysymys paskaa");
            return;
        }



        dbpath = dbpath+"/question"+pathNumber;
        statement =database.getReference(dbpath+"/aStatement");
        op1Counter = database.getReference(dbpath+"/zOp1Count");
        op2Counter = database.getReference(dbpath+"/zOp2Count");
        op3Counter = database.getReference(dbpath+"/zOp3Count");
        totalCounter = database.getReference(dbpath+"/zTotalCount");


        ReadDataFromFirebase();
    }
    public void ReadDataFromFirebase(){

        //QUESTION TEXT
        statement.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                questionText = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Question is: " + questionText);
                questionView.setText(questionText);

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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
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