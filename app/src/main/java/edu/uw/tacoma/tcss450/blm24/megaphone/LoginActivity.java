package edu.uw.tacoma.tcss450.blm24.megaphone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * LoginActivity activity which is the first activity that is presented to the
 * user. This activity enables the user to register an account or login
 */
public class LoginActivity extends AppCompatActivity {

    //via emailregex.com
    private static final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~" +
            "-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0" +
            "b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x0" +
            "9\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0" +
            "-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[" +
            "0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]" +
            "?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0" +
            "e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0" +
            "e-\\x7f])+)\\])";

    /**
     * onCreate method which intialized the buttons to do stuff
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        buttonInit();
    }

    /**
     * Attaches click listeners to various buttons used on the login page.
     * Should be called inside the onCreate() method.
     *
     * INPUT: Nothing.
     * OUTPUT: Buttons on login activity now have click functionality.
     */
    private void buttonInit() {
        Context context = getApplicationContext();
        // Register button set up:
        final Button registerButton = (Button) findViewById(R.id.RegisterUserButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            /**
             * This method starts the register page when clicked.
             * @param v - the view
             */
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        });

        // Login button set up:
        EditText email = (EditText)findViewById(R.id.emailInputField);
        EditText password = (EditText)findViewById(R.id.passwordInputField);




        //Janky authentication... need to be compared to the account database
        //In order to log in you will have to at least have an @ symbol on the name
        //and password length has to be >= 6
        final Button logButton = (Button) findViewById(R.id.SignIn);
        logButton.setOnClickListener(new View.OnClickListener() {

            /**
             * enables the log in button...
             * Requires that an email and the password to be valid according too...
             * email having an @ symbol and the password being greater than 6
             *
             * Future implemntation: This will compare when looking at the
             * login database.
             *
             * @param v - the view
             */
            @Override
            public void onClick(View v) {
                Log.i("TAG","Clicking signIn");
                String memberEmail = email.getText().toString();
                String memberPass = password.getText().toString();
                Log.i("TAG", memberEmail);
                boolean emailOk;
                boolean passOk;
                if ((emailOk = memberEmail.matches(emailRegex)) & (passOk = memberPass.length() >= 6)) {
                    Intent intent = new Intent(LoginActivity.this, GroupActivity.class);
                    LoginActivity.this.startActivity(intent);
                } else if (!emailOk) {
                    Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show();
                } else if (!passOk) {
                    Toast.makeText(context, "Password too short", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Cannot login"+ passOk, Toast.LENGTH_SHORT).show();
                }

            }
        });





    }

}
