package com.phongbm.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.Image;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Runnable {
    private Bitmap bitmap = null;
    private boolean running = true;
    private String url;
    private Thread thread;
    private int x1, y1, x2, y2;

    public MySurfaceView(Context context) {
        super(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startThread(String url) {
        this.url = url;
        bitmap = BitmapFactory.decodeFile(url);
        if (bitmap.getWidth() < ImageControl.WIDTH_SCREEN || bitmap.getHeight() < ImageControl.WIDTH_SCREEN) {
            if (bitmap.getWidth() < bitmap.getHeight()) {
                bitmap = ImageControl.getReziseBitmap(bitmap, ImageControl.WIDTH_SCREEN,
                        (int) (bitmap.getHeight() * ((float) ImageControl.WIDTH_SCREEN / bitmap.getWidth())));
            } else {
                bitmap = ImageControl.getReziseBitmap(bitmap,
                        (int) (bitmap.getWidth() * ((float) ImageControl.WIDTH_SCREEN / bitmap.getHeight())),
                        ImageControl.WIDTH_SCREEN);
            }
        }


        x1 = Math.abs(bitmap.getWidth() - ImageControl.WIDTH_SCREEN) / 2;
        y1 = Math.abs(bitmap.getHeight() - ImageControl.WIDTH_SCREEN) / 2;
        x2 = x1 + ImageControl.WIDTH_SCREEN;
        y2 = y1 + ImageControl.WIDTH_SCREEN;
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }


    @Override
    public void run() {
        while (running) {
            SurfaceHolder holder = getHolder();
            if (holder != null) {
                Canvas canvas = holder.lockCanvas();
                if (canvas == null) continue;
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, new Rect(x1, y1, x2, y2),
                            new Rect(0, 0, ImageControl.WIDTH_SCREEN, ImageControl.WIDTH_SCREEN), null);
                }
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}