package com.phongbm.image;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.phongbm.ahihi.R;
import com.phongbm.libs.SquareImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> imageURLs;
    private ArrayList<ImageState> imageStates;
    private LayoutInflater layoutInflater;

    public ImageAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        initializeListImage();
        initializeListImageState();
    }

    private void initializeListImage() {
        imageURLs = new ArrayList<String>();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media.DATA}, null, null,
                MediaStore.Images.Media.DATE_ADDED + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            imageURLs.add(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            cursor.moveToNext();
        }
        return;
    }

    private void initializeListImageState() {
        imageStates = new ArrayList<ImageState>();
        for (int i = 0; i < imageURLs.size(); i++) {
            imageStates.add(new ImageState());
        }
    }

    @Override
    public int getCount() {
        return imageURLs.size();
    }

    @Override
    public String getItem(int position) {
        return imageURLs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquareImageView imgImage;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_image, parent, false);
            imgImage = (SquareImageView) convertView.findViewById(R.id.imgImage);
            convertView.setTag(imgImage);
        } else {
            imgImage = (SquareImageView) convertView.getTag();
        }
        if (!imageStates.get(position).isFinish) {
            imgImage.setImageResource(R.drawable.download);
        }
        if (imageStates.get(position).isFinish) {
            imgImage.setImageBitmap(imageStates.get(position).image);
        } else {
            if (!imageStates.get(position).isLoading) {
                imageStates.get(position).isLoading = true;
                (new ImageAsyncTask(position, imgImage)).execute(imageURLs.get(position));
            }
        }


        return convertView;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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
        return inSampleSize;
    }

    private Bitmap decodeSampledBitmapFromMemory(String data, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(data, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(data, options);
    }

    private class ImageState {
        Bitmap image;
        boolean isLoading = false, isFinish = false;
    }

    private class ImageAsyncTask extends AsyncTask<String, Bitmap, Void> {
        int position = -1;
        ImageView picture = null;

        public ImageAsyncTask(int position, ImageView picture) {
            this.position = position;
            this.picture = picture;
        }

        @Override
        protected Void doInBackground(String... params) {
            Bitmap bitmap = decodeSampledBitmapFromMemory(params[0], 100, 100);
            publishProgress(bitmap);
            return null;
        }

        @Override
        protected void onProgressUpdate(Bitmap... values) {
            picture.setImageBitmap(values[0]);
            imageStates.get(this.position).image = values[0];
            imageStates.get(this.position).isFinish = true;
        }
    }

}