package com.avenueinfotech.panoramastreetview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

    // Cole St, San Fran
    private static final LatLng SAN_FRAN = new LatLng(37.765927, -122.449972);

    private StreetViewPanorama mStreetViewPanorama;

    private CheckBox mStreetNameCheckbox;

    private CheckBox mNavigationCheckbox;

    private CheckBox mZoomCheckbox;

    private CheckBox mPanningCheckbox;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStreetNameCheckbox = (CheckBox) findViewById(R.id.streetnames);
        mNavigationCheckbox = (CheckBox) findViewById(R.id.navigation);
        mZoomCheckbox = (CheckBox) findViewById(R.id.zoom);
        mPanningCheckbox = (CheckBox) findViewById(R.id.panning);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)
                        getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        mStreetViewPanorama = panorama;
                        mStreetViewPanorama.setStreetNamesEnabled(mStreetNameCheckbox.isChecked());
                        mStreetViewPanorama
                                .setUserNavigationEnabled(mNavigationCheckbox.isChecked());
                        mStreetViewPanorama.setZoomGesturesEnabled(mZoomCheckbox.isChecked());
                        mStreetViewPanorama.setPanningGesturesEnabled(mPanningCheckbox.isChecked());

                        // Only set the panorama to SAN_FRAN on startup (when no panoramas have been
                        // loaded which is when the savedInstanceState is null).
                        if (savedInstanceState == null) {
                            mStreetViewPanorama.setPosition(SAN_FRAN);
                        }
                    }
                });
    }

    private boolean checkReady() {
        if (mStreetViewPanorama == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onStreetNamesToggled(View view) {
        if (!checkReady()) {
            return;
        }
        mStreetViewPanorama.setStreetNamesEnabled(mStreetNameCheckbox.isChecked());
    }

    public void onNavigationToggled(View view) {
        if (!checkReady()) {
            return;
        }
        mStreetViewPanorama.setUserNavigationEnabled(mNavigationCheckbox.isChecked());
    }

    public void onZoomToggled(View view) {
        if (!checkReady()) {
            return;
        }
        mStreetViewPanorama.setZoomGesturesEnabled(mZoomCheckbox.isChecked());
    }

    public void onPanningToggled(View view) {
        if (!checkReady()) {
            return;
        }
        mStreetViewPanorama.setPanningGesturesEnabled(mPanningCheckbox.isChecked());
    }
}
