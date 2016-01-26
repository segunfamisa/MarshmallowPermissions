package com.segunfamisa.sample.marshmallow.permissions;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button mButtonOpenCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonOpenCamera = (Button) findViewById(R.id.button_open_camera);
        mButtonOpenCamera.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view == mButtonOpenCamera){
            //check for permissions
            //launch camera preview activity
        }
    }
}
