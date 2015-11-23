package com.leonbaird.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private static final int CAMERA_INTENT_REQUEST_CODE = 100;
	
	private TextView  tvResultURL;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvResultURL = (TextView) findViewById(R.id.tvURL);
		imageView   = (ImageView) findViewById(R.id.imageView);
		
		Button cameraButton = (Button) findViewById(R.id.button);
		cameraButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View button) {
				
		// open camera intent
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);		
		startActivityForResult(cameraIntent, CAMERA_INTENT_REQUEST_CODE);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == CAMERA_INTENT_REQUEST_CODE) {
			
			if(resultCode == RESULT_OK) {
				// image received
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				imageView.setImageBitmap(bm);
				
				// save to disk
				File imageFile = generateImageFilePath();
				if(imageFile == null) {
					Toast message = Toast.makeText(this, "Cannot Create Image Path", Toast.LENGTH_SHORT);
					message.show();
					return;
				}
				
				try {
					
					FileOutputStream fos = new FileOutputStream(imageFile);
					bm.compress(Bitmap.CompressFormat.JPEG, 90,	fos);
					fos.close();
					
					tvResultURL.setText(imageFile.getAbsolutePath());
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else if(resultCode == RESULT_CANCELED) {
				// user cancelled images
			} else {
				// capture failed
			}
			
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	private File generateImageFilePath() {		
		
		File filesDIR = getFilesDir();
			
		// create filename based on date
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile = new File(filesDIR.getPath() + File.separator + "IMG_" +timestamp+ ".jpg");
				
		return mediaFile;
	}

}
