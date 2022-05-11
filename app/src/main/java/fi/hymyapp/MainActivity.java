package fi.hymyapp;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Person;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Daniel Heugenhauser
 * @author Taru Vikströn
 * @version 1
 * Main Activity class for the app.
 */

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA = "com.example.myfirstapp.MESSAGE";
    /**
     * Hide the support action bar
     * Add custon listView adapter to show themes from singleton class.
     * Add listener to listView.
     * @param savedInstanceState a reference to a Bundle object that is passed into the onCreate method of every Android Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ListView lv = findViewById(R.id.themesListView);
        lv.setVisibility(GONE);
        //Custom layout for list items.
        lv.setAdapter(new ArrayAdapter<Themes>(this,
                R.layout.adapter_view_layout,R.id.listItem,
                Theme.getInstance().getThemes()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Gets index of clicked item. Build an intent and include the index in in intent - it can be used in next activity.
             * Start button sound effect
             * <p>
             * This method is called when a item on the list is selected.
             * @param adapterView Listener for the List
             * @param view Building block for interface components
             * @param i Index of clicked item
             * @param l The row id of the item that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent nextActivity = new Intent(MainActivity.this,GameActivity.class);
                nextActivity.putExtra(EXTRA,i);
                startActivity(nextActivity);
            }
        });
    }

    public void start(View view) {
        updateUI();
    }

    /**
     * UpdateUI function is called in situations when activity view needs changes.
     * The function takes a button, a list view, a text view and shared preferences into variables.
     * If user has no saved preferences, the button is visible and list and text views are invisible.
     * However if the user is known and there are saved preferences, the button is invisible, list and text views are visible.
     * The text view is set to have a text that takes key values from preferences to greet the person by their name and age.
     */
    private void updateUI() { //This function can be called whenever changes have been made to the Activity elements and they need to be updated
        Button start = findViewById(R.id.startButton);
        TextView greetText = findViewById(R.id.greetText);
        ListView lv = findViewById(R.id.themesListView);
        ImageView logo = findViewById(R.id.logoImage);

        start.setVisibility(GONE);
        greetText.setVisibility(View.VISIBLE);
        lv.setVisibility(View.VISIBLE);
        logo.setVisibility(GONE);
    }

    /**
     * openChartActivity function is bound to an on-click of a button "vastaukset" and is called when it's clicked.
     * @param view a visual building block that responds to user inputs
     */
    public void openChartActivity(View view) { // This function is to onClick of the continue button that leads to a new activity
        Intent chartActivity = new Intent(MainActivity.this, ChartActivity.class);
        startActivity(chartActivity); // Starting ChartActivity
    }
}