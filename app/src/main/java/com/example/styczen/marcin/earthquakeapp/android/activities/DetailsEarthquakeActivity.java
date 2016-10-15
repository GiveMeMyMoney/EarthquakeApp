package com.example.styczen.marcin.earthquakeapp.android.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.android.fragments.DetailsEarthquakeFragment;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailsEarthquakeActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static String INTENT_EARTHQUAKE = DetailsEarthquakeActivity.class.getName() + ".earthquakeIntent";

    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap mMap;

    DetailsEarthquakeFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_earthquake);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Earthquake earthquake = getEarthquakeFromIntent(getIntent());

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

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return false;
            }
        });
        params.setBehavior(behavior);



        Toast.makeText(this, "DetailsEarthquakeActivity2", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap;
            mMap.setBuildingsEnabled(true);
            Marker kiel = mMap.addMarker(new MarkerOptions()
                    .position(KIEL)
                    .title("Kiel")
                    .snippet("Kiel is cool")
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_media_play)));

            // Move the camera instantly to hamburg with a zoom of 15.
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KIEL, 15));

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
