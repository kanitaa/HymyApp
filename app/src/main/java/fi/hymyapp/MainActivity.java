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
    User user = new User();

    /**
     * Creates a MediaPlayer variable for the sound effects in the buttons.
     * Sets ListView element for the main activity layout.
     * Sets a new ArrayAdapter witch uses list item layout. And get sets all the themes from the singleton class into the ListView.
     * Sets a Listener for the list items.
     * <p>
     * This method is called when MainActivity is created
     * @param savedInstanceState a reference to a Bundle object that is passed into the onCreate method of every Android Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample3);
        ListView lv =findViewById(R.id.themesListView);
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
                Intent nextActivity=new Intent(MainActivity.this,GameActivity.class);
                nextActivity.putExtra(EXTRA,i);
                startActivity(nextActivity);
            }
        });

        userPrefReader(); // When starting the software the preferences need to be read to know if the user is new or known
        updateUI(); // UI needs updating
    }

    /**
     * UpdateUI function is called in situations when activity view needs changes.
     * The function takes a button, a list view, a text view and shared preferences into variables.
     * If user has no saved preferences, the button is visible and list and text views are invisible.
     * However if the user is known and there are saved preferences, the button is invisible, list and text views are visible.
     * The text view is set to have a text that takes key values from preferences to greet the person by their name and age.
     */
    private void updateUI() { //This function can be called whenever changes have been made to the Activity elements and they need to be updated
        Button continueButton = findViewById(R.id.newUserButton);
        ListView lv = findViewById(R.id.themesListView);
        TextView greetUser = findViewById(R.id.greetUserText);
        ImageView logoImage = findViewById(R.id.logoImage);
        SharedPreferences userPrefGet = getSharedPreferences("UserPrefs", Activity.MODE_PRIVATE);

        if(userPrefGet.getBoolean("newUserKey", true)) { // If the user is new, the quizzes are not visible and continue button is visible
            logoImage.setVisibility(View.VISIBLE);
            continueButton.setVisibility(View.VISIBLE);
            lv.setVisibility(GONE);
            greetUser.setVisibility(GONE);
        } else { // If the user is known, greeting text is visible, the quiz list is visible and the continue button is hidden
            logoImage.setVisibility(GONE);
            continueButton.setVisibility((GONE));
            lv.setVisibility(View.VISIBLE);
            greetUser.setText("Moi " + userPrefGet.getString("nameKey", "") + " " + userPrefGet.getInt("ageKey", 0) + "-vuotta ja tervetuloa kertomaan mielipiteesi lasten asioista!");
            greetUser.setVisibility(View.VISIBLE);
        }
    }

    /**
     *  userPrefReader functions takes Shared preferences as a variable and reads the saved key values.
     *  new user is created with the values gotten from the shared preferences. If there were no saved preferences, starter key values are given.
     */
    private void userPrefReader() { // This functions reads from the preferences
        SharedPreferences userPrefGet = getSharedPreferences("UserPrefs", Activity.MODE_PRIVATE);
        user = new User(userPrefGet.getString("nameKey", ""), userPrefGet.getInt("ageKey", 0), userPrefGet.getBoolean("newUserKey", true));
    }

    /**
     * resetPrefs function resets the existing preferences back to the starter values and is bound to a button "uusi käyttäjä".
     * Shared preferences and it's editor is taken as variables to change the values and commit them.
     * updateUI function is called at the end to do the possible visual changes to the activity
     * @param view a visual building block that responds to user inputs
     */
    public void resetPrefs(View view) { // This function resets the user prefs, then new user input is needed before quizzes open again
        SharedPreferences userPrefPut = getSharedPreferences("UserPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor userPrefEditor = userPrefPut.edit();
        userPrefEditor.putString("nameKey", ""); // Putting "empty" values for their corresponding keys
        userPrefEditor.putInt("ageKey", 0);
        userPrefEditor.putBoolean("newUserKey", true);
        userPrefEditor.commit(); // Committing the changes to the preferences
        updateUI(); // UI needs updating
    }

    /**
     * openNewUserActivity function is bound to an on-click of a button "aloita" and is called when it's clicked.
     * Intent is taken into a variable and it has the information of the starting activity and the activity that it starts, which is userInputActivity
     * @param view a visual building block that responds to user inputs
     */
    public void openNewUserActivity(View view) { // This function is to onClick of the continue button that leads to a new activity
        Intent userInputActivity = new Intent(MainActivity.this, NewUserActivity.class);
        startActivity(userInputActivity); // Starting userInputActivity
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