package edu.uw.tacoma.tcss450.blm24.megaphone.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uw.tacoma.tcss450.blm24.megaphone.GroupChat.GroupActivity;
import edu.uw.tacoma.tcss450.blm24.megaphone.R;

/**
 * LoginActivity activity which is the first activity that is presented to the
 * user. This activity enables the user to register an account or login
 */
public class LoginActivity extends AppCompatActivity {

    SharedPreferences sp;

    /**
     * onCreate method which intialized the buttons to do stuff
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get shared preferences and check to see if the user is already logged in.
        sp = getSharedPreferences("login", MODE_PRIVATE); // If logged in, go to groups page.
        if (sp.getBoolean("loggedIn", true)) {
            Intent i = new Intent(this, GroupActivity.class);
            startActivity(i);
        }

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
        final Button registerButton = findViewById(R.id.RegisterUserButton);
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
        EditText email = findViewById(R.id.emailInputField);
        EditText password = findViewById(R.id.passwordInputField);




        final Button logButton = findViewById(R.id.SignIn);
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
                if ((emailOk = Validation.validateEmail(memberEmail)) & (passOk = memberPass.length() >= 6)) {
                    sp.edit().putString("email", memberEmail).apply();
                    sp.edit().putBoolean("loggedIn", true).apply();
                    Intent intent = new Intent(LoginActivity.this, GroupActivity.class);
                    LoginActivity.this.startActivity(intent);

                    finish();
                } else if (!emailOk) {
                    Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show();
                } else if (!passOk) {
                    Toast.makeText(context, "Password too short", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Cannot login"+ passOk, Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button debugButton = findViewById(R.id.debug_button);

        debugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, GroupActivity.class);
                LoginActivity.this.startActivity(intent);
                finish();
            }
        });





    }

}
