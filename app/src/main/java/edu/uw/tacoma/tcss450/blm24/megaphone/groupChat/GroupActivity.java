package edu.uw.tacoma.tcss450.blm24.megaphone.groupChat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import edu.uw.tacoma.tcss450.blm24.megaphone.depreciated.GroupListFragment;
import edu.uw.tacoma.tcss450.blm24.megaphone.R;
import edu.uw.tacoma.tcss450.blm24.megaphone.utils.LocationHelper;


/**
 * GroupActivity Activity. This creates the Group landing page
 * where you can see the groups you've joined and create a
 * new group
 */
public class GroupActivity extends AppCompatActivity
        implements GroupAddFragment.OnGroupAddragmentInteractionListener,
        GroupListFragment.OnGroupListFragmentInteractionListener,
        GroupFireStoreListFragment.OnListFragmentInteractionListener{

    private String TAG = "GROUP_ACTIVITY";

    private SharedPreferences sp;

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
        setTitle("Local Groups");

        FloatingActionButton fab = findViewById(R.id.fab);

        //add list
        if (findViewById(R.id.fragment_group_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_group_container, new GroupFireStoreListFragment())
                    .commit();
        }

        fab.setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_group_container, new GroupAddFragment()).addToBackStack(null)
                    .commit();
        });

        LocationHelper.setup(this);
        checkDefaultUsername();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_toolbar, menu);
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
            case R.id.action_change_username:
                AlertDialog.Builder alertBuilder = changeUsernameAlertDialogAction();
                alertBuilder.setCancelable(false);
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
                break;

            case R.id.action_logout:
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                sp.edit().putBoolean("loggedIn", false).apply();
                finish();
                break;

            case R.id.share_menu_item:
                // Check and request permissions if necessary.
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.SEND_SMS},1);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final EditText editText = new EditText(this);
                builder.setTitle(R.string.share_content_alert_title)
                        .setMessage(R.string.share_content_alert_message)
                        .setCancelable(false)
                        .setView(editText)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Create sms manager to handle texting.
                                SmsManager manager = SmsManager.getDefault();
                                // Send the message.
                                manager.sendTextMessage(editText.getText().toString(), null,
                                        getString(R.string.share_content_text_message_content_1),
                                        null, null);

                                // Notify the user that the message was sent.
                                Toast.makeText(getApplicationContext(),"Message sent!", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;

        }


        return super.onOptionsItemSelected(item);
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

    /**
     * When the change username menu item is selected, this function creates and shows a dialog
     * box to the user, prompting them to select their new username.
     */
    public AlertDialog.Builder changeUsernameAlertDialogAction() {
        // Set up the dialog.
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.change_username_title);
        alertDialog.setMessage(R.string.change_username_message);
        final EditText editText = new EditText(this);
        alertDialog.setView(editText);

        // Set up the positive button option. Should take the entered text and set as new username.
        alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newUsername = editText.getText().toString();
                // TODO: Use stored username in firebase chat.
                sp.edit().putBoolean("defaultName", false).apply();
                sp.edit().putString("username", newUsername).apply();
                dialog.dismiss();

            }
        });

        // Set up the negative button option. Should keep old username and dismiss dialog.
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialog;
    }

    public void checkDefaultUsername() {
        sp = getSharedPreferences("user", MODE_PRIVATE);
        if (sp.getBoolean("defaultName", true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.default_username_alert_title)
                    .setMessage(R.string.default_username_alert_message)
                    .setCancelable(false)
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();

        }

    }

}
