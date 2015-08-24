package com.phongbm.image;

<<<<<<< HEAD
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.phongbm.ahihi.R;
import com.phongbm.common.CommonValue;
import com.phongbm.common.GlobalApplication;

import java.io.ByteArrayOutputStream;


public class ImageControl extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    private String url;

    private static final float SIZE_IMAGE = GlobalApplication.WIDTH_SCREEN;

    private RelativeLayout layoutImage;
    private TouchImageView image;
    private ImageView imageView;
    private Button btnCrop;

    private float radius;

    private Bitmap bmMain;
=======
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
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_control);
<<<<<<< HEAD
        url = getIntent().getStringExtra(ImageControl.EXTRA_IMAGE);
        initLayoutImage();
        intializeComponent();
    }

    private void initLayoutImage() {
        layoutImage = (RelativeLayout) findViewById(R.id.layoutImage);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) SIZE_IMAGE, (int) SIZE_IMAGE);
        params.gravity = Gravity.CENTER;
        layoutImage.setLayoutParams(params);
    }

    private void intializeComponent() {
        btnCrop = (Button) findViewById(R.id.btnCrop);
        btnCrop.setOnClickListener(this);
        image = (TouchImageView) findViewById(R.id.img);
        image.setMaxZoom(5);
        createBmMain();
        cropFadeCirle(radius);

    }

    private void createBmMain() {
        int widthResize, heightResize;
        try {
            bmMain = BitmapFactory.decodeFile(url);
            widthResize = GlobalApplication.WIDTH_SCREEN;
            heightResize = (int) ((float) GlobalApplication.WIDTH_SCREEN / bmMain.getWidth() * bmMain.getHeight());
            if (heightResize > GlobalApplication.HEIGHT_SCREEN) {
                heightResize = GlobalApplication.HEIGHT_SCREEN;
                widthResize = (int) ((float) GlobalApplication.HEIGHT_SCREEN / heightResize * widthResize);
            }
            bmMain = Bitmap.createScaledBitmap(bmMain, widthResize, heightResize, true);

        } catch (OutOfMemoryError e) {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(url, o);
            int width = o.outWidth;
            int height = o.outHeight;
            widthResize = width;
            heightResize = height;
            if (widthResize > GlobalApplication.WIDTH_SCREEN) {
                heightResize = (int) ((float) GlobalApplication.WIDTH_SCREEN
                        / widthResize * heightResize);
                widthResize = GlobalApplication.WIDTH_SCREEN;
            }
            if (heightResize > GlobalApplication.HEIGHT_SCREEN) {
                widthResize = (int) ((float) GlobalApplication.HEIGHT_SCREEN
                        / heightResize * widthResize);
                heightResize = GlobalApplication.HEIGHT_SCREEN;
            }

            bmMain = decodeSampledBitmapFromResource(url, widthResize, heightResize);
//            bmMain = Bitmap.createScaledBitmap(bmMain, widthResize,
//                    heightResize, true);

        }
        image.setImageBitmap(bmMain);
        imageView = (ImageView) findViewById(R.id.imageView);
        if (bmMain.getWidth() > bmMain.getHeight()) {
            radius = (float) heightResize / 2;
            if (radius * 2 > SIZE_IMAGE) radius = SIZE_IMAGE / 2;
        } else {
            radius = (float) widthResize / 2;
            if (radius * 2 > SIZE_IMAGE) radius = SIZE_IMAGE / 2;
            radius = SIZE_IMAGE / 2;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize * 2;
    }


    public static Bitmap decodeSampledBitmapFromResource(String uri,
                                                         int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(uri, options);
    }


    private void cropFadeCirle(float radius) {
        Bitmap bitmapFade = Bitmap.createBitmap(bmMain.getWidth(),
                bmMain.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasbitmapFade = new Canvas(bitmapFade);
        canvasbitmapFade.drawColor(Color.parseColor("#AF000000"));
        Paint eraser = new Paint();
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser.setAntiAlias(true);
        canvasbitmapFade.drawCircle(bmMain.getWidth() / 2,
                bmMain.getHeight() / 2, radius, eraser);
        imageView.setImageBitmap(bitmapFade);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCrop:
                cropimage();
                break;
        }
    }
    private void cropimage() {
        RectF rect = image.getZoomedRect();
        float currentZoom = image.getCurrentZoom();

        float leftReal = (rect.left * bmMain.getWidth());
        float topReal = (rect.top * bmMain.getHeight());
        float radiusReal = (radius / currentZoom);

        Bitmap bmCrop = Bitmap.createBitmap((int) (radiusReal * 2), (int) (radiusReal * 2), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bmCrop);

        canvas.drawBitmap(bmMain, new Rect((int) leftReal, (int) topReal, (int) (leftReal + radiusReal * 2), (int) (topReal + radiusReal * 2)),
                new Rect(0, 0, (int) (radiusReal * 2), (int) (radiusReal * 2)), null);
        bmCrop = Bitmap.createScaledBitmap(bmCrop, (int) (radius * 2), (int) (radius * 2), true);

        bmCrop = Bitmap.createScaledBitmap(bmCrop, 300, 300, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmCrop.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        Intent intent = new Intent();
        intent.putExtra(CommonValue.BYTE_AVATAR, bytes);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        if (bmMain != null) {
            bmMain.recycle();
            bmMain = null;
        }
        super.onDestroy();
    }
=======
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


>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
}