package fi.hymyapp;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Daniel Heugenhauser
 * @author Taru Vikströn
 */

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA = "com.example.myfirstapp.MESSAGE";
    User user = new User();


    /**
     *
     * Executes when MainActivity is created. Created a MediaPlayer variable for the sound effects in the buttons.
     * @param savedInstanceState a reference to a Bundle object that is passed into the onCreate method of every Android Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);

        ListView lv =findViewById(R.id.themesListView);
        lv.setAdapter(new ArrayAdapter<Themes>(this,
                android.R.layout.simple_list_item_1,
                Theme.getInstance().getThemes()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mp.start();
                Intent nextActivity=new Intent(MainActivity.this,GameActivity.class);
                nextActivity.putExtra(EXTRA,i);
                startActivity(nextActivity);
            }
        });

        userPrefReader(); // When starting the software the preferences need to be read to know if the user is new or known
        updateUI(); // UI needs updating
    }

    public void updateUI() { //This function can be called whenever changes have been made to the Activity elements and they need to be updated
        Button continueButton = findViewById(R.id.newUserButton);
        ListView lv =findViewById(R.id.themesListView);
        TextView greetUser = findViewById(R.id.greetUserText);
        SharedPreferences userPrefGet = getSharedPreferences("UserPrefs", Activity.MODE_PRIVATE);

        if(userPrefGet.getBoolean("newUserKey", true)) { // If the user is new, the quizzes are not visible and continue button is visible
            continueButton.setVisibility(View.VISIBLE);
            lv.setVisibility(GONE);
            greetUser.setVisibility(GONE);
        } else { // If the user is known, greeting text is visible, the quiz list is visible and the continue button is hidden
            continueButton.setVisibility((GONE));
            lv.setVisibility(View.VISIBLE);
            greetUser.setText("Moi " + userPrefGet.getString("nameKey", "") + " " + userPrefGet.getInt("ageKey", 0) + "-vuotta!");
            greetUser.setVisibility(View.VISIBLE);
        }
    }

    public void userPrefReader() { // This functions reads from the preferences
        SharedPreferences userPrefGet = getSharedPreferences("UserPrefs", Activity.MODE_PRIVATE);
        user = new User(userPrefGet.getString("nameKey", ""), userPrefGet.getInt("ageKey", 0), userPrefGet.getBoolean("newUserKey", true));
    }

    public void resetPrefs(View view) { // This function resets the user prefs, then new user input is needed before quizzes open again
        SharedPreferences userPrefPut = getSharedPreferences("UserPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor userPrefEditor = userPrefPut.edit();
        userPrefEditor.putString("nameKey", ""); // Putting "empty" values for their corresponding keys
        userPrefEditor.putInt("ageKey", 0);
        userPrefEditor.putBoolean("newUserKey", true);
        userPrefEditor.commit(); // Committing the changes to the preferences
        updateUI(); // UI needs updating
    }

    public void openNewUserActivity(View view) { // This function is to onClick of the continue button that leads to a new activity
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        mp.start();
        Intent userInputActivity = new Intent(MainActivity.this, NewUserActivity.class);
        startActivity(userInputActivity); // Starting userInputActivity
    }
}