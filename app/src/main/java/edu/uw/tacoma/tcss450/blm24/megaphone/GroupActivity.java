package edu.uw.tacoma.tcss450.blm24.megaphone;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;


/**
 * GroupActivity Activity. This creates the Group landing page
 * where you can see the groups you've joined and create a
 * new group
 */
public class GroupActivity extends AppCompatActivity
        implements GroupAddFragment.OnGroupAddragmentInteractionListener,
        GroupFragment.OnGroupListFragmentInteractionListener{

    /**
     * onCreate method. This sets up the list fragment and
     * the add fragment for groups
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FloatingActionButton fab = findViewById(R.id.fab);

        //add list
        if(findViewById(R.id.fragment_group_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_group_container, new GroupFragment())
                    .commit();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change this to replace once list fragment gets added
                fragmentTransaction.replace(R.id.fragment_group_container, new GroupAddFragment()) //TODO crashes
                        .addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onGroupAddFragmentInteraction(Uri uri) {

    }

    @Override
    public void onGroupListFragmentInteraction(Group item) {

    }
}
