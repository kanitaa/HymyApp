package fi.hymyapp;

import static fi.hymyapp.MainActivity.EXTRA;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;


public class ChartActivity extends AppCompatActivity {

    //variables needed for database
    GetFirebase base;
    int pathNumber =1;
    TextView statementView;

    //variables for chart
    ArrayList barArrayList;
    BarChart barChart;

    //variables for score class
    Score score;
    TextView scoreView;
    String op1="a";
    String op2="b";
    String op3="c";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        scoreView = (TextView) findViewById(R.id.scoreText);
        statementView = (TextView) findViewById(R.id.statementView);
        barChart = findViewById(R.id.barchart);

        //update database datapath to show first question
        GameActivity.dbpath = GameActivity.dbTemp+pathNumber;


        //get score from previous activity
        Bundle b = getIntent().getExtras();
        String scoreAmount =b.getString(EXTRA,"");
        // and set it to final screen
        score = new Score(Integer.parseInt(scoreAmount));
        scoreView.setText(score.checkResult());

        //get database values for charts
        base = new GetFirebase();

        base.setCounters(statementView);
        //hide statementView, its needed for parameter but not used in this activity
        statementView.setVisibility(View.GONE);


    }

    //onclick event for datachart button
    public void setChart(View view){
            op1 = base.getOp1();
            op2 = base.getOp2();
            op3 = base.getOp3();
            drawChart();
            updateFirebase();


    }
    private void drawChart(){
        //remove old chart
        barChart.removeAllViews();

        //create new bar chart with database values
        barArrayList = new ArrayList();
        barArrayList.add(new BarEntry(1f,base.getOp1Value()));
        barArrayList.add(new BarEntry(2f,base.getOp2Value()));
        barArrayList.add(new BarEntry(3f,base.getOp3Value()));
        BarDataSet barDataSet = new BarDataSet(barArrayList,base.getStatementText());
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
                return op1;
            }else if(value==2){
                return op2;
            }else{
                return op3;
            }
        }
    }
    private void updateFirebase(){
            //if path isnt already max (max question amount)
            if(pathNumber!=2) {
                pathNumber += 1;
                //update path with new value to get another question info from database
                GameActivity.dbpath = GameActivity.dbTemp+pathNumber;
                base = new GetFirebase();
                base.setCounters(statementView);

            } else{
                //if path is maxed out, start showing questions all over again from start
                pathNumber = 1;
                GameActivity.dbpath = GameActivity.dbTemp+pathNumber;
                base = new GetFirebase();
                base.setCounters(statementView);
            }
    }
}