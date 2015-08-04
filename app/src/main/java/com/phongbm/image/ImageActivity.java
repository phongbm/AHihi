package com.phongbm.image;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.phongbm.ahihi.R;

public class ImageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final int REQUEST_CODE = 0;

    private GridView gridViewImage;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initializeToolbar();
        initializeComponent();

        imageAdapter = new ImageAdapter(this);
        gridViewImage.setAdapter(imageAdapter);
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeComponent() {
        gridViewImage = (GridView) findViewById(R.id.gridViewImage);
        gridViewImage.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url = imageAdapter.getItem(position);
        ImageControl.launch(ImageActivity.this, view.findViewById(R.id.image), url);
        /*Intent intent = new Intent(ImageActivity.this, ImageControl.class);
        intent.putExtra(ImageControl.EXTRA_IMAGE, url);
        this.startActivityForResult(intent, REQUEST_CODE);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}