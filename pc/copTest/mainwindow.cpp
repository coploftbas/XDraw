#include "mainwindow.h"
#include "myrectangle.h"
#include "ui_mainwindow.h"
#include <string>

using namespace std;

qreal posX = 0;
qreal posY = 0;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    scene = new QGraphicsScene(this);
    ui->graphicsView->setScene(scene);

    QBrush whiteBrush(Qt::white);
    QPen whitePen(Qt::white);

    qreal tmp_x = 0;
    qreal tmp_y = 0;
    qreal tmp_h = 0.001;
    qreal tmp_w = 0.0001;
    //blackPen.setWidth(6);

    rectangle = scene->addRect(tmp_x,tmp_y,tmp_w,tmp_h,whitePen, whiteBrush);
    rectangle->setFlag(QGraphicsItem::ItemIsMovable);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_pushButton_clicked()
{
    ui->label->setText(QString::number(posX));

    QBrush blueBrush(Qt::gray);
    QPen blackPen(Qt::black);
    blackPen.setWidth(6);
    rectangle = scene->addRect(posX, posY,myWidth,myHeight,blackPen, blueBrush);
    rectangle->setFlag(QGraphicsItem::ItemIsMovable);
    posX += 50;
    posY += 50;
}

void MainWindow::on_pushButton_2_clicked()
{
    QColor color = QColorDialog::getColor(Qt::green, this);

    rectangle->setBrush(color);
       /*if (color.isValid())
       {
           ui->label->setText(color.name());
           ui->label->setPalette(QPalette(color));
           ui->label->setAutoFillBackground(true);
       }*/
}



void MainWindow::mousePressEvent(QMouseEvent *e)
{
    QBrush blueBrush(Qt::gray);
    QPen blackPen(Qt::black);
    //QPointF pt = QPointF::mapToScene(e->pos());
    rectangle = scene->addRect( e->pos().x()-10, e->pos().y()-18, myWidth, myHeight, blackPen, blueBrush );
    //rectangle = scene->addRect( posX, posY, 80, 50, blackPen, blueBrush );
    rectangle->setFlag(QGraphicsItem::ItemIsMovable);
    ui->label->setText("Click => x:"+QString::number(e->pos().x()) + "  y:"+QString::number(e->pos().y()));
    //ui->label->setText("Click => x:"+ (qreal)e->pos().x() + "  y:"+ (qreal)e->pos().y());
}


void MainWindow::drawLines(QPainter *p)
{
    if (!startPos.isNull() && !endPos.isNull())
    {
        p->drawLine(startPos, endPos);
    }

    p->drawLines(lines);
}

void MainWindow::paintEvent(QPaintEvent *event)
{
    QPainter p(this);
    QPen pen;
    pen.setColor(Qt::red);
    pen.setWidth(4);
    p.setPen(pen);

    drawLines(&p);
}

void MainWindow::on_xml_reader_clicked()
{
    QFile xmlFile("/Users/coploftbas/Documents/XDraw/sdqi.xml");
       xmlFile.open(QIODevice::ReadOnly);
       xml.setDevice(&xmlFile);

       //ui->label->setText(xml.name().toString());

       if (xml.readNextStartElement() && xml.name() == "project")
          processProject();
          //ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString());

       // readNextStartElement() leaves the stream in
       // an invalid state at the end. A single readNext()
       // will advance us to EndDocument.
       if (xml.tokenType() == QXmlStreamReader::Invalid)
           xml.readNext();

       if (xml.hasError()) {
           xml.raiseError();
       }
}

void MainWindow::processProject(){
    if (!xml.isStartElement() || xml.name() != "project")
            return;
        while (xml.readNextStartElement()) {
            if (xml.name() == "rectangles"){
                //processRate();
                ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString());
                processRectangles();
            }
            else
                xml.skipCurrentElement();
        }
}

void MainWindow::processRectangles(){
    if (!xml.isStartElement() || xml.name() != "rectangles")
            return;
        while (xml.readNextStartElement()) {
            if (xml.name() == "rectangle"){
                //processRate();
                ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString());
                processRectangle();
            }
            else
                xml.skipCurrentElement();
        }
}

void MainWindow::processRectangle(){

    if (!xml.isStartElement() || xml.name() != "rectangle")
            return;

    while (xml.readNextStartElement()) {
        if (xml.name() == "id")
            //from = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+readNextText());
        else if (xml.name() == "label")
            //to = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+readNextText());
        else if (xml.name() == "x")
            //conversion = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+readNextText());
        else if (xml.name() == "y")
            //conversion = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+readNextText());
        else if (xml.name() == "width")
            //conversion = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+readNextText());
        else if (xml.name() == "height")
            //conversion = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+readNextText());
        else if (xml.name() == "color")
            //conversion = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+readNextText());




    //#ifndef USE_READ_ELEMENT_TEXT
            xml.skipCurrentElement();
    //#endif
        }


}

QString MainWindow::readNextText(){
    #ifndef USE_READ_ELEMENT_TEXT
        xml.readNext();
        return xml.text().toString();
    #else
        return xml.readElementText();
    #endif
}
