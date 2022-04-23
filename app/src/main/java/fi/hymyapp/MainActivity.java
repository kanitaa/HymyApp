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


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA = "com.example.myfirstapp.MESSAGE";

    public static boolean newUser = true;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);

        ListView lv =findViewById(R.id.themesListView);
        lv.setAdapter(new ArrayAdapter<Themes>(this,
                android.R.layout.simple_list_item_1,
                Singleton.getInstance().getThemes()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mp.start();
                Intent nextActivity=new Intent(MainActivity.this,GameActivity.class);
                nextActivity.putExtra(EXTRA,i);
                startActivity(nextActivity);
            }
        });

        userPrefReader();
        updateUI();
    }

    public void updateUI() { //This function can be called whenever changes have been made to the Activity elements and they need to be updated

        Button continueButton = findViewById(R.id.newUserButton);

        if(newUser) {
            continueButton.setVisibility(View.VISIBLE);
        } else {
            continueButton.setVisibility((GONE));
            Intent fromUserInput = getIntent();
            userPrefEdit(fromUserInput.getStringExtra("name"), Integer.parseInt(fromUserInput.getStringExtra("age")));
        }

    }

    public void userPrefReader() {
        SharedPreferences userPrefGet = getSharedPreferences("UserPrefs", Activity.MODE_PRIVATE);
        user = new User(userPrefGet.getString("nameKey", ""), userPrefGet.getInt("ageKey", 0));
    }

    public void userPrefEdit(String name, int age) {
        SharedPreferences userPrefPut = getSharedPreferences("UserPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor userPrefEditor = userPrefPut.edit();
        userPrefEditor.putString("nameKey", name);
        userPrefEditor.putInt("ageKey", age);
        userPrefEditor.commit();
    }

    public void openNewUserActivity(View view) { // This function is to onClick of the continue button that leads to a new activity
        Intent userInputActivity = new Intent(MainActivity.this, NewUserActivity.class);
        startActivity(userInputActivity);
    }

  /*  public void OpenChart(View view){
        Intent nextActivity=new Intent(MainActivity.this,ChartActivity.class);

        startActivity(nextActivity);
    }*/
}