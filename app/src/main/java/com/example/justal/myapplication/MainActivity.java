package com.example.justal.myapplication;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private float x1=0,x2=0,y1=0,y2=0,tmp=0;
    private Drawing drawing;
    private LinearLayout ll;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_main, null, false);
        ll = (LinearLayout) view.findViewById(R.id.ll);

        drawing = new Drawing(this);
        drawing.add(new Rectangle(0, 0, 200, 350));
        ll.addView(drawing);
        setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Window win = getWindow();
        int contentViewTop = win.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1=event.getX();
                y1=event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                 x2=event.getX();
                 y2=event.getY();
                 if(!drawing.testInside(x1,y1- contentViewTop) && !drawing.testInside(x2,y2- contentViewTop)) {
                     drawing.add(new Rectangle(x1, y1 - contentViewTop, x2, y2 - contentViewTop));
                     setContentView(view);
                 }
                break;

        }
        return true;
    }
}
