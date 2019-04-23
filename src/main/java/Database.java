import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.internal.NonNull;

import javax.annotation.Nullable;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Database {

    public static Firestore database;

    static boolean hasBeenInitialized = false;

    public static void initializeDatabase() {
        try {
            InputStream serviceAccount = new FileInputStream("/home/sam/rpi-qrscanner/attendance_ascendance");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            database = FirestoreClient.getFirestore();
            hasBeenInitialized = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void newEntry(@NonNull String collection, @NonNull Map<String, Object> data) {
        if(!hasBeenInitialized) {
            System.err.println("Failed to initialize Database");
            return;
        }
        CollectionReference docRef = database.collection(collection);
        System.out.println("DocRef is: " + docRef.toString());
        ApiFuture<DocumentReference> result = docRef.add(data);
        try {
            System.out.println("Result: " + result.get().toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static List<QueryDocumentSnapshot> retrieveData(String collection) {

        ApiFuture<QuerySnapshot> query = database.collection(collection).get();

        try {

            QuerySnapshot querySnapshot = query.get();
            return querySnapshot.getDocuments();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
