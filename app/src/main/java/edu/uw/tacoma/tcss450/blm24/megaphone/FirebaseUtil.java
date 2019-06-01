package edu.uw.tacoma.tcss450.blm24.megaphone;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

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
                    groupId = task.getResult().getId();
                } else {
                    Log.w(TAG, "Add failed");
                }
            }
        });
    }



    public String getGroupId() {
        return groupId;
    }
}
