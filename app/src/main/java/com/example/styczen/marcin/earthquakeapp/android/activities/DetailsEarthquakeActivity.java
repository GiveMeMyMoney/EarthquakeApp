package com.example.styczen.marcin.earthquakeapp.android.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.android.fragments.DetailsEarthquakeFragment;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailsEarthquakeActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static String INTENT_EARTHQUAKE = DetailsEarthquakeActivity.class.getName() + ".earthquakeIntent";

    private static final LatLng KIEL = new LatLng(63.102, -151.6458); //(os Y, os X)
    private GoogleMap mMap;

    private DetailsEarthquakeFragment fragment;

    Earthquake earthquake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_earthquake);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        earthquake = getEarthquakeFromIntent(getIntent());

        FragmentManager fm = getSupportFragmentManager();
        fragment = (DetailsEarthquakeFragment) fm.findFragmentById(R.id.contentContainer);
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            fragment = DetailsEarthquakeFragment.newInstance(earthquake);
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_google);
        mapFragment.getMapAsync(this);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitleEnabled(false);
        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        //TODO refactor
        //Drag na mapce - by przesuwac mapke
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return false;
            }
        });
        params.setBehavior(behavior);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //chowanie tytulu gdy expanded
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    getSupportActionBar().setTitle(earthquake.getTitle());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    isShow = true;
                } else if (isShow) {
                    getSupportActionBar().setTitle(" ");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap; //earthquake.getLocalisation()
            Marker earthquakePlace = mMap.addMarker(new MarkerOptions()
                    .position(KIEL)
                    .title("Kiel"));

            // Move the camera instantly to hamburg with a zoom of 15.
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KIEL, 50));

            // Zoom in, animating the camera.
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
        }
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
