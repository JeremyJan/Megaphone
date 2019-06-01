package edu.uw.tacoma.tcss450.blm24.megaphone.GroupChat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import edu.uw.tacoma.tcss450.blm24.megaphone.R;
import edu.uw.tacoma.tcss450.blm24.megaphone.Utils.FirebaseUtil;

public class GroupChatActivity extends AppCompatActivity {

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        ImageView messageImageView;
        TextView messengerTextView;
        ImageView messengerImageView;
        View view;

        public MessageViewHolder(View v) {
            super(v);
            view = v;
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messageImageView = (ImageView) itemView.findViewById(R.id.messageImageView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (ImageView) itemView.findViewById(R.id.messengerImageView);
        }

        void setMessageTextView(String theMessage) {
            messageTextView.setText(theMessage);
        }

        void setMessengerTextView(String theMessenger) {
            messageTextView.setText(theMessenger);
        }
    }

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
        getSupportActionBar().setTitle(groupName);

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
                GroupMessage groupMessage = new GroupMessage(groupID,theMessage, "HuskyHippo");
                FirebaseUtil.createMessage(groupMessage);
            }
        });
        


    }

}
