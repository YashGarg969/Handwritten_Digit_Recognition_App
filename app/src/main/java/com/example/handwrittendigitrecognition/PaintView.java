package com.example.handwrittendigitrecognition;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PaintView extends View {
    public static ArrayList<Path> pathList = new ArrayList<>();
    public static ArrayList<Integer> colorList = new ArrayList<>();
    public static int currColor = Color.BLACK;
    Paint paintbrush = new Paint();
    public static boolean sizeChanged=false;
    public int width;
    public int height;
    public PaintView(Context context) {
        super(context);
        initializeBrushStroke();
        onAttachedToWindow();
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeBrushStroke();
        onAttachedToWindow();
    }

    private void initializeBrushStroke() {
        paintbrush.setAntiAlias(true);
        paintbrush.setColor(currColor);
        paintbrush.setStyle(Paint.Style.STROKE);
        paintbrush.setStrokeJoin(Paint.Join.ROUND);
        paintbrush.setStrokeWidth(8f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Path path = new Path();
                path.moveTo(x, y);
                PaintView.pathList.add(path);
                PaintView.colorList.add(currColor);
                break;
            case MotionEvent.ACTION_MOVE:
                PaintView.pathList.get(PaintView.pathList.size() - 1).lineTo(x, y);
                break;
            default:
                return false;
        }
        postInvalidate();
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        sizeChanged = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < PaintView.pathList.size(); i++) {
            paintbrush.setColor(PaintView.colorList.get(i));
            canvas.drawPath(PaintView.pathList.get(i), paintbrush);
        }
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener to avoid multiple callbacks
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Your code here
                // Access the final width and height values and perform the necessary operations.
                width = getWidth();
                height = getHeight();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        requestLayout();
    }

    public Bitmap getBitmapFromView() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        return bitmap;
    }
}