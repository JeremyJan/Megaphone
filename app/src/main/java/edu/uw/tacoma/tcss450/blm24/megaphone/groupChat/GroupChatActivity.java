package edu.uw.tacoma.tcss450.blm24.megaphone.groupChat;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URI;

import edu.uw.tacoma.tcss450.blm24.megaphone.R;
import edu.uw.tacoma.tcss450.blm24.megaphone.utils.FirebaseUtil;

public class GroupChatActivity extends AppCompatActivity implements
        GroupMessageListFragment.OnListFragmentInteractionListener{


    public static final String TAG = "GROUPCHATACTIVITY";

    public static final String MESSAGES_CHILD = "messages";

    public static final String GROUPID = "GROUPID";

    public static final String GROUPNAME = "GROUPNAME";

    private String groupID = "";

    private String groupName = "";

    private Button mSendButton;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    private ImageView mAddMessageImageView;
    private Toolbar mToolbar;

    private SharedPreferences sp;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            groupID = extras.getString(GROUPID, "");
            groupName = extras.getString(GROUPNAME, "");
        }
        Log.d(TAG, "onCreate: id = " + groupID);

        mToolbar = findViewById(R.id.group_toolbar);
        mToolbar.getMenu();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(groupName);

        Bundle bundle = new Bundle();
        bundle.putString("groupID", groupID);

        GroupMessageListFragment fragInfo = new GroupMessageListFragment();

        fragInfo.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_chat_container, fragInfo)
                .commit();

        db = FirebaseFirestore.getInstance();

        mSendButton = findViewById(R.id.sendButton);
        mMessageEditText = findViewById(R.id.messageEditText);

        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theMessage = mMessageEditText.getText().toString();
                Log.d(TAG, "onClick: " + theMessage);
                sp = getSharedPreferences("user", MODE_PRIVATE);
                GroupMessage groupMessage = new GroupMessage(groupID,theMessage,
                        sp.getString("username", "HuskyHippo"), null);
                FirebaseUtil.createMessage(groupMessage);
            }
        });


    }

    @Override
    public void onListFragmentInteraction(GroupMessage item) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.share_menu_item:
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
                                String message = sp.getString("username", "friend")
                                        + R.string.share_content_text_message_content_1;
                                // Send the message.
                                manager.sendTextMessage(editText.getText().toString(), null,
                                        message, null, null);

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
                        });


        }

        return super.onOptionsItemSelected(item);
    }

}
