package com.leonbaird.placesofinterest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class AddPlaceActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {

    // Outlets
    private EditText placeName;
    private EditText placeDescription;
    private ImageView placeImage;

    // image data
    private Bitmap imageData;

    // geotagging
    private LocationManager locaMan;
    private String provider;
    private Location currentLocation;

    // Request codes
    private static final int REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // get outlets
        placeName = (EditText) findViewById(R.id.add_name);
        placeDescription = (EditText) findViewById(R.id.add_description);
        placeImage = (ImageView) findViewById(R.id.add_image);

        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            // on tablet need onscreen buttons that are not in the action bar
            ImageButton camera = (ImageButton) findViewById(R.id.add_button_camera);
            Button cancel = (Button) findViewById(R.id.add_button_Cancel);
            Button save = (Button) findViewById(R.id.add_button_save);

            camera.setOnClickListener(this);
            cancel.setOnClickListener(this);
            save.setOnClickListener(this);
        }

        // setup geolocation
        locaMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locaMan.getBestProvider(criteria, false);
        currentLocation = locaMan.getLastKnownLocation(provider);

        if (!locaMan.isProviderEnabled(provider)) {
            new AlertDialog.Builder(this)
                    .setTitle("Locaton services are disabled!")
                    .setMessage("Do you want to enable them for geotagging your places?")
                    .setCancelable(false)
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent enableLocationServices = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(enableLocationServices);
                        }
                    }).create().show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        locaMan.requestLocationUpdates(provider, 5000, 1, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_menu_clear:
                clearForm();
                return true;
            case R.id.add_menu_camera:
                pickSource();
                return true;
            case R.id.add_menu_save:
                savePlace();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Action methods

    private void savePlace() {
        // make model
        Place data = new Place();
        data.placeName = placeName.getText().toString();
        data.placeDescription = placeDescription.getText().toString();

        // location?
        if (currentLocation != null) {
            data.geoLat  = currentLocation.getLatitude();
            data.geoLong = currentLocation.getLongitude();
        } else {
            Toast.makeText(this, "Cannot get current location!", Toast.LENGTH_LONG).show();
        }

        // deal with image data
        if (imageData != null) {

            // generate the image path
            String filepath = getFilesDir().getPath()+"/"+
                    new SimpleDateFormat("'image-'yyyyMMdd_HHmmss'.jpg'", Locale.US)
                            .format(data.dateVisited);

            // make a file
            File imageFile = new File(filepath);

            try {
                FileOutputStream fos = new FileOutputStream(imageFile);
                imageData.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.close();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to save image! Aborting save.", Toast.LENGTH_SHORT).show();
                return;
            }

            data.imagePath = filepath;
        }

        // add data to store
        DataController.getController(this).addPlace(data);
        finish();
    }

    private void pickSource() {
        Intent cameraActivity = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraActivity, REQUEST_CAMERA);
    }

    private void clearForm() {
        placeName.setText("");
        placeDescription.setText("");
        imageData = null;
        placeImage.setImageBitmap(null);
    }

    // Acvitivity callbacks

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check from camera
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            imageData = (Bitmap) data.getExtras().get("data");
            placeImage.setImageBitmap(imageData);
        }
    }


    // Location Listening Methods

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        Log.d("add_place", "Location found: "+currentLocation.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //ignore
    }

    @Override
    public void onProviderEnabled(String provider) {
        //ignore
    }

    @Override
    public void onProviderDisabled(String provider) {
        //ignore
    }

    @Override
    public void onClick(View v) {
        // handle button clicks on tablet
        switch (v.getId()) {
            case R.id.add_button_Cancel:
                finish();
                break;
            case R.id.add_button_camera:
                pickSource();
                break;
            case R.id.add_button_save:
                savePlace();
                break;
        }
    }
}
