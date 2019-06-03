package edu.uw.tacoma.tcss450.blm24.megaphone.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.uw.tacoma.tcss450.blm24.megaphone.groupChat.Group;
import edu.uw.tacoma.tcss450.blm24.megaphone.groupChat.GroupMessage;

public class FirebaseUtil {

    private static final String TAG = "FirebaseUtil";

    private FirebaseFirestore db;

    private String groupId;

    public FirebaseUtil(FirebaseFirestore db) {
        this.db = db;
    }

    public void createGroup(Group group) {
        db.collection("Rooms").add(group).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Add success");
                    Log.d(TAG, "Id: " + task.getResult().getId());
                    group.setGroupID(task.getResult().getId());
                } else {
                    Log.w(TAG, "Add failed");
                }
            }
        });
    }

    public static void createMessage(GroupMessage groupMessage) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("messages").add(groupMessage)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: createMessage added");
                } else {
                    Log.w(TAG, "onComplete: createMessage failed");
                }
            }
        });
    }



    public String getGroupId() {
        return groupId;
    }
}
