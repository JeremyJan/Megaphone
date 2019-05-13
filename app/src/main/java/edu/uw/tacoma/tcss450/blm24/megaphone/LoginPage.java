package edu.uw.tacoma.tcss450.blm24.megaphone;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.net.URI;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);



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

        // Register button set up:
        final Button registerButton = (Button) findViewById(R.id.RegisterUserButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                LoginPage.this.startActivity(intent);

            }
        });

        // Login button set up:


    }

}