package com.btxdev.gasolinerias;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDialog extends AppCompatDialog implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LatLng markerLocation;
    private LatLng extraMarkerLocation;
    private String action;
    private LatLng userLocation;
    private boolean mapReady;
    private MarkerLocationCallback markerLocationCallback;

    private static final String ACTION_SET_MARKER = "action_set_marker";

    private AppCompatActivity activity;

    public MapDialog(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) activity.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapReady = true;

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        if ( PermissionsUtil.hasLocationPermissions(getContext())) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    if(userLocation==null){
                        userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        if(extraMarkerLocation==null){
                           centerUserLocation();
                        }
                    }else{
                        userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                }
            });
        } else {
            Toast.makeText(getContext(), R.string.err_no_location_permissions, Toast.LENGTH_SHORT).show();
        }

        if(extraMarkerLocation!=null){
            setMarker(extraMarkerLocation);
            centerMarkerLocation();
        }

        if(action.equals(ACTION_SET_MARKER)){
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    markerLocation = latLng;
                    setMarker(markerLocation);
                }
            });
        }

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mMap.clear();
                if(markerLocationCallback!=null){
                    markerLocationCallback.onDismiss(markerLocation);
                }
            }
        });
    }

    private void centerUserLocation(){
        if(userLocation!=null){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            mMap.setOnMyLocationChangeListener(null);
        }
    }

    private void centerMarkerLocation(){
        if(extraMarkerLocation!=null){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(extraMarkerLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    public void getMarkerLocation(LatLng currentMarkerLocation, MarkerLocationCallback markerLocationCallback){
        this.markerLocationCallback = markerLocationCallback;
        this.extraMarkerLocation = currentMarkerLocation;
        this.action = ACTION_SET_MARKER;
        super.show();
        if(mapReady){
            if(extraMarkerLocation==null){
                centerUserLocation();
            }else{
                setMarker(extraMarkerLocation);
                centerMarkerLocation();
            }
        }
    }

    public void show(){
        this.markerLocationCallback = null;
        this.extraMarkerLocation = null;
        super.show();
        if(mapReady){
            centerUserLocation();
        }
    }

    interface MarkerLocationCallback{
        void onDismiss(LatLng latLng);
    }

    private void setMarker(LatLng latLng){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(activity.getString(R.string.ubicacion));
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.addMarker(markerOptions);
    }
}
