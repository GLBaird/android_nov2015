package com.leonbaird.drawingandmedia;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class DrawingDemo extends View {
    public DrawingDemo(Context context) {
        super(context);
    }

    public DrawingDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawingDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawingDemo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint strokePaint = new Paint();
        strokePaint.setColor(Color.RED);
        strokePaint.setStrokeWidth(3);
        strokePaint.setStyle(Paint.Style.STROKE);

        Paint fillPaint = new Paint();
        fillPaint.setColor(Color.BLUE);
        fillPaint.setStyle(Paint.Style.FILL);

        Rect square = new Rect(100, 100, 50, 50);

        canvas.drawRect(square, strokePaint);
        canvas.drawRect(square, fillPaint);
    }
}
