package fi.hymyapp;

import static android.content.ContentValues.TAG;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;
/**
 * @author Janita Korhonen
 * @version 1
 * A class for getting data from Firebase
 */
public class GetFirebase {

    //database
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://hymyapp-default-rtdb.europe-west1.firebasedatabase.app/");

    //database references
    private DatabaseReference op1Counter;
    private DatabaseReference op2Counter;
    private DatabaseReference op3Counter;
    private DatabaseReference totalCounter;
    private DatabaseReference statement;
    private DatabaseReference correctAnswer;
    private DatabaseReference op1;
    private DatabaseReference op2;
    private DatabaseReference op3;

    //values
    private String op1Text;
    private String op2Text;
    private String op3Text;
    private String correctAnswerText;
    private String statementText;
    private int op1Value;
    private int op2Value;
    private int op3Value;
    private int totalValue;


    private Button op1button;
    private Button op2button;
    private Button op3button;

    /**
     * Constructor for GetFirebase class.
     * Used when it is required to get data from Firebase
     */
    public GetFirebase(){
        //init database values
        this.statementText="";
        this.op1Value=0;
        this.op2Value=0;
        this.op3Value=0;
        this.totalValue=0;
        this.correctAnswerText="";
        this.op1Text="";
        this.op2Text="";
        this.op3Text="";

        //link database references to correct path in database
        op1Counter = database.getReference(GameActivity.dbpath+"/zOp1Count");
        op2Counter = database.getReference(GameActivity.dbpath+"/zOp2Count");
        op3Counter = database.getReference(GameActivity.dbpath+"/zOp3Count");
        totalCounter = database.getReference(GameActivity.dbpath+"/zTotalCount");
        statement = database.getReference(GameActivity.dbpath+"/aStatement");
        correctAnswer=database.getReference(GameActivity.dbpath+"/correctAnswer");
        op1=database.getReference(GameActivity.dbpath+"/op1");
        op2=database.getReference(GameActivity.dbpath+"/op2");
        op3=database.getReference(GameActivity.dbpath+"/op3");

    }


    /**
     * Returns op1Counter database reference.
     * This same applies for op2Counter, op3Counter and totalCounter.
     * <p>
     * This method is called when zOp1Count firebase reference is needed
     * @return  op1Counter as database reference
     */
    public DatabaseReference getOp1Counter(){
        return op1Counter;
    }
    public DatabaseReference getOp2Counter(){
        return op2Counter;
    }
    public DatabaseReference getOp3Counter(){
        return op3Counter;
    }
    public DatabaseReference getTotalCounter(){
        return totalCounter;
    }

    /**
     * Returns op1Value.
     * This same applies for op2Value, op3Value and totalValue.
     * <p>
     * This method is called when op1Value is needed
     * @return  op1Value as integer
     */
    public int getOp1Value(){
        return op1Value;
    }
    public int getOp2Value(){
        return op2Value;
    }
    public int getOp3Value(){
        return op3Value;
    }
    public int getTotalValue(){
        return totalValue;
    }

    /**
     * Returns statementText.
     * This same applies for getCorrectAnswer(), getOp1(), getOp2(), getOp3().
     * <p>
     * This method is called when statementText String is needed
     * @return  statementText String
     */
    public String getStatementText(){
        return statementText;
    }
    public String getCorrectAnswer(){return correctAnswerText; }
    public String getOp1(){return op1Text;}
    public String getOp2(){return op2Text;}
    public String getOp3(){return op3Text;}


    /**
     * Sets buttons for GameActivity.
     * Allows option texts to change when new question is retrieved from Firebase.
     * <p>
     * This method is called after new GetFirebase class has been created in GameActivity.java
     */
    public void setButtons(Button op1, Button op2, Button op3){
        this.op1button=op1;
        this.op2button=op2;
        this.op3button=op3;
    }


    /**
     * Adds listeners to all database references
     * <p>
     * This method is called after new GetFirebase class has been created
     */
    //add listeners to all database references
    public void setCounters(TextView statementView) {
        //QUESTION TEXT, add listener which triggers when data is changed
        statement.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //after first time when data is changed, variable gets new value from database
                //this applies to all listeners in setCounters method.
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
        //Read correct answer from the database
        correctAnswer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                correctAnswerText=datasnapshot.getValue(String.class);
                Log.d(TAG,"Value is : "+correctAnswerText);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG,"Failed to read value.",error.toException());
            }
        });

        //Read option1 from the database
        op1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                op1Text=datasnapshot.getValue(String.class);
                Log.d(TAG,"Op1 text value is : "+op1Text);
                //if setButtons has been called so button isn't null
                if(op1button!=null)
                op1button.setText(op1Text);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG,"Failed to read value.",error.toException());
            }
        });
        // Read option 2 from the database
        op2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                op2Text=datasnapshot.getValue(String.class);
                Log.d(TAG,"Op2 text value is : "+op2Text);
                if(op2button!=null)
                op2button.setText(op2Text);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG,"Failed to read value.",error.toException());
            }
        });
        // Read option 3 from the database
        op3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                op3Text=datasnapshot.getValue(String.class);
                Log.d(TAG,"Op3 text value is : "+op3Text);
                if(op3button!=null)
                op3button.setText(op3Text);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG,"Failed to read value.",error.toException());
            }
        });
    }
}
