package fi.hymyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    String questionText;
    TextView questionView;
    int pathNumber=1;
    private static final String TAG ="asd" ;
    public int trueValue;
    public int falseValue;
    public int totalValue;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://sovellus-4af70-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference trueCounter = database.getReference("questions/question1/zOp1Count");
    DatabaseReference falseCounter = database.getReference("questions/question1/zOp2Count");
    DatabaseReference totalCounter = database.getReference("questions/question1/zTotalCount");

    DatabaseReference qRef = database.getReference("questions/question1/aStatement");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionView = (TextView) findViewById(R.id.questionText);
        ReadDataFromFirebase();



    }

    public void TrueButton(View view){
        int newValue = trueValue+1;
        trueCounter.setValue(newValue);
        newValue = totalValue+1;
        totalCounter.setValue(newValue);

        ChangeQuestion();
    }
    public void FalseButton(View view){
        int newValue =falseValue+1;
        falseCounter.setValue(newValue);
        newValue = totalValue+1;
        totalCounter.setValue(newValue);

        ChangeQuestion();
    }
    public void ChangeQuestion(){
        if(pathNumber!=10)
        {
            pathNumber+=1;
        }else{
            Log.d(TAG, "Se oli vika kysymys");
            return;
        }



        String path = "questions/question"+pathNumber;
        qRef =database.getReference(path+"/aStatement");
        trueCounter = database.getReference(path+"/zOp1Count");
        falseCounter = database.getReference(path+"/zOp2Count");
        totalCounter = database.getReference(path+"/zTotalCount");


        ReadDataFromFirebase();
    }
    public void ReadDataFromFirebase(){


        qRef.addValueEventListener(new ValueEventListener() {
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


        // Read from the database
        trueCounter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                trueValue = dataSnapshot.getValue(Integer.class);
                Log.d(TAG, "True Value is: " + trueValue);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Read from the database
        falseCounter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                falseValue = dataSnapshot.getValue(Integer.class);
                Log.d(TAG, "False Value is: " + falseValue);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Read from the database
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