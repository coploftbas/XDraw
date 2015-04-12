package com.shema.justal.schema;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * The rectangle drawing on the screen.
 * Every rectangles is in fact a view where I have drawn a rectangle.
 * @author Justal Kevin
 */
public class Rectangle extends View {
    private float right;
    private float left;
    private float bottom;
    private float top;

    private Paint mPaintFinal;
    String color = "#FFFFFF";


    /**
     * The contructor of the rectangle
     * @param context The context
     * @param mStartX The x left position of the rectangle
     * @param mStartY The y top position of the rectangle
     * @param mx The x right position of the rectangle
     * @param my The y bot position of the rectangle
     */
    public Rectangle(Context context,float mStartX,float mStartY,float mx,float my) {
        super(context);
        this.right = mStartX > mx ? mStartX : mx;
        this.left = mStartX > mx ? mx : mStartX;
        this.bottom = mStartY > my ? mStartY : my;
        this.top = mStartY > my ? my : mStartY;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.d("Canvas", "!!!!");
        mPaintFinal = new Paint(Paint.DITHER_FLAG);
      //  mPaintFinal.setColor(getContext().getResources().getColor(android.R.color.holo_orange_dark));

        mPaintFinal.setColor(Color.parseColor(color));

       //  Log.d("Color", String.valueOf(android.R.color.holo_orange_dark));

        canvas.drawRect(left, top , right, bottom, mPaintFinal);
    }

    /**
     * Checking if the couple of position (x,y) is inside this rectangle.
     * @param x The x position that we want test
     * @param y The y position that we want test
     * @return True is the position is inside this rectangle, false if else.
     */
    public boolean inside(float x,float y) {
        if(Float.compare(left,x)<=0 && Float.compare(right,x)>=0 && Float.compare(top,y)<=0 && Float.compare(bottom,y)>=0) {
            return true;
        }
        return false;
    }

    /**
     * Checking if the rectangle that we want draw is upon this one.
     * @param left The left position of the possibly new rectangle
     * @param top The top position of the possibly new rectangle
     * @param right The right position of the possibly new rectangle
     * @param bottom The bottom position of the possibly new rectangle
     * @return True if the new rectangle is upon an other one.
     */
    public boolean on(float left,float top,float right,float bottom) {
        if((left<=this.left && right>=this.left && top<=this.top && bottom>=this.top) ||
           (left<=this.right && right>=this.right && bottom>=this.bottom && top<=this.bottom)){
                return true;
        }
        return false;
    }

    /**
     * Moving the rectangle as we want following the direction X or/and Y
     * @param x The x position that we want translate the rectangle
     * @param y The y position that we want translate the rectangle
     */
    public void setMove(float x,float y) {
        this.bottom+=y;
        this.top+=y;
        this.left+=x;
        this.right+=x;
    }

    void changeColor(String color) {

        Log.d("Change color", "Try to change color");
        //mPaintFinal = new Paint(Paint.DITHER_FLAG);
       this.color = color;
        invalidate();
    }
}

