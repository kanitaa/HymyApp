package fi.hymyapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NewUserActivity extends AppCompatActivity {
    /**
     * @author Taru Vikström
     * @version 1
     * In the NewUserActivity class the user gets to input their name and age and the values are saved into preferences.
     * The class also contains a validation system for the user input text fields.
     * @param savedInstanceState a reference to a Bundle object that is passed into the onCreate method of every Android Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
    }

    /**
     * backToMainActivity function is called when a button is being pressed that has this function as it's on click parameter.
     * The function takes edit text fields for name and age as variables and passes the values into string variables.
     * userName and userAge string variables are then checked by sending them as parameters to their corresponding validator functions, which will send a boolean value.
     * If either of the validator checks returns false value, the function doesn't continue further.
     * If validator checks both return true, the function makes a new intent with current activity and the main activity as it's parameters,
     * calls for userPrefEdit function to save new values to the preferences and then changes the activity back to main activity.
     * @param view a visual building block that responds to user inputs.
     */
    public void backToMainActivity(View view) { // This function is on "OK" button under user input fields

        EditText editName = findViewById(R.id.editName); // Getting the input elements into variables
        EditText editAge = findViewById(R.id.editAge);

        String userName = editName.getText().toString(); // Taking the user input into variables for further use
        String userAge = editAge.getText().toString();

        if(!ageInputValidator(userAge) | !nameInputValidator(userName)) { // This check calls for validators for age and name inputs
            return; // if either of the functions return false, the backToMainActivity functions wont continue any further
        }

        // If checks go through we make a new intent and call for userPrefEdit function for saving the new user values
        Intent backToMainActivity = new Intent(NewUserActivity.this, MainActivity.class);
        userPrefEdit(userName, Integer.parseInt(userAge)); // Calling function and sending variables as parameters
        startActivity(backToMainActivity); // Swapping back to Main Activity
    }

    /**
     * userPrefEdit function has shared preferences and it's editor as variables to add new values, that have been sent by parameter to the function, to the preferences and commit the changes.
     * @param name is the String value that the user wrote to the input field (see backToMainActivity function)
     * @param age is the integer value that the user wrote to the input field (see backToMainActivity function)
     */
    private void userPrefEdit(String name, int age) { // This function saves the sent parameters as key values to preferences
        SharedPreferences userPrefPut = getSharedPreferences("UserPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor userPrefEditor = userPrefPut.edit();
        userPrefEditor.putString("nameKey", name); // Putting values for their corresponding keys
        userPrefEditor.putInt("ageKey", age);
        userPrefEditor.putBoolean("newUserKey", false);
        userPrefEditor.commit(); // Committing the changes to the preferences
    }

    /**
     * nameInputValidator validates the String that has been sent to it as a parameter and returns a boolean depending if the checks went through or not.
     * The function takes the corresponding edit text element as a variable in order to be able to set error messages that gives the user information, what was wrong with their input.
     * The value must be not empty, between 3-12 characters, contain only letters and special chars (.) ( ) and (-).
     * @param name is the String value that the user wrote to the input field (see backToMainActivity function)
     * @return tells if the checks went through (true) or if the string input was not valid (false)
     */
    private boolean nameInputValidator(String name) { // This validator function goes through various checks for name String that was sent as a parameter and returns a boolean

        EditText editName = findViewById(R.id.editName); // Taking the input field as a variable in order to be able to set error texts to it

        if(name.isEmpty()) { // Name field cannot be empty
            editName.setError("Kenttää ei voi jättää tyhjäksi!");
            return false;
        } else if (name.length() > 12 || name.length() < 3) { // Name must be between 3-12 characters long
            editName.setError("Nimen pitää olla 3-12 kirjainta.");
            return false;
        } else if (!name.matches("[a-zA-Z. -]*")) { // Name must have only these characters
            editName.setError("Käytä vain kirjaimia, välilyöntiä tai merkkejä . ja -");
            return false;
        } else {
            editName.setError(null); // Set the error as null if the check goes through
            return true;
        }
    }

    /**
     * ageInputValidator validates the integer that has been sent to it as a parameter and returns a boolean depending if the checks went through or not.
     * The function takes the corresponding edit text element as a variable in order to be able to set error messages that gives the user information, what was wrong with their input.
     * The value must not be empty, it has to be between 1-99 and must contain of only numbers from 0 to 9.
     * @param age is the integer value that the user wrote to the input field (see backToMainActivity function)
     * @return tells if the checks went through (true) or if the integer input was not valid (false)
     */
    private boolean ageInputValidator(String age) { // This validator function goes through various checks for age String that was sent as a parameter and returns a boolean

        EditText editAge = findViewById(R.id.editAge); // Taking the input field as a variable in order to be able to set error texts to it

        if(age.isEmpty()) { // Age field cannot be empty
            editAge.setError("Kenttää ei voi jättää tyhjäksi!");
            return false;
        } else if (!age.matches("[0-9]+")) { // Making sure only numbers are used
            editAge.setError("Iän täytyy olla numero.");
            return false;
        } else if ((Integer.parseInt(age)) < 1 || (Integer.parseInt(age)) > 99) { // Age must be between 1-99
            editAge.setError("Iän täytyy olla väliltä 1-99.");
            return false;
        } else {
            editAge.setError(null); // Set the error as null if the check goes through
            return true;
        }
    }
}