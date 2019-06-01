package edu.uw.tacoma.tcss450.blm24.megaphone;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


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
        toolbar.getMenu();
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FloatingActionButton fab = findViewById(R.id.fab);

        // Add options to the toolbar.


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

    }

    /**
     * Inflate the menu for the toolbar.
     * @param menu The menu to inflate.
     * @return True if the menu was successfully inflated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /**
     * Defines the actions that correspond to different MenuItems on the Toolbar.
     * @param item Item selected from the Toolbar.
     * @return True if action occured as expected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Switch cases to get correct action for each menu item.
        switch(item.getItemId())
        {
            case R.id.action_logout:
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                sp.edit().putBoolean("loggedIn", false).apply();
                finish();

        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * An asynchronous task that adds the group to the database.
     */
    private class AddGroupAsyncTask extends AsyncTask<String, Void, String> {
        /**
         * Submits the group args to the url(s)
         *
         * @param urls URLS to submit to
         * @return the response of this operation.
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                    wr.write(args.toString());
                    wr.flush();
                    wr.close();
                    InputStream content = urlConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new
                            InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to add the new group, Reason: " + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * Reports the result of the operation and moves in BackStack.
         *
         * @param s the result to report
         */
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject res = new JSONObject(s);
                if(res.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "Group added", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().popBackStackImmediate();
                } else {
                    Toast.makeText(getApplicationContext(), "Couldn't add group: "+ res.getString("error"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
