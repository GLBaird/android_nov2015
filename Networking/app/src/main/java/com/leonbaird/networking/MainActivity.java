package com.leonbaird.networking;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // outlets
    TextView log;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        log = (TextView) findViewById(R.id.main_log);
        imageView = (ImageView) findViewById(R.id.main_imageview);

        // setup the log for scrolling multi-line text
        log.setMovementMethod(new ScrollingMovementMethod());

        performGetRequest();

        performPostRequest();

        downloadImage();

    }

    private void downloadImage() {
        RequestPackage p = new RequestPackage();
        p.setUri("http://leonbaird.co.uk/iphone/large.jpg");

        BackgroundImageDownloader bt = new BackgroundImageDownloader();
        bt.execute(p);
    }

    private void performPostRequest() {
        RequestPackage p = new RequestPackage();
        p.setUri("http://leonbaird.co.uk/iphone/input.php");
        p.setMethod(RequestPackage.METHOD_POST);
        p.setParam("username", "Leon Baird");
        p.setParam("SomethingCool", "Hi there from everyone");

        BackgroundDownloadText bt = new BackgroundDownloadText();
        bt.execute(p);
    }

    private void performGetRequest() {
        RequestPackage p = new RequestPackage();
        p.setUri("http://leonbaird.co.uk/iphone/input.php");
        p.setMethod(RequestPackage.METHOD_GET);
        p.setParam("username", "Leon Baird");
        p.setParam("APIKey", "LBsjdhdiu2837646");

        BackgroundDownloadText bt = new BackgroundDownloadText();
        bt.execute(p);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Background Tasks

    private class BackgroundDownloadText extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            log.append("\nStarting to download Request for string...");
        }

        @Override
        protected String doInBackground(RequestPackage... params) {
            return HTTPManager.getDataAsString( params[0] );
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            if (data != null) {
                log.append("\n*** Data Received from Request:\n"+data);
            } else {
                log.append("\nERROR - Failed to download string: "+HTTPManager.errorReason);
            }

            log.append("\n****END OF REQUEST****");
        }
    }

    private class BackgroundImageDownloader extends AsyncTask<RequestPackage, String, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            log.append("\n*** Starting to download image");
        }

        @Override
        protected Bitmap doInBackground(RequestPackage... params) {
            return HTTPManager.getDataAsBitmap(params[0]);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            log.append("\n Progress Update:");
            for (String value : values) {
                log.append("\n\t"+value);
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null) {
                log.append("\n*** Image data received");
                imageView.setImageBitmap(bitmap);
            } else {
                log.append("\nError - failed to download image: "+HTTPManager.errorReason);
            }

            log.append("\n***** END OF REQUEST *****");
        }
    }
}
