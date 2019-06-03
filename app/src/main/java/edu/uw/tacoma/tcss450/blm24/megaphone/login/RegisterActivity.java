package edu.uw.tacoma.tcss450.blm24.megaphone.login;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.uw.tacoma.tcss450.blm24.megaphone.R;

/**
 * RegisterActivity activity which enables the user to
 * register for an account.
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * mEmail field which stores the email of the user
     */
    private String mEmail;
    /**
     * mPassword field which stores the password of the user
     */
    private String mPassword;
    /**
     * mConfirmPassword which again stores the password of the user
     * but mostly for verification that the two passwords are the
     * same.
     */
    private String mConfirmPassword;

    /**
     * emailEditText field which represents the EditText field of the
     * email edit text
     */
    private EditText emailEditText;
    /**
     * passwordEditText field which represents the EditText field of
     * the password edit text
     */
    private EditText passwordEditText;
    /**
     * confirmPasswordEditText field which represents the EditText field of
     * the confirm editText
     */
    private EditText confirmPasswordEditText;
    /**
     * account button
     */
    private Button createAccountButton;

    /**
     * Sets the create account buttons onClickListener and inits activityInit method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        activityInit();

        createAccountButton.setOnClickListener(v -> {
            // Save info currently in EditText fields.
            getFieldInfo();

            // Input validation.
            if (isValidInfo()) {
                new RegisterAccountAsyncTask(RegisterActivity.this).execute();

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
     * Validates the correct input of the EditText fields and shows Toasts depending on the
     * invalid text.
     *
     * @return True if all fields are populated, false if not.
     */
    private boolean isValidInfo() {
        // TODO: Fix validation cases.
        boolean result = false;
        Toast toast;

        String email, password, passwordConfirm;
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        passwordConfirm = confirmPasswordEditText.getText().toString();

        // Case 1: One or more fields are empty.
        if (email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            toast = Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0 ,0);
            toast.show();
        } else if (!Validation.validateEmail(mEmail)) { //Case 2: Bad Email
            toast = Toast.makeText(this, "Invalid email.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0 ,0);
            toast.show();
        } else if (!Validation.validatePassword(password)) { // Case 3: Password is less than six characters long.
            toast = Toast.makeText(this, "Passwords must be at least six characters.",
                                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0 , 0);
            toast.show();
        }  else if (!password.equals(passwordConfirm)) { // Case 4: Password and confirm password do not match.
            toast = Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();

        }

        // If not the others, then input is valid.
        else result = true;

        return result;
    }

    /**
     * Separate class to handle the async register task.
     */
    private class RegisterAccountAsyncTask extends AsyncTask<String, Void, String> {

        RegisterActivity parent;

        public RegisterAccountAsyncTask(RegisterActivity parent) {
            this.parent = parent;
        }

        /**
         * Override of the doInBackground task, used to create, populate and send a JSON
         * object to the register page hosted on our Heroku server.
         *
         * @param strings
         * @return The response from the server (success/fail).
         */
        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            HttpURLConnection urlConnection;

            // Create and populate a JSON Object with account information.
            JSONObject registerReq = new JSONObject();
            try {
                registerReq.put("email", mEmail);
                registerReq.put("password", mPassword);
            } catch (JSONException e) {
                Log.e("JSONException", "Error creating JSONObject: " + e);
            }

            // Attempt to send JSON Object to the register page.
            try {
                URL urlObject = new URL(getString(R.string.check_email_availability_url));
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write(registerReq.toString());
                wr.flush();
                wr.close();

                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
                urlConnection.disconnect();

                // Create a JSON object from the response message.
                // This message will tell us if the entered email is already used in our
                // database or not.
                JSONObject responseJSON = new JSONObject(response);

                // If it is email is not in use, continue on with registration.
                if (responseJSON.getBoolean("isAvailable") == true) {
                    urlObject = new URL(getString(R.string.register_account_url));
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);
                    wr = new OutputStreamWriter(urlConnection.getOutputStream());
                    wr.write(registerReq.toString());
                    wr.flush();
                    wr.close();

                    content = urlConnection.getInputStream();
                    buffer = new BufferedReader(new InputStreamReader(content));
                    s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                    parent.runOnUiThread(() -> {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Successfully created your account. Let's get started!",
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                        finish();
                    });

                } else {  // If email is in use, tell the user it is unavailable.
                    parent.runOnUiThread(() -> {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "That email is already in use.",
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                    });

                }


            } catch (Exception e) {

                Log.e("TAG", "Error creating account, error: " + e);
                parent.runOnUiThread(() -> {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Sorry something as gone wrong.",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                });
            }

            return response;

        }

    }
}
