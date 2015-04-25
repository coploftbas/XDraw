#include "mainwindow.h"
#include "myrectangle.h"
#include "ui_mainwindow.h"
#include <string>

using namespace std;

qreal posX = 0;
qreal posY = 0;
QString default_color_brush = "#A4A4A4";
QString default_color_pen = "#000000";

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
    //rectangle->setFlag(QGraphicsItem::ItemIsMovable);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_pushButton_clicked()
{
    ui->label->setText("x:"+QString::number(posX)+" y:"+QString::number(posY));
    QColor color;
    color.setNamedColor(default_color_brush);
    QBrush blueBrush(color);
    color.setNamedColor(default_color_pen);
    QPen blackPen(color);
    blackPen.setWidth(3);
    rectangle = scene->addRect(posX, posY,myWidth,myHeight,blackPen, blueBrush);
    rectangle->setFlag(QGraphicsItem::ItemIsMovable);
    posX += 50;
    posY += 50;
}

void MainWindow::on_pushButton_2_clicked()
{
    QColor color = QColorDialog::getColor(Qt::green, this);
    QString str;
    str.append( QString::number( color.red(), 16 ).toUpper() )
       .append( QString::number( color.green(), 16 ).toUpper() )
       .append( QString::number( color.blue(), 16 ).toUpper() );
    ui->label->setText("rgbcolor: #"+str);
    rectangle->setBrush(color);
}

void MainWindow::mousePressEvent(QMouseEvent *e)
{
    QColor color;
    color.setNamedColor(default_color_brush);
    QBrush blueBrush(color);
    color.setNamedColor(default_color_pen);
    QPen blackPen(color);
    blackPen.setWidth(3);
    //QPointF pt = QPointF::mapToScene(e->pos());
    rectangle = scene->addRect( e->pos().x()-10, e->pos().y()-18, myWidth, myHeight, blackPen, blueBrush );
    //rectangle = scene->addRect( posX, posY, 80, 50, blackPen, blueBrush );
    rectangle->setFlag(QGraphicsItem::ItemIsMovable);
    ui->label->setText("Click => x:"+QString::number(e->pos().x()) + "  y:"+QString::number(e->pos().y()));
    //ui->label->setText("Click => x:"+ (qreal)e->pos().x() + "  y:"+ (qreal)e->pos().y());
}

void MainWindow::on_xml_reader_clicked()
{
    QString niwnew_read = "/Users/new482/Documents/sdqi_workspace/XDraw/sdqi.xml";
    QString cop_read = "/Users/coploftbas/Documents/XDraw/sdqi.xml";
    QFile xmlFile(cop_read);
       xmlFile.open(QIODevice::ReadOnly);
       xml.setDevice(&xmlFile);

       if (xml.readNextStartElement() && xml.name() == "project")
          processProject();

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

    qreal my_x, my_y = -1;

    while (xml.readNextStartElement()) {
        if (xml.name() == "id"){
            //from = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+xml.readElementText());
        }else if (xml.name() == "label"){
            //to = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+xml.readElementText());
        }else if (xml.name() == "x"){
            //conversion = readNextText();
            my_x = xml.readElementText().toDouble();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+my_x);
        }else if (xml.name() == "y"){
            //conversion = readNextText();
            my_y = xml.readElementText().toDouble();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+my_y);
        }else if (xml.name() == "width"){
            //conversion = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+xml.readElementText());
        }else if (xml.name() == "height"){
            //conversion = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+xml.readElementText());
        }else if (xml.name() == "color"){
            //conversion = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+xml.readElementText());
        }

     }

    if ( !(my_x==-1 || my_y == -1) )
        drawRectangle(my_x,my_y);


}

void MainWindow::on_writeButton_clicked()
{
    //QString filename = QFileDialog::getSaveFileName(this,tr("Save Xml"), ".",tr("Xml files (*.xml)"));

    QString niwnew_write = "/Users/new482/Desktop/sdqi.xml";
    QString cop_write = "/Users/coploftbas/Desktop/sdqi.xml";

    QFile file(cop_write);
    file.open(QIODevice::WriteOnly);
    QXmlStreamWriter xmlWriter(&file);
    xmlWriter.setAutoFormatting(true);
    xmlWriter.writeStartDocument();

    xmlWriter.writeStartElement("project");
    xmlWriter.writeStartElement("rectangles");
    xmlWriter.writeStartElement("rectangle");
        xmlWriter.writeTextElement("id","1");
        xmlWriter.writeTextElement("label","Mylabel");
        xmlWriter.writeTextElement("x","10");
        xmlWriter.writeTextElement("y","10");
        xmlWriter.writeTextElement("width","80");
        xmlWriter.writeTextElement("height","50");
        xmlWriter.writeTextElement("color","FFFFFF");
    xmlWriter.writeEndElement();
    xmlWriter.writeEndElement();
    xmlWriter.writeEndElement();

    file.close();
}

void MainWindow::drawRectangle(qreal x, qreal y)
{
    QBrush blueBrush(Qt::gray);
    QPen blackPen(Qt::black);
    blackPen.setWidth(3);
    rectangle = scene->addRect(x, y,myWidth,myHeight,blackPen, blueBrush);
    rectangle->setFlag(QGraphicsItem::ItemIsMovable);
}
