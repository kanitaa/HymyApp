package fi.hymyapp;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class GetFirebase {

    //init database
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://hymyapp-default-rtdb.europe-west1.firebasedatabase.app/");

    private DatabaseReference op1Counter;
    private DatabaseReference op2Counter;
    private DatabaseReference op3Counter;
    private DatabaseReference totalCounter;
    private DatabaseReference statement;

    private String statementText;
    private int op1Value;
    private int op2Value;
    private int op3Value;
    private int totalValue;

    private String dbpath;


    public GetFirebase(){
        //init database values
        this.statementText="";
        this.op1Value=0;
        this.op2Value=0;
        this.op3Value=0;
        this.totalValue=0;

        //link database references to correct path in database
        op1Counter = database.getReference(GameActivity.dbpath+"/zOp1Count");
        op2Counter = database.getReference(GameActivity.dbpath+"/zOp2Count");
        op3Counter = database.getReference(GameActivity.dbpath+"/zOp3Count");
        totalCounter = database.getReference(GameActivity.dbpath+"/zTotalCount");
        statement = database.getReference(GameActivity.dbpath+"/aStatement");

    }
    //functions to get info from this database class
    public DatabaseReference getOp1Counter(){
        return op1Counter;
    }
    public int getOp1Value(){
        return op1Value;
    }
    public DatabaseReference getOp2Counter(){
        return op2Counter;
    }
    public int getOp2Value(){
        return op2Value;
    }
    public DatabaseReference getOp3Counter(){
        return op3Counter;
    }
    public int getOp3Value(){
        return op3Value;
    }
    public DatabaseReference getTotalCounter(){
        return totalCounter;
    }
    public int getTotalValue(){
        return totalValue;
    }
    public String getStatementText(){
        return statementText;
    }
    public void setDataPath(String dataPath){
        this.dbpath = dataPath;
    }
    public String getDataPath(){
        return dbpath;
    }

    //add listeners to all database references
    public void setCounters(TextView statementView) {
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
