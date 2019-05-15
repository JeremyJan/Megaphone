package edu.uw.tacoma.tcss450.blm24.megaphone;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.datatype.Duration;

/**
 * RegisterPage activity which enables the user to
 * register for an account.
 */
public class RegisterPage extends AppCompatActivity {

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
    private Button createAccountButton;

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

                // Input validation.
                if (isValidInfo()) {
                    Log.d("TAG", "Successfully retrieved information.");
                    String url = "https://megaphone-backend.herokuapp.com/register";
                    new registerAccountAsyncTask().execute(url);

                } else {

                    Log.e("","Error: Invalid/Missing information in EditText fields.");
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

    private class registerAccountAsyncTask extends AsyncTask<String, Void, String> {

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

            // Attempt to send formed JSON Object to register page.
            Log.i("TAG", "Before the try block");
            try {
                Log.i("TAG","In the try block");
                URL urlObject = new URL(getString(R.string.register_account_url));
                Log.i("TAG","url: " + urlObject.toString());
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

            } catch (Exception e) {
                Log.e("URLException", "Error sending account creation request: " + e);
                Toast.makeText(getApplicationContext(), "Sorry, something has gone wrong." , Toast.LENGTH_LONG);
            }

            return response;

        }
    }
}
