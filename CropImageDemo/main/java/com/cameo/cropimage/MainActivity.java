package com.cameo.cropimage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    ImageView image;
    MaterialTextView clickHereToSelectanImage;
    MaterialButton click,compressImage;
    private final int PICK_IMAGE =100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.activity_cameo_imageview);
        clickHereToSelectanImage = findViewById(R.id.activity_cameo_materialtextview);
        click = findViewById(R.id.activity_cameo_materialbtn_click);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startCropActivity();

            }
        });

        compressImage = findViewById(R.id.activity_cameo_materialbtn_compressImage);
        compressImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compressImage();

            }
        });

    }


    private void startCropActivity() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                image.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void compressImage() {
        try {
            BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
            Bitmap currentBitMap = drawable.getBitmap();
            Log.i(TAG, "before compressImage: "+currentBitMap.getByteCount());
            Bitmap imageBitmap = drawable.getBitmap();
            Log.i(TAG, "before compressImage: "+imageBitmap.getByteCount());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
            Log.i(TAG, "after compressImage: "+decoded.getByteCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        }