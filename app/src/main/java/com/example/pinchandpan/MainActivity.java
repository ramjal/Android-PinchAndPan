package com.example.pinchandpan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private final String DEBUG_TAG = MainActivity.class.getSimpleName();
    private final Float MIN_SIZE = 0.25F;
    private final Float MAX_SIZE = 2f;
    private ImageView mImageView;
    private Matrix mMatrix = new Matrix();
    Float currentScale = 0.75f;
    Float currentTranslate = -200f;
    ScaleGestureDetector mScaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        mImageView = findViewById(R.id.imageView);
        mMatrix.setScale(currentScale, currentScale);
        mImageView.setImageMatrix(mMatrix);
        mImageView.setOnTouchListener(new ImageOnTouchListener());
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            currentScale = currentScale * detector.getScaleFactor();
            currentScale = Math.max(MIN_SIZE, Math.min(currentScale, MAX_SIZE));
            mMatrix.setScale(currentScale, currentScale);
            //mMatrix.setTranslate(currentTranslate, currentTranslate);
            mImageView.setImageMatrix(mMatrix);
            return true;
        }

    }

    private class ImageOnTouchListener implements View.OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            int x = Math.round(event.getRawX());
            int y = Math.round(event.getRawY());

            mScaleGestureDetector.onTouchEvent(event);

            switch (action) {
                case (MotionEvent.ACTION_DOWN):
                    Log.d(DEBUG_TAG,"Action was DOWN");
                    return true;
                case (MotionEvent.ACTION_MOVE):
                    Log.d(DEBUG_TAG, String.format("MOVE: %d, %d", x, y));
                    return true;
                case (MotionEvent.ACTION_UP):
                    Log.d(DEBUG_TAG,"Action was UP");
                    return true;
                case (MotionEvent.ACTION_CANCEL):
                    Log.d(DEBUG_TAG,"Action was CANCEL");
                    return true;
                case (MotionEvent.ACTION_OUTSIDE):
                    Log.d(DEBUG_TAG,"Movement occurred outside bounds of current screen element");
                    return true;
                default:
                    return true;
            }
        }
    }
}