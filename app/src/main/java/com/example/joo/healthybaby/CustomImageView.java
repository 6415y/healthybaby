package com.example.joo.healthybaby;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Allows to draw Circle on ImageView.
 *
 * @author Maciej Nux Jaros
 *
 * - Custom by lolhi
 */

public class CustomImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint currentPaint;
    public boolean drawCircle = false;
    public float XVlaue;
    public float YValue;
    public int colorValue;

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        currentPaint = new Paint();
        currentPaint.setDither(true);
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);
        currentPaint.setStrokeCap(Paint.Cap.ROUND);
        currentPaint.setStrokeWidth(7);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        currentPaint.setColor(colorValue);
        if (drawCircle)
        {
            canvas.drawCircle(XVlaue,YValue,10,currentPaint);
        }
    }
}