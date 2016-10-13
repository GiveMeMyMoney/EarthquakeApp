package com.example.styczen.marcin.earthquakeapp.android.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.android.fragments.PlusOneFragment;

public class DetailsEarthquakeActivity extends AppCompatActivity {

    PlusOneFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO act_single_frag?
        setContentView(R.layout.activity_details_earthquake);

        FragmentManager fm = getSupportFragmentManager();
        fragment = (PlusOneFragment) fm.findFragmentById(R.id.container);
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            fragment = PlusOneFragment.newInstance("bla1", "bla2");
            ft.add(R.id.container, fragment);
            ft.commit();
        }

    }




}
