package fi.hymyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        backToMainActivity.putExtra("name", userName);
        backToMainActivity.putExtra("age", userAge);
        MainActivity.newUser = false;
        startActivity(backToMainActivity);
    }

    public void inputValidator(String name, String age) {
        //joku validaatio systeemi?
    }

}