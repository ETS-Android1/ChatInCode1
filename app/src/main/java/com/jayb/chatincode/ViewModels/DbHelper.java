package com.jayb.chatincode.ViewModels;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jayb.chatincode.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DbHelper {
    private static final String TAG = "DB_HELPER";

    public static boolean addCipherToDb (String saveName, String cipher, String encryptionType) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userId = user.getUid();
        Map<String, Object> userEntry = new HashMap<>();
        //First make sure the user exists in db
//        userEntry.put("saved", saveName);
//        if(!addToDb("users", userEntry, userId)) { return false;}
        
        //Now save the cipher
        Map<String, Object> cipherEntry = new HashMap<>();
        Date now = new Date();
        Timestamp timeNow = new Timestamp(now);
        cipherEntry.put("SaveName", saveName);
        cipherEntry.put("Cipher", cipher);
        cipherEntry.put("DateCreated", timeNow);
        cipherEntry.put("EncryptMethod", encryptionType);
        cipherEntry.put("User", user.getUid());
        return addToDbWithSub("users", userId, "saved", saveName, cipherEntry);
    }

    private static boolean addToDbWithSub(String firstLevelColl, String firstLevelDoc, String secondLevelColl, String secondLevelDoc, Map<String, Object> entry) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final boolean[] success = {false};
        //Attempt to add document to firestore
        db.collection(firstLevelColl)
                .document(firstLevelDoc)
                .collection(secondLevelColl)
                .document(secondLevelDoc)
                .set(entry)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        success[0] = true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        success[0] = false;
                    }
                });
        return success[0];
    }

    public static void logOutCurrUser() {
        FirebaseAuth.getInstance().signOut();
    }
}
