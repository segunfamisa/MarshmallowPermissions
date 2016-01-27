package com.segunfamisa.sample.marshmallow.permissions;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

public class CameraPreviewActivity extends AppCompatActivity{



    /**
     * Custom {@link SurfaceView} to hold the camera preview
     */
    CameraPreview mCameraPreview;

    /**
     * Hardware camera
     */
    Camera mCamera;

    /**
     * request code for camera permission
     */
    private static final int REQUEST_PERMISSION_CAMERA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);

        mCameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
    }

    @Override
    protected void onResume() {
        super.onResume();

        openAndAttachCamera();
    }

    /**
     * Checks for permission and requests for necessary missing permissions before opening camera
     */
    private void openAndAttachCamera(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //if permission is not granted.
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                //this is an utility method to handle case where the user has previously declined.
                //you may need to show an explanation.
                Snackbar.make(mCameraPreview, "You need to enable this app to access your camera",
                        Snackbar.LENGTH_INDEFINITE).setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //open settings
                        showInstalledAppDetails(getApplicationContext(), getPackageName());
                    }
                }).show();
            } else {
                //request for permission. You can listen for a response via onRequestPermissionsResult method.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
            }
        } else {
            //if permission is granted.
            try{
                mCamera = Camera.open();
            } catch (Exception e){

            }

            if(mCamera != null){
                mCameraPreview.setCamera(mCamera);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION_CAMERA) {
            //for the camera permission request
            //check if the grantResults array is not empty and it contains PackageManager.PERMISSION_GRANTED
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //openCamera
                openAndAttachCamera();
            } else{
                //permission has been denied. Disable the request.
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Method to launch the app info settings page
     * @param context
     * @param packageName
     */
    public static void showInstalledAppDetails(Context context, String packageName) {
        if (context == null) {
            return;
        }
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);

    }

}
