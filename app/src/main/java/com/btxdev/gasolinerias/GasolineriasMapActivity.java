package com.btxdev.gasolinerias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class GasolineriasMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String EXTRA_GASOLINERIAS_LIST = "extra_gasolinerias_list";

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.explorar_mapa);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        if ( PermissionsUtil.hasLocationPermissions(this)) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    mMap.setOnMyLocationChangeListener(null);
                }
            });
        } else {
            Toast.makeText(this, R.string.err_no_location_permissions, Toast.LENGTH_SHORT).show();
        }

        List<Gasolineria> gasolinerias = getIntent().getParcelableArrayListExtra(EXTRA_GASOLINERIAS_LIST);

        if(gasolinerias==null||gasolinerias.size()==0){
            Toast.makeText(this, R.string.err_no_hay_gasolinerias_registradas, Toast.LENGTH_SHORT).show();
        }else{
            for(Gasolineria gasolineria: gasolinerias){
                addMarker(new LatLng(Double.parseDouble(gasolineria.getLatitud()),Double.parseDouble(gasolineria.getLongitud())), gasolineria.getNombre());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void addMarker(LatLng latLng, String title){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);
        mMap.addMarker(markerOptions);
    }
}