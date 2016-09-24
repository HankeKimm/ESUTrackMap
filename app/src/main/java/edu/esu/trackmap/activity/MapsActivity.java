package edu.esu.trackmap.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import edu.esu.trackmap.R;
import edu.esu.trackmap.task.LocationDataLoadTask;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        new LocationDataLoadTask(mMap, getApplicationContext()).execute();
        // Add a marker in Sydney and move the camera
        //mMap.addPolyline((new PolylineOptions()).add(new LatLng(41.6563, -75.1095)).width(5).color(Color.BLUE).geodesic(true));
        // move camera to zoom on map
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.6563, -75.1095), 13));
    }
}
