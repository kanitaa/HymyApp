package fi.hymyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        String userName = editName.getText().toString();
        EditText editAge = findViewById(R.id.editAge);
        String userAge = editAge.getText().toString();

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

    public void inputValidator(String name, String age) {
        //joku validaatio systeemi?
    }

}