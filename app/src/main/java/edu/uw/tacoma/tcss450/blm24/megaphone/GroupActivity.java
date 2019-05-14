package edu.uw.tacoma.tcss450.blm24.megaphone;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * GroupActivity Activity. This creates the Group landing page
 * where you can see the groups you've joined and create a
 * new group
 */
public class GroupActivity extends AppCompatActivity
        implements GroupAddFragment.OnGroupAddragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.add(R.id.fragment_group_container, new GroupAddFragment()).commit();
            }
        });
    }

    @Override
    public void onGroupAddFragmentInteraction(Uri uri) {

    }
}
