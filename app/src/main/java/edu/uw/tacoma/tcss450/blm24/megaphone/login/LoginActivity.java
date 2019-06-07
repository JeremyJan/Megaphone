package edu.uw.tacoma.tcss450.blm24.megaphone.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.uw.tacoma.tcss450.blm24.megaphone.groupChat.GroupActivity;
import edu.uw.tacoma.tcss450.blm24.megaphone.R;
import edu.uw.tacoma.tcss450.blm24.megaphone.utils.LocationHelper;

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
        LocationHelper.setup(this);
        // Get shared preferences and check to see if the user is already logged in.
        sp = getSharedPreferences("user", MODE_PRIVATE); // If logged in, go to groups page.
        if (sp.getBoolean("loggedIn", false)) {
            Intent i = new Intent(this, GroupActivity.class);
            startActivity(i);
        }

        //Hide debug button
        View debug = findViewById(R.id.debug_button);
        debug.setVisibility(View.INVISIBLE);

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

                    new ValidateLoginInfoAsyncTask(LoginActivity.this).execute();

                    //finish();
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

    /**
     * Async task to handle querying the database that holds login information to check whether
     * or not user input relates and matches to an existing account.
     */
    private class ValidateLoginInfoAsyncTask extends AsyncTask<String, Void, String> {

        /** Reference to the parent class. */
        LoginActivity parent;

        public ValidateLoginInfoAsyncTask(LoginActivity parent) { this.parent = parent; }

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            HttpURLConnection urlConnection = null;
            EditText email = findViewById(R.id.emailInputField);
            EditText password = findViewById(R.id.passwordInputField);

            JSONObject credentialsReq = new JSONObject();

            // Attempt to reach the backend server.
            try {
                credentialsReq.put("email", email.getText().toString());
                Log.d("email", email.getText().toString());
                credentialsReq.put("password", password.getText().toString());
                Log.d("password", password.getText().toString());
                Log.d("JSON Contents:", credentialsReq.toString());

                URL urlObject = new URL(getString(R.string.check_credentials_url));
                urlConnection = (HttpURLConnection)urlObject.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());

                wr.write(credentialsReq.toString());
                wr.flush();
                wr.close();

                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;

                }
                Log.d("response: ", response);

                JSONObject responseJSON = new JSONObject(response);

                if (!responseJSON.getBoolean("success")) {

                    parent.runOnUiThread(() -> {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Invalid email or password.",
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                    });

                } else {

                    sp.edit().putBoolean("loggedIn", true).apply();
                    Intent intent = new Intent(LoginActivity.this, GroupActivity.class);
                    LoginActivity.this.startActivity(intent);
                }

            } catch (Exception e) {
                Log.e("TAG", e.getMessage());
            }


            return response;
        }
    }

}