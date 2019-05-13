package edu.uw.tacoma.tcss450.blm24.megaphone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.datatype.Duration;

public class RegisterPage extends AppCompatActivity {

    private String mEmail;
    private String mPassword;
    private String mConfirmPassword;

    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        activityInit();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save info currently in EditText fields.
                getFieldInfo();
                Log.w("TAG", "Here before the check");


                // Input validation.
                if (isValidInfo()) {
                    // TODO: Send JSON request to register page.


                } else {
                    Log.w("TAG", "Here");
                    // Display to use they haven't filled a field.
                    Toast toast = Toast.makeText(RegisterPage.this,
                            getResources().getString(R.string.invalid_info_register_error_message),
                            Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });

    }

    /**
     * Initialize EditText and Button objects used by the activity.
     */
    private void activityInit() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        createAccountButton = findViewById(R.id.createAccountButton);

    }

    /**
     * Collects information currently input into the three EditText fields to be used for
     * account creation.
     *
     * INPUT: Nothing.
     * OUTPUT: mEmail, mPassword, mConfirmPassword should all be populated.
     */
    private void getFieldInfo() {
        // Set all fields to null before gathering new data.
        // Ensures that previously stored info can't act as a false positive for currently
        // input in the fields.
        mEmail = null;
        mPassword = null;
        mConfirmPassword = null;

        mEmail = emailEditText.getText().toString();
        mPassword = passwordEditText.getText().toString();
        mConfirmPassword = confirmPasswordEditText.getText().toString();

    }

    /**
     * Checks whether or not all the fields have been populated.
     * To be called before sending information to database to attempt account creation.
     *
     * @return True if all fields are populated, false if not.
     */
    private boolean isValidInfo() {
        if (mEmail.length() > 0 &&
                mPassword.length() > 0 &&
                mConfirmPassword.length() > 0) {
            return true;
        } else return false;

    }
}
