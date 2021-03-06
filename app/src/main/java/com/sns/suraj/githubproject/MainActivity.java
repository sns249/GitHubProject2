package com.sns.suraj.githubproject;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadFragmentClicked(View view) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().findFragmentById(R.id.container_main_activity)==null){
            fragmentTransaction.add(R.id.container_main_activity,new FragmentBox());
        }else fragmentTransaction.replace(R.id.container_main_activity,new FragmentBox());
        fragmentTransaction.commit();
    }
}
