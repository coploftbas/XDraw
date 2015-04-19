package com.shema.justal.schema;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List; 

/**
 * The main program
 * Taking all the frame and putting on !
 *
 * @author Justal Kevin
 */
public class Main extends ActionBarActivity implements Constants {
    private FrameLayout frame;
    private List<Rectangle> listRectangle = new ArrayList<Rectangle>();
    private static int idFrame = 0;

    private float mStartX;
    private float mStartY;
    private float mx;
    private float my;

    private String currentColor;

    private boolean drawingMode = false;
    private boolean movingMode = false;
    private boolean changeColor = false;

    private FrameLayout.LayoutParams params;
    private int left, top, index;
    private float tmpX = 0, tmpY = 0, newX = 0, newY = 0;

    private int contentViewTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame = (FrameLayout) findViewById(R.id.framelayout);

        //Log.d("Create","O");

    }


    Rectangle rectangle;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Calculate the fucking size of the bar - There is certainly a way for passing this
        Window win = getWindow();
        contentViewTop = win.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int correctionTMP = 236; //Changement inComming, why 236 ? (31) for the picture, (contentView) status bar
        mx = event.getX();
        my = event.getY() - contentViewTop - correctionTMP; //TODO There is an error there, the my has to be zero on the left corner. The only way to do this is to minus this value by 236, but why 236 ?
        Log.d("Tourh Event",String.valueOf(my));
        Log.d("Tourh Event",String.valueOf(contentViewTop));
        /*if (!drawingMode && !movingMode) {
            if (!inside(mx, my)) {
                Toast.makeText(Main.this, "inside", Toast.LENGTH_LONG).show();
                drawingMode = true;
            } else {
                movingMode = true;
            }
        }*/

        /*if (drawingMode) {
            onTouchEventRectangle(event);
        } else*/

        if (inside(mx, my)) {
           // Toast.makeText(Main.this, "inside", Toast.LENGTH_LONG).show();
            Log.d("Tourh Event","inside");

            //rectangle = new Rectangle(this);
            if(changeColor == true){
                onTouchEventChangeColor(event);
                changeColor = false;
            }
            movingMode = true;
        }

        if (movingMode) {
          onTouchEventMoveRectangle(event);
           // Log.d("Touch event", "Moving");
        } else {
           // Toast.makeText(Main.this, "else", Toast.LENGTH_LONG).show();
            Log.d("Touch Event","else");

//            throw new IllegalArgumentException("Impossible");
        }

        return true;
    }

//    private void onTouchEventRectangle(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mStartX = mx;
//                mStartY = my;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
////                drawRectangle();//comment by sanin
//                drawingMode = false;
//                break;
//        }
//    }

    private void onTouchEventChangeColor(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Position
                tmpX = event.getX();
                tmpY = event.getY() - contentViewTop;

                // LayoutParam
                index = -1;
                for (int i = 0; i < listRectangle.size(); i++) {
                    if (listRectangle.get(i).inside(mx, my)) {
                        index = i;
                    }
                }
                params = (FrameLayout.LayoutParams) frame.getChildAt(index).getLayoutParams();
                left = params.leftMargin;
                top = params.topMargin;

                Log.d("Change color","Start to change color");
                listRectangle.get(index).changeColor(currentColor);

                break;

            case MotionEvent.ACTION_UP:
               // movingMode = false;
                break;
        }

    }

    private void onTouchEventMoveRectangle(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Position
                tmpX = event.getX();
                tmpY = event.getY() - contentViewTop;

                // LayoutParam
                index = -1;
                for (int i = 0; i < listRectangle.size(); i++) {
                    if (listRectangle.get(i).inside(mx, my)) {
                        index = i;
                    }
                }
                params = (FrameLayout.LayoutParams) frame.getChildAt(index).getLayoutParams();
                left = params.leftMargin;
                top = params.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:
                newX = event.getX();
                newY = event.getY() - contentViewTop;
                params.leftMargin = left + (int) (newX - tmpX);
                params.topMargin = top + (int) (newY - tmpY);
                listRectangle.get(index).setMove((int) (newX - tmpX), (int) (newY - tmpY));
                frame.getChildAt(index).setLayoutParams(params);
                tmpX = event.getX();
                tmpY = event.getY() - contentViewTop;
                break;
            case MotionEvent.ACTION_UP:
                movingMode = false;
                break;
        }
    }

    /**
     * Checking if we try to draw a rectangle with a too small size.
     *
     * @param left   The left side of the rectangle that we try to draw
     * @param top    The top side of the rectangle that we try to draw
     * @param right  The right side of the rectangle that we try to draw
     * @param bottom The bottom side of the rectangle that we try to draw
     * @return true if the size of the rectangle that we try to draw is acceptable, false if else
     */
    private boolean isSizeDrawable(float left, float top, float right, float bottom) {
        if (Math.abs(left - right) > SIZE_MAX_X_RECTANGLE && Math.abs(top - bottom) > SIZE_MAX_Y_RECTANGLE) {
            return true;
        }
        return false;
    }

    /**
     * Checking if we try to draw a rectangle upon an other rectangle
     *
     * @param left   The left side of the rectangle that we try to draw
     * @param top    The top side of the rectangle that we try to draw
     * @param right  The right side of the rectangle that we try to draw
     * @param bottom The bottom side of the rectangle that we try to draw
     * @return True if the rectangle is not upon an other one, false if else
     */
    private boolean isOutOfRectangle(float left, float top, float right, float bottom) {
        for (int i = 0; i < listRectangle.size(); i++) {
            if (listRectangle.get(i).on(left, top, right, bottom)) {
                return false;
            }
        }
        return true;
    }
	
	public void writeXML(int posX,int posY) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String filepath = Environment.getExternalStorageDirectory().toString()+"/sdqi.xml";
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(filepath);

        Node company = doc.getFirstChild();

        Node staff = doc.getElementsByTagName("rectangles").item(0);

        // append a new node to staff
        Element rectangle = doc.createElement("rectangle");
        staff.appendChild(rectangle);

        Element id = doc.createElement("id");
        id.appendChild(doc.createTextNode(String.valueOf(idFrame)));
        rectangle.appendChild(id);

        Element label = doc.createElement("label");
        label.appendChild(doc.createTextNode("MyLabel"+String.valueOf(idFrame)));
        rectangle.appendChild(label);

        Element x = doc.createElement("x");
        x.appendChild(doc.createTextNode(String.valueOf(posX)));
        rectangle.appendChild(x);

        Element y = doc.createElement("y");
        y.appendChild(doc.createTextNode(String.valueOf(posY)));
        rectangle.appendChild(y);

        Element width = doc.createElement("width");
        width.appendChild(doc.createTextNode(String.valueOf(idFrame)));
        rectangle.appendChild(width);

        Element height = doc.createElement("height");
        width.appendChild(doc.createTextNode(String.valueOf(idFrame)));
        rectangle.appendChild(height);

        Element color = doc.createElement("color");
        color.appendChild(doc.createTextNode(String.valueOf(idFrame)));
        rectangle.appendChild(color);

        // loop the staff child node
        NodeList list = staff.getChildNodes();

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filepath));
        transformer.transform(source, result);
    }

	public void readXML(String url) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        URL input = new URL(url);
        xpp.setInput(input.openStream(),null);
        int eventType = xpp.getEventType();

        String currentTag = null;
        int posX=0;
        int posY=0;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                currentTag = xpp.getName();
            } else if (eventType == XmlPullParser.TEXT) {
                if ("x".equals(currentTag)) {
                    posX = Integer.valueOf(xpp.getText());
                }
                if ("y".equals(currentTag)) {
                    posY = Integer.valueOf(xpp.getText());
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                if ("rectangle".equals(xpp.getName())) {
                    drawRectangle(posX,posY);
                }
            }
            eventType = xpp.next();
        }
    }
	
    /**
     * Drawing a simple rectangle if this one follow some principle
     */
    private void drawRectangle(int posX,int posY) {
        Rectangle tmp = new Rectangle(this, posX+0, posY+0, posX+SIZE_MAX_X_RECTANGLE, posY+SIZE_MAX_Y_RECTANGLE);
        listRectangle.add(tmp);
        frame.addView(tmp, idFrame);
        idFrame++;
    }

    /**
     * Checking if the corner of the rectanle that we want draw is not inside an other one
     *
     * @param x The X position of the finger
     * @param y The Y position of the finger
     * @return True if this position is inside a rectangle
     */
    private boolean inside(float x, float y) {
        for (int i = 0; i < listRectangle.size(); i++) {
            if (listRectangle.get(i).inside(x, y)) {
                return true;
            }
        }
        return false;
    }

    public void createRectangle(View view)  throws ParserConfigurationException, TransformerException, SAXException, IOException {
        //writeXML(0,0); TODO Create a SD Card
        Rectangle tmp = new Rectangle(this, 0, 0, SIZE_MAX_X_RECTANGLE, SIZE_MAX_Y_RECTANGLE);
        listRectangle.add(tmp);
        frame.addView(tmp, idFrame);
        idFrame++;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void paintClicked(View view){
        Log.d("test", view.getTag().toString());
        currentColor = view.getTag().toString();
        changeColor = true;
    }
}
