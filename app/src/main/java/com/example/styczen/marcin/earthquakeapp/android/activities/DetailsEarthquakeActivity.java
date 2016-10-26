package com.example.styczen.marcin.earthquakeapp.android.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.android.fragments.DetailsEarthquakeFragment;
import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.dbManager.DbEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.dbManager.interfaces.IDbEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.core.Earthquake;
import com.example.styczen.marcin.earthquakeapp.exceptions.DataBaseException;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailsEarthquakeActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String LOG_TAG = DetailsEarthquakeActivity.class.getSimpleName();
    public static String INTENT_EARTHQUAKE = DetailsEarthquakeActivity.class.getName() + ".earthquakeIntent";
    private static int MAPS_ZOOM_SIZE = 3; // 1 = all map.

    //region VIEW
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private FloatingActionButton fab;
    //endregion VIEW

    private GoogleMap mMap;
    private DetailsEarthquakeFragment fragment;
    private Earthquake earthquake;
    private IDbEarthquakeManager earthquakeManager;

    public DetailsEarthquakeActivity() {
        try {
            earthquakeManager = DbEarthquakeManager.getEarthquakeManager(this);
        } catch (DataBaseException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Error: " + e.getMessage());
        }
    }

    //TODO refactor
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_google);
        mapFragment.getMapAsync(this);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        fab = (FloatingActionButton) findViewById(R.id.fab_favorites);
        setViewBehavior();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setViewBehavior() {
        mCollapsingToolbarLayout.setTitleEnabled(false);
        //Enable Drag on map
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return false;
            }
        });
        params.setBehavior(behavior);

        //Hide title and backArrow when expanded
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

        //FAB
        addRemoveFavoriteEarthquake();
    }

    private void addRemoveFavoriteEarthquake() {
        try {
            boolean isFavorite = earthquakeManager.selectById((int) earthquake.getId()) != null;
            int imageFavoriteId = isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border;
            fab.setImageResource(imageFavoriteId);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        boolean isFavorite = earthquakeManager.selectById((int) earthquake.getId()) != null;
                        if (isFavorite) {
                            earthquakeManager.deleteById((int) earthquake.getId());
                            fab.setImageResource(R.drawable.ic_favorite_border);
                            Toast.makeText(DetailsEarthquakeActivity.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                        } else {
                            earthquakeManager.insert(earthquake);
                            fab.setImageResource(R.drawable.ic_favorite);
                            Toast.makeText(DetailsEarthquakeActivity.this, "Now, it's your favorite!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (DataBaseException e) {
                        e.printStackTrace();
                    }

                }

            });
        } catch (DataBaseException e) {
            Toast.makeText(DetailsEarthquakeActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Error: " + e.getMessage());
        }
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
        LatLng mapsLocalisation = new LatLng(earthquake.getGeometry().getCoordinatesMapY(), earthquake.getGeometry().getCoordinatesMapX());
        if (googleMap != null && mapsLocalisation != null) {
            mMap = googleMap;
            Marker earthquakePlace = mMap.addMarker(new MarkerOptions()
                    .position(mapsLocalisation)
                    .title("Kiel"));

            // Move the camera instantly to hamburg with a zoom of MAPS_ZOOM_SIZE.
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapsLocalisation, MAPS_ZOOM_SIZE));
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
