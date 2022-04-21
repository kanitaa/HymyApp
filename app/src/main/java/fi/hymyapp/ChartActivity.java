package fi.hymyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    private static final String TAG = "F";
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://hymyapp-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference op1Counter;
    DatabaseReference op2Counter;
    DatabaseReference op3Counter;
    DatabaseReference totalCounter;
    DatabaseReference statement;
    String dbpath = "involvementQuestions";
    int pathNumber =1;
    //init values for data you want to access, make their value = database value later
    public int op1Value;
    public int op2Value;
    public int op3Value;
    public int totalValue;
    String statementText;

    ArrayList barArrayList;
    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        op1Counter = database.getReference(dbpath+"/question1/zOp1Count");
        op2Counter = database.getReference(dbpath+"/question1/zOp2Count");
        op3Counter = database.getReference(dbpath+"/question1/zOp3Count");
        totalCounter = database.getReference(dbpath+"/question1/zTotalCount");
        statement =database.getReference(dbpath+"/question1/aStatement");

        readDataFromFirebase();

        barChart = findViewById(R.id.barchart);



    }



    public void setChart(View view){
        drawChart();
        updateFirebase();
    }
    private void drawChart(){
        barChart.removeAllViews();
        System.out.println(statementText);
        barArrayList = new ArrayList();
        barArrayList.add(new BarEntry(2f,op1Value));
        barArrayList.add(new BarEntry(3f,op2Value));
        barArrayList.add(new BarEntry(4f,op3Value));



        BarDataSet barDataSet = new BarDataSet(barArrayList,statementText);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        //color bar data set
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //text color
        barData.setValueTextColor(Color.BLACK);
        //setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);




    }
    private void updateFirebase(){

        if(pathNumber!=2){
            pathNumber+=1;

            //change db references to match correct question number
            dbpath = dbpath+"/question"+pathNumber;

            statement =database.getReference(dbpath+"/aStatement");

            op1Counter = database.getReference(dbpath+"/zOp1Count");
            op2Counter = database.getReference(dbpath+"/zOp2Count");
            op3Counter = database.getReference(dbpath+"/zOp3Count");
            totalCounter = database.getReference(dbpath+"/zTotalCount");
            readDataFromFirebase();
        }
        else{
            System.out.println("Vika kyssäri");
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