package fi.hymyapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
    }

    public void backToMainActivity(View view) {

        EditText editName = findViewById(R.id.editName);
        EditText editAge = findViewById(R.id.editAge);
        String userName = editName.getText().toString();
        String userAge = editAge.getText().toString();

        if(!ageInputValidator(userAge) | !nameInputValidator(userName)) {
            return;
        }
        Intent backToMainActivity = new Intent(NewUserActivity.this, MainActivity.class);
        userPrefEdit(userName, Integer.parseInt(userAge));
        startActivity(backToMainActivity);

    }

    public void userPrefEdit(String name, int age) {
        SharedPreferences userPrefPut = getSharedPreferences("UserPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor userPrefEditor = userPrefPut.edit();
        userPrefEditor.putString("nameKey", name);
        userPrefEditor.putInt("ageKey", age);
        userPrefEditor.putBoolean("newUserKey", false);
        userPrefEditor.commit();
    }

    public boolean nameInputValidator(String name) {

        EditText editName = findViewById(R.id.editName);
        Log.d(TAG, "nameInputValidator: tsekataan");

        if(name.isEmpty()) {
            editName.setError("Kenttää ei voi jättää tyhjäksi!");
            return false;
        } else if (name.length() > 12 || name.length() < 3) {
            editName.setError("Nimen pitää olla 3-12 kirjainta.");
            return false;
        } else if (!name.matches("[a-zA-Z. -]*")) {
            editName.setError("Käytä vain kirjaimia, välilyöntiä tai merkkejä . ja -");
            return false;
        } else {
            editName.setError(null);
            return true;
        }
    }

    public boolean ageInputValidator(String age) {

        EditText editAge = findViewById(R.id.editAge);
        Log.d(TAG, "ageInputValidator: tsekataan");

        if(age.isEmpty()) {
            editAge.setError("Kenttää ei voi jättää tyhjäksi!");
            return false;
        } else if (!age.matches("[0-9]+")) {
            editAge.setError("Iän täytyy olla numero.");
            return false;
        } else if ((Integer.parseInt(age)) < 1 || (Integer.parseInt(age)) > 99) {
            editAge.setError("Iän täytyy olla väliltä 1-99.");
            return false;
        } else {
            editAge.setError(null);
            return true;
        }
    }

}