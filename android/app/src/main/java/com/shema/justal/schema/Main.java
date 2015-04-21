package com.shema.justal.schema;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
	private File gpxfile;
	
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

        new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... params) {
                try {

                   // Log.d("Info return", ReadFile("st116389", "password", "bazooka.cs.ait.ac.th", 22));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);

        new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... params) {
                try {


                   // Log.d("Info return", WriteFile("st116389", "password", "bazooka.cs.ait.ac.th", 22));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);

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
	
    public void saveData(View view) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        writeXML();
    }

    public void writeXML() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Log.d("Change color",getFilesDir().getAbsolutePath());
        File root = new File(getFilesDir().getAbsolutePath(), "sdqi.xml");
        if (!root.exists()) {
            root.mkdirs();
        }
        gpxfile = new File(root, "sdqi.xml");
        FileWriter writer = new FileWriter(gpxfile);
        writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<project>\n" +
                "\t<rectangles>\n" +
                "\t</rectangles>\n" +
                "</project>");
        writer.flush();
        writer.close();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(gpxfile);

        Node company = doc.getFirstChild();

        for(int i=0;i<listRectangle.size();i++) {
            Node staff = doc.getElementsByTagName("rectangles").item(0);

            // append a new node to staff
            Element rectangle = doc.createElement("rectangle");
            staff.appendChild(rectangle);

            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(String.valueOf(i)));
            rectangle.appendChild(id);

            Element label = doc.createElement("label");
            label.appendChild(doc.createTextNode("MyLabel" + String.valueOf(i)));
            rectangle.appendChild(label);

            Element x = doc.createElement("x");
            x.appendChild(doc.createTextNode(String.valueOf(listRectangle.get(i).getPositionX())));
            rectangle.appendChild(x);

            Element y = doc.createElement("y");
            y.appendChild(doc.createTextNode(String.valueOf(listRectangle.get(i).getPositionY())));
            rectangle.appendChild(y);

            Element width = doc.createElement("width");
            width.appendChild(doc.createTextNode(String.valueOf(SIZE_MAX_X_RECTANGLE)));
            rectangle.appendChild(width);

            Element height = doc.createElement("height");
            height.appendChild(doc.createTextNode(String.valueOf(SIZE_MAX_Y_RECTANGLE)));
            rectangle.appendChild(height);

            Element color = doc.createElement("color");
            color.appendChild(doc.createTextNode(listRectangle.get(i).getColor()));
            rectangle.appendChild(color);

            // loop the staff child node
            NodeList list = staff.getChildNodes();
        }

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(gpxfile.getAbsolutePath()));
        transformer.transform(source, result);
    }

    public void readData(View view) throws ParserConfigurationException, TransformerException, SAXException, IOException, XmlPullParserException {
        readXML();
    }

    public void readXML() throws XmlPullParserException, IOException {
        frame.removeAllViews();
        listRectangle = new ArrayList<Rectangle>();
        idFrame=0;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        URL input = gpxfile.toURI().toURL();
        xpp.setInput(input.openStream(),null);
        int eventType = xpp.getEventType();

        String currentTag = null;
        float posX=0;
        float posY=0;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                currentTag = xpp.getName();
            } else if (eventType == XmlPullParser.TEXT) {
                if ("x".equals(currentTag)) {
                    posX = (Float.valueOf(xpp.getText())).floatValue();
                }
                if ("y".equals(currentTag)) {
                    posY = (Float.valueOf(xpp.getText())).floatValue();
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
    private void drawRectangle(float posX,float posY) {
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

    public String WriteFile(String username, String password, String hostname, int port)
            throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();

        // Log.d("Info Path : ", getResources().getString(R.xml.trying));


        //Log.d("AbsolutePath", getFilesDir().getAbsolutePath());


        String path = getFilesDir().getAbsolutePath();

        String localPath = path + "/trying.xml";


        writeFileToServer(session,localPath,"trying_999.xml");

        session.disconnect();

        return "Finish Writing";
    }


    public String ReadFile(String username, String password, String hostname, int port)
            throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();

        // Log.d("Info Path : ", getResources().getString(R.xml.trying));


        //Log.d("AbsolutePath", getFilesDir().getAbsolutePath());


        String path = getFilesDir().getAbsolutePath();

        String localPath = path + "/trying.xml";

        readFileFromServer(session, localPath,"trying.xml" );
        session.disconnect();

        return "Finish reading";
    }


    private void writeFileToServer(Session session,String localFile, String remoteFile) {
        FileInputStream fis = null;
        try {

            // InputStream ins = getResources().openRawResource(R.xml.trying);

            String lfile = localFile; //getResources().getString(R.xml.trying); // localhost file path
            String rfile = remoteFile ;// remote file path


            boolean ptimestamp = false;

            // exec 'scp -t rfile' remotely
            String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + rfile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            if (checkAck(in) != 0) {
                System.exit(0);
            }


            File _lfile = new File(lfile);


            if (ptimestamp) {
                command = "T " + (_lfile.lastModified() / 1000) + " 0";
                // The access time should be sent here,
                // but it is not accessible with JavaAPI ;-<
                command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
                out.write(command.getBytes());
                out.flush();
                if (checkAck(in) != 0) {
                    System.exit(0);
                }
            }

            // send "C0644 filesize filename", where filename should not include '/'
            long filesize = _lfile.length();
            command = "C0644 " + filesize + " ";
            if (lfile.lastIndexOf('/') > 0) {
                command += lfile.substring(lfile.lastIndexOf('/') + 1);
            } else {
                command += lfile;
            }
            command += "\n";
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                System.exit(0);
            }


            // send a content of lifle

            fis = new FileInputStream(new File(lfile)); //getResources().openRawResource(R.xml.trying);

            byte[] buf = new byte[1024];
            while (true) {
                int len = fis.read(buf, 0, buf.length);
                if (len <= 0) break;
                out.write(buf, 0, len); //out.flush();
            }
            fis.close();
            fis = null;
            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            if (checkAck(in) != 0) {
                System.exit(0);
            }
            out.close();

            fis.close();
            channel.disconnect();

            //  session.disconnect();
            Log.d("Info: " ,"Writing file is finished");

            System.exit(0);
        } catch (Exception e) {
            System.out.println(e);
            try {
                if (fis != null) fis.close();
            } catch (Exception ee) {
            }
        }
    }

    private void readFileFromServer(Session session,String localFile,String remoteFile) {

        FileOutputStream fos = null;
        try {

            String rfile = remoteFile; // remote file
            String lfile = localFile;// local file

            // exec 'scp -f rfile' remotely
            String command = "scp -f " + rfile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] buf = new byte[1024];

            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

            while (true) {
                int c = checkAck(in);
                if (c != 'C') {
                    break;
                }

                // read '0644 '
                in.read(buf, 0, 5);

                long filesize = 0L;
                while (true) {
                    if (in.read(buf, 0, 1) < 0) {
                        // error
                        break;
                    }
                    if (buf[0] == ' ') break;
                    filesize = filesize * 10L + (long) (buf[0] - '0');
                }

                String file = null;
                for (int i = 0; ; i++) {
                    in.read(buf, i, 1);
                    if (buf[i] == (byte) 0x0a) {
                        file = new String(buf, 0, i);
                        break;
                    }
                }

                Log.d("Info read file from server", "filesize=" + filesize + ", file=" + file);

                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();



                // String path = getFilesDir().getAbsolutePath();

                // String localPath = path + "/POP.xml";

                // Log.d("PATH 1:" ,localPath) ;
                // Log.d("PATH:" ,path + "/POP.xml") ;

                fos =  new FileOutputStream (new File(lfile));

                //fos = openFileOutput("Test.xml", MODE_PRIVATE);

                int foo;
                while (true) {
                    if (buf.length < filesize) foo = buf.length;
                    else foo = (int) filesize;
                    foo = in.read(buf, 0, foo);
                    if (foo < 0) {
                        // error
                        break;
                    }
                    fos.write(buf, 0, foo);
                    filesize -= foo;
                    if (filesize == 0L) break;
                }
                fos.close();
                fos = null;

                if (checkAck(in) != 0) {
                    System.exit(0);
                }

                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();
            }

            out.close();
            channel.disconnect();
            fos.close();
            //  session.disconnect();

            System.exit(0);
        } catch (Exception e) {
            System.out.println(e);
            try {
                if (fos != null) fos.close();
            } catch (Exception ee) {
            }
        }
    }


    static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if (b == 0) return b;
        if (b == -1) return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            }
            while (c != '\n');
            if (b == 1) { // error
                System.out.print(sb.toString());
            }
            if (b == 2) { // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }
}
