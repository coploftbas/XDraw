package com.example.justal.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justal on 22/03/2015.
 */
public class Drawing extends View {
    List<Rectangle> list = new ArrayList<Rectangle>();

    public Drawing(Context context)
    {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        paint.setColor(Color.parseColor("#CD5C5C"));
        for(int i=0;i<list.size();i++) {
            canvas.drawRect(list.get(i).getX1(), list.get(i).getY1(), list.get(i).getX2(), list.get(i).getY2(), paint);
        }
    }

    /**
     * adding the rectangle to the list of rectangle
     * @param rectangle
     */
    public void add(Rectangle rectangle) {

        list.add(rectangle);
    }

    public boolean testInside(float x,float y) {
        for(int i=0;i<list.size();i++) {
            if(list.get(i).inside(x,y)) {
                return true;
            }
        }
        return false;
    }
}
