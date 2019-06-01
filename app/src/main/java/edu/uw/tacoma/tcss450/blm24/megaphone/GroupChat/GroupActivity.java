package edu.uw.tacoma.tcss450.blm24.megaphone.GroupChat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.json.JSONObject;

import edu.uw.tacoma.tcss450.blm24.megaphone.Depreciated.GroupListFragment;
import edu.uw.tacoma.tcss450.blm24.megaphone.R;


/**
 * GroupActivity Activity. This creates the Group landing page
 * where you can see the groups you've joined and create a
 * new group
 */
public class GroupActivity extends AppCompatActivity
        implements GroupAddFragment.OnGroupAddragmentInteractionListener,
        GroupListFragment.OnGroupListFragmentInteractionListener,
        GroupFireStoreListFragment.OnListFragmentInteractionListener{

    private JSONObject args;

    private String TAG = "GROUP_ACTIVITY";

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
        if (findViewById(R.id.fragment_group_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_group_container, new GroupFireStoreListFragment())
                    .commit();
        }

        fab.setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_group_container, new GroupAddFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public void onGroupListFragmentInteraction(Group group) {
        //TODO
    }

    /**
     * Forwards a JSON representation of the group to upload it.
     *
     * @param group the group to add to the database.
     */
    @Override
    public void onGroupAddFragmentInteraction(Group group) {
    }

    @Override
    public void onListFragmentInteraction(Group group) {
        Log.d(TAG, "onListFragmentInteraction: myGroupID is... " + group.getGroupID());
        Intent newIntent = new Intent(this, GroupChatActivity.class);
        newIntent.putExtra(GroupChatActivity.GROUPID, group.getGroupID());
        newIntent.putExtra(GroupChatActivity.GROUPNAME, group.getName());
        startActivity(newIntent);
    }

}
