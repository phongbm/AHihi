package com.phongbm.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.phongbm.ahihi.R;

public class ImageControl extends AppCompatActivity {
    public static final String EXTRA_IMAGE = "ImageControl:image";

    public static int WIDTH_SCREEN, HEIGHT_SCREEN;

    private RelativeLayout layoutCropImage;
    private MySurfaceView mySurfaceView;
    private String url;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_control);
        getScreenSize();
        initializeLayoutCropImage();
        drawCircleFade();

        url = getIntent().getStringExtra(EXTRA_IMAGE);
        mySurfaceView = (MySurfaceView) findViewById(R.id.mySurfaceView);
        mySurfaceView.startThread(url);
    }

    private void getScreenSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        WIDTH_SCREEN = metrics.widthPixels;
        HEIGHT_SCREEN = metrics.heightPixels;
    }

    private void initializeLayoutCropImage() {
        layoutCropImage = (RelativeLayout) findViewById(R.id.layoutCropImage);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(WIDTH_SCREEN, WIDTH_SCREEN);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        layoutCropImage.setLayoutParams(params);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    private void drawCircleFade() {
        Bitmap bm = Bitmap.createBitmap(WIDTH_SCREEN, WIDTH_SCREEN, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        canvas.drawColor(Color.parseColor("#904caf50"));
        Paint p = new Paint();
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        p.setAntiAlias(true);
        canvas.drawCircle(WIDTH_SCREEN / 2, WIDTH_SCREEN / 2, WIDTH_SCREEN / 2, p);
        imageView.setImageBitmap(bm);
    }

    public static Bitmap getReziseBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int heiht = bitmap.getHeight();
        float scaleWidth = (float) newWidth / width;
        float scaleHeight = (float) newHeight / heiht;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, heiht, matrix, false);
    }

    @Override
    protected void onDestroy() {
        mySurfaceView.setRunning(false);
        super.onDestroy();
    }


}