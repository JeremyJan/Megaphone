package edu.uw.tacoma.tcss450.blm24.megaphone;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        GroupFragment.OnGroupListFragmentInteractionListener{

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
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FloatingActionButton fab = findViewById(R.id.fab);

        //add list
        if (findViewById(R.id.fragment_group_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_group_container, new GroupFragment())
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
        try {
            args = group.toJSON();
            new AddGroupAsyncTask().execute(getString(R.string.add_group));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Cannot send "+ group, Toast.LENGTH_LONG).show();
        }
    }

    private class AddGroupAsyncTask extends AsyncTask<String, Void, String> {
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
