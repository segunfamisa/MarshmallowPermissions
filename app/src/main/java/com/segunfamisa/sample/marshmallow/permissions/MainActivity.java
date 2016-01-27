package com.segunfamisa.sample.marshmallow.permissions;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    Button mButtonStartCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonStartCamera = (Button) findViewById(R.id.button_open_camera);
        mButtonStartCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start camera preview activity
                Intent intent = new Intent(MainActivity.this, CameraPreviewActivity.class);
                startActivity(intent);
            }
        });
    }
}
