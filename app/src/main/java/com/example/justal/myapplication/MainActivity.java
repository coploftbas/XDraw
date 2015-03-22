package com.example.justal.myapplication;

import android.app.Activity;
import android.os.Bundle;
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
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private float x1=0,x2=0,y1=0,y2=0,tmp=0;
    private List<Drawing> list=new ArrayList<Drawing>();
    private boolean drawing=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_main, null, false);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1=event.getX();
                y1=event.getY();
                drawing=true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(drawing) {
                    x2 = event.getX();
                    y2 = event.getY();
                    if (x1 > x2) {
                        tmp = x1;
                        x1 = x2;
                        x2 = tmp;
                    }
                    if (y1 < y2) {
                        tmp = y1;
                        y1 = y2;
                        y2 = tmp;
                    }
                        View view = getLayoutInflater().inflate(R.layout.activity_main, null, false);
                        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);
                        Drawing tmpDrawing = new Drawing(this, x1, x2, y1, y2);
                        this.list.add(tmpDrawing);
                        ll.addView(tmpDrawing);
                        setContentView(view);
                }
                drawing=false;
                break;
        }
        return true;
    }

    public boolean inside(float x,float y) {
        for(int i=0;i<list.size();i++) {
            if(x>x1 && x<x2 && y>y2 && y<y1) {
                return true;
            }
        }
        return false;
    }
}
