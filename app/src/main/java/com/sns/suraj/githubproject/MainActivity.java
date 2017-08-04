package com.sns.suraj.githubproject;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    TextView name, age;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mcondRef = mRootRef.child("name");
    DatabaseReference mageRef = mRootRef.child("age");
    Button button_plus;
    String currrent_age_from_database;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (TextView) findViewById(R.id.name_textview);
        age = (TextView) findViewById(R.id.age_textview);
        button_plus = (Button) findViewById(R.id.btn_plus_age);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // Create channel to show notifications.
//            String channelId  = getString(R.string.default_notification_channel_id);
//            String channelName = getString(R.string.default_notification_channel_name);
//            NotificationManager notificationManager =
//                    getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
//                    channelName, NotificationManager.IMPORTANCE_LOW));}

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(MainActivity.class.getSimpleName(), "Key: " + key + " Value: " + value);
            }
        }
        Button subscribeButton = (Button) findViewById(R.id.subscribeButton);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("news");
                // [END subscribe_topics]

                // Log and toast
                String msg = "subscribed";
                Log.d(MainActivity.class.getSimpleName(), msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        Button logTokenButton = (Button) findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get token
                String token = FirebaseInstanceId.getInstance().getToken();

                // Log and toast
                String msg = getString(R.string.msg_token_fmt, token);
                Log.d(MainActivity.class.getSimpleName(), msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadFragmentClicked(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().findFragmentById(R.id.container_main_activity) == null) {
            fragmentTransaction.add(R.id.container_main_activity, new FragmentBox());
        } else fragmentTransaction.replace(R.id.container_main_activity, new FragmentBox());
        fragmentTransaction.commit();
    }

    public void saveNoteClicked(View view) {
        //hahahahaa
        Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
        FragmentTransaction fragmentTransaction;
//dafadfadfaafdaeer
        new Handler();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mcondRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                name.setText(text);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currrent_age_from_database = dataSnapshot.getValue(String.class);
                age.setText(currrent_age_from_database);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mageRef.setValue("" + (Integer.parseInt(currrent_age_from_database) + 1));
            }
        });

    }
    /*firebase is  noSQL database,it has a json like structure it can also be called json object*/

    public void minus_btnClicked(View view) {
        mageRef.setValue("" + (Integer.parseInt(currrent_age_from_database) - 1));
    }
}
