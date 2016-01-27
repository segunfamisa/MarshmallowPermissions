package com.segunfamisa.sample.marshmallow.permissions;

/**
 * Camera preview
 *
 * Created by segun.famisa on 27/01/2016.
 */

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

import java.io.IOException;

/**
 * A {@link SurfaceView} that can be adjusted to a specified aspect ratio.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;

    private SurfaceHolder mHolder;

    private Camera mCamera;

    public CameraPreview(Context context) {
        this(context, null);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets a camera and sets it up for use
     * @param camera {@link Camera} to use
     */
    public void setCamera(Camera camera){
        mCamera = camera;

        setupCamera();
    }

    private void setupCamera(){
        mCamera.setDisplayOrientation(90);

        //get the holder and set callback
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
    }


    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that
     * is, calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height);
        } else {
            if (width < height * mRatioWidth / mRatioHeight) {
                setMeasuredDimension(width, width * mRatioHeight / mRatioWidth);
            } else {
                setMeasuredDimension(height * mRatioWidth / mRatioHeight, height);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try{
            //set the camera to draw the view
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException ioe){

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //stop preview before orientation change
        if(mHolder.getSurface() == null){
            return;
        }

        try{
            mCamera.stopPreview();
        } catch (Exception e){

        }

        try{
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException ioe){

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
    }
}
