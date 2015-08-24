package com.phongbm.image;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_control);
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
}