package fi.hymyapp;

import static fi.hymyapp.MainActivity.EXTRA;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
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
    String dbpath;
    int pathNumber =1;
    //init values for data you want to access, make their value = database value later
    public int op1Value;
    public int op2Value;
    public int op3Value;
    public int totalValue;
    String statementText;

    ArrayList barArrayList;
    BarChart barChart;


    Score score;
    TextView scoreView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        dbpath = GameActivity.DATAPATH;
        op1Counter = database.getReference(dbpath+"/question1/zOp1Count");
        op2Counter = database.getReference(dbpath+"/question1/zOp2Count");
        op3Counter = database.getReference(dbpath+"/question1/zOp3Count");
        totalCounter = database.getReference(dbpath+"/question1/zTotalCount");
        statement =database.getReference(dbpath+"/question1/aStatement");

        readDataFromFirebase();

        barChart = findViewById(R.id.barchart);

        scoreView = (TextView) findViewById(R.id.scoreText);

        //get score from previous activity
        Bundle b = getIntent().getExtras();
        String scoreAmount =b.getString(EXTRA,"");
        // and set it to final screen
        score = new Score(Integer.parseInt(scoreAmount));
        scoreView.setText(score.checkResult());




    }



    public void setChart(View view){
        drawChart();
        updateFirebase();
        scoreView.setText(statementText);
    }
    private void drawChart(){
        //remove old chart
        barChart.removeAllViews();

        //create new bar chart with database values
        barArrayList = new ArrayList();
        barArrayList.add(new BarEntry(1f,op1Value));
        barArrayList.add(new BarEntry(2f,op2Value));
        barArrayList.add(new BarEntry(3f,op3Value));



        BarDataSet barDataSet = new BarDataSet(barArrayList,"Yht "+Integer.toString(totalValue));
        BarData barData = new BarData(barDataSet);

        //change option values into integer (they're float without this)
        ValueFormatter vf = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return ""+(int)value;
            }
        };
        barData.setValueFormatter(vf);
        barChart.setData(barData);



        //top of the chart, 3 label amount because 3 answer options
        XAxis valAxis = barChart.getXAxis();
        valAxis.setLabelCount(3);
        //set values for labels here
        valAxis.setValueFormatter(new MyAxisFormatter());
        valAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        //color bar data set
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //text color
        barData.setValueTextColor(Color.BLACK);
        //setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);



        barChart.invalidate(); // refresh








    }


    //set values for labels
    private class MyAxisFormatter extends ValueFormatter {


        @Override
        public String getFormattedValue(float value) {

            if(value==1){
                return "Samaa mieltä";
            }else if(value==2){
                return "Eri mieltä";
            }else{
                return "En osaa sanoa";
            }

        }

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