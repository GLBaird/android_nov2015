package com.leonbaird.activites02;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // constants
    private static final String ACTIVITY_TAG      = "main_activity";
    private static final String BUNDLE_USERNAME   = "username";
    public  static final String INTENT_KEY_DATA   = "data";
    private static final int    REQUEST_KEY       = 0;
    private static final int    REQUEST_KEY_PHONE = 1;

    // properties
    private String username = "Bob";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load from bundle
        if (savedInstanceState != null) {
            username = savedInstanceState.getString(BUNDLE_USERNAME);
        }

        Log.d(ACTIVITY_TAG, "Username is "+username);

        username = "Leon";

        // setup buttons
        Button openButton = (Button) findViewById(R.id.main_button_open);
        Button sendButton = (Button) findViewById(R.id.main_button_send);

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondActivityIntent = new Intent(MainActivity.this, DataInActivity.class);
                startActivity(secondActivityIntent);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textField = (EditText) findViewById(R.id.main_edittext);
                String textValue = textField.getText().toString();

                Intent secondActivityWithDataIntent = new Intent(MainActivity.this, DataInActivity.class);
                secondActivityWithDataIntent.putExtra(DataInActivity.INTENT_KEY_TEXT, textValue);
                startActivity(secondActivityWithDataIntent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_USERNAME, username);
        Log.d(ACTIVITY_TAG, "State saved in bundle");
    }

    public void passDataToNextActivity(View clickedButton) {
        EditText textField = (EditText) findViewById(R.id.main_edittext);
        String textValue = textField.getText().toString();

        Intent secondActivityWithDataIntent = new Intent(MainActivity.this, DataInActivity.class);
        secondActivityWithDataIntent.putExtra(DataInActivity.INTENT_KEY_TEXT, textValue);

        startActivityForResult(secondActivityWithDataIntent, REQUEST_KEY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_KEY && resultCode == RESULT_OK) {
            EditText textField = (EditText) findViewById(R.id.main_edittext);
            String valueReturned = data.getStringExtra(INTENT_KEY_DATA);
            textField.setText(valueReturned);
        } else if (requestCode == REQUEST_KEY_PHONE && resultCode == RESULT_OK) {

            // unpack results from contact list
            Uri contactURI = data.getData();

            // make column projection
            String[] projection = {
                    ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };

            Cursor cursor = getContentResolver().query(contactURI, projection, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();

                int nameCol  = cursor.getColumnIndex( ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME );
                int phoneCol = cursor.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER );

                String contactName  = cursor.getString(nameCol);
                String contactPhone = cursor.getString(phoneCol);

                Toast.makeText(this, "Name: "+contactName+", Phone: "+contactPhone, Toast.LENGTH_SHORT).show();

                cursor.close();
            }



        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_information:
                showInformation();
                break;

            case R.id.action_hello:
                sayHello();
                break;

            case R.id.action_dialogue:
                showDialogue();
                break;

            case R.id.action_contacts:
                showContacts();
                break;

            default:
                return false;

        }

        return true;
    }

    private void sayHello() {
        Toast.makeText(this, R.string.main_code_hi, Toast.LENGTH_SHORT).show();
    }

    private void showInformation() {
        Toast.makeText(this, getString(R.string.main_code_username)+username, Toast.LENGTH_SHORT).show();
    }


    private void showDialogue() {
        Intent dialogueActivity = new Intent(this, DialogueActivity.class);
        startActivity(dialogueActivity);
    }

    private void showContacts() {
        Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContact, REQUEST_KEY_PHONE);
    }
}
