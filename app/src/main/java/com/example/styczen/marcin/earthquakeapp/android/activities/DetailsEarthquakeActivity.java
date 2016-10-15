package com.example.styczen.marcin.earthquakeapp.android.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.android.fragments.PlusOneFragment;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class DetailsEarthquakeActivity extends AppCompatActivity {
    public static String INTENT_EARTHQUAKE = DetailsEarthquakeActivity.class.getName() + ".earthquakeIntent";

    PlusOneFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_earthquake);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Earthquake earthquake = getEarthquakeFromIntent(getIntent());

        FragmentManager fm = getSupportFragmentManager();
        fragment = (PlusOneFragment) fm.findFragmentById(R.id.contentContainer);
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            fragment = PlusOneFragment.newInstance("bla1", "bla2");
            ft.add(R.id.contentContainer, fragment);
            ft.commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_favorites);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Toast.makeText(this, "DetailsEarthquakeActivity2", Toast.LENGTH_SHORT).show();
    }

    public static Intent getStartActivityIntent(Context ctx, Earthquake earthquake) {
        Intent i = new Intent(ctx, DetailsEarthquakeActivity.class);
        i.putExtra(INTENT_EARTHQUAKE, earthquake);
        return i;
    }

    private static Earthquake getEarthquakeFromIntent(Intent intent) {
        return intent.getParcelableExtra(INTENT_EARTHQUAKE);
    }
}
