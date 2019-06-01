package edu.uw.tacoma.tcss450.blm24.megaphone.GroupChat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import edu.uw.tacoma.tcss450.blm24.megaphone.R;

public class GroupChatActivity extends AppCompatActivity {

    public static final String TAG = "GROUPCHATACTIVITY";

    public static final String GROUPID = "GROUPID";

    public static final String GROUPNAME = "GROUPNAME";

    private String groupID = "";

    private String groupName = "";

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
        getSupportActionBar().setTitle(groupName);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

    }
}
