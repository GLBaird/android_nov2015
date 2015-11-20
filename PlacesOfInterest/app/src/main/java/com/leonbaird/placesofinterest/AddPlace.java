package com.leonbaird.placesofinterest;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddPlace extends AppCompatActivity implements LocationListener {

    // outlets
    private EditText placeName;
    private EditText placeDescription;
    private ImageView placeImage;

    // image data
    private Bitmap imageData;

    // geotagging
    private LocationManager locationManager;
    private Location currentLocation;

    // Request Codes
    private static final int REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // get outlets
        placeName = (EditText) findViewById(R.id.add_placename);
        placeDescription = (EditText) findViewById(R.id.add_description);
        placeImage = (ImageView) findViewById(R.id.add_imageView);

        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            // TODO: setup tablet UI
        }

    }

    static final int PERMISSION_REQUEST_LOCATION = 0;

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                Log.d("add_activity", "Permissions second ask");

                new AlertDialog.Builder(this)
                        .setTitle("Location Service Permission")
                        .setMessage("We need your location to tag the places you visit on a map")
                        .setCancelable(true)
                        .setPositiveButton("Activate", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                askPermissionForLocation();
                            }
                        })
                        .setNeutralButton("Cancel", null)
                        .create().show();

            } else {

                // No explanation needed, we can request the permission.

                Log.d("add_activity", "Permission being asked");
                askPermissionForLocation();

            }
        } else {
            Log.d("add_activity", "Permission granted for locations MAIN");
            setupLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("add_activity", "Permission granted for locations");
                    setupLocation();
                }
            }
        }
    }

    private void askPermissionForLocation() {
        Log.d("add_activity", "Asking permissions");
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_LOCATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d("add_activity", "Stop Updating");
            locationManager.removeUpdates(this);
        }

    }

    private void setupLocation() {
        Log.d("add_activity", "Location Setup");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            currentLocation = locationManager.getLastKnownLocation(provider);

            if (locationManager.isProviderEnabled(provider)) {
                Log.d("add_activity", "Request location updates");
                locationManager.requestLocationUpdates(provider, 5000, 20, this);
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Location services are disabled")
                        .setMessage("Do you want to enable them for geotagging your places?")
                        .setCancelable(false)
                        .setNegativeButton("NO", null)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent settings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(settings);
                            }
                        })
                        .create().show();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_camera:
                openCamera();
                return true;

            case R.id.action_save:
                savePlace();
                return true;
        }

        return false;
    }

    private void savePlace() {

        Place newPlace = new Place();
        newPlace.placeName = placeName.getText().toString();
        newPlace.placeDescription = placeDescription.getText().toString();

        if (currentLocation != null) {
            newPlace.geoLat = currentLocation.getLatitude();
            newPlace.geoLong = currentLocation.getLongitude();
        } else {
            Toast.makeText(this, "Can't geotag place!", Toast.LENGTH_SHORT).show();
        }

        if (imageData != null) {

            // generate filename from date
            String path = getFilesDir().getPath()+"/"+
                    new SimpleDateFormat("'image-'yyyyMMdd_HHmmss'.jpg'", Locale.US)
                            .format(newPlace.dateVisited);

            File imageFile = new File(path);

            try {
                FileOutputStream fos = new FileOutputStream(imageFile);
                imageData.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                newPlace.imagePath = path;
            } catch (java.io.IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to save image - "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        DataController.getDataController(this).addPlace(newPlace);

        // close activity
        finish();
    }

    private void openCamera() {
        Intent cameraActivity = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraActivity, REQUEST_CAMERA);
    }

    // Activity Results

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {

            imageData = (Bitmap) data.getExtras().get("data");
            placeImage.setImageBitmap(imageData);

        }

    }

    // Location Manager Listener Methods
    @Override
    public void onLocationChanged(Location location) {
        Log.d("add_activity", "Location Change "+location.toString());
        currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("add_activity", "Location status changed");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("add_activity", "Provider enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("add_activity", "Provider disabled");
    }
}
