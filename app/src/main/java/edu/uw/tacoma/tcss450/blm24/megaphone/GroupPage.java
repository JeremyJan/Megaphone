package edu.uw.tacoma.tcss450.blm24.megaphone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GroupPage extends AppCompatActivity implements
        GroupListFragment.OnGroupListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.group_page_container, new GroupListFragment())
                .commit();
    }

    @Override
    public void onGroupListFragmentInteraction(GroupModel item) {

    }
}
