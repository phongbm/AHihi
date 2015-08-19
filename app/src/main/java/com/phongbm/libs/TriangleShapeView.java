package com.phongbm.libs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class TriangleShapeView extends View {
    private int color = Color.WHITE;

    public TriangleShapeView(Context context) {
        super(context);
    }

    public TriangleShapeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TriangleShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        String colorString = attrs.getAttributeValue(null, "color");
        if (colorString != null) {
            color = Color.parseColor(colorString);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth() / 2;
        Path path = new Path();
        path.moveTo(w, 0);
        if (color == Color.parseColor("#4caf50")) {
            path.lineTo(2 * w, 0);
            path.lineTo(2 * w, w);
            path.lineTo(w, 0);
        } else {
            path.lineTo(0, 0);
            path.lineTo(0, w);
            path.lineTo(w, 0);
        }
        path.close();
        Paint p = new Paint();
        p.setColor(color);
        canvas.drawPath(path, p);
    }

}