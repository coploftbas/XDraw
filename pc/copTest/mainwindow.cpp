#include "mainwindow.h"
#include "linklist.h"
#include "myrectangle.h"
#include "ui_mainwindow.h"
#include <string>

using namespace std;

LNK_LST *get_node()
{
    LNK_LST *p;
    p=(LNK_LST *)malloc(sizeof(LNK_LST));

    if(p) {
        p->id = 0;
        //p->label = "MyLabel";
        p->x = 0;
        p->y = 0;
        p->width = 80;
        p->height = 50;
        //p->color = "A4A4A4";
        p->next=NULL;
    }
    return p;
}

LNK_LST *head,*current;
qreal rec_count = 0;
qreal posX = 3;
qreal posY = 3;
QString default_color_brush = "A4A4A4";
QString default_color_pen = "000000";

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    scene = new QGraphicsScene(this);
    ui->graphicsView->setScene(scene);

    //This rectangle is to remove bug that cause from mouse clicking
    QBrush whiteBrush(Qt::white);
    QPen whitePen(Qt::white);
    qreal tmp_x = 0;
    qreal tmp_y = 0;
    qreal tmp_h = 0.001;
    qreal tmp_w = 0.0001;
    rectangle = scene->addRect(tmp_x,tmp_y,tmp_w,tmp_h,whitePen, whiteBrush);
    //end fixing bug

    posX=0;
    posY=0;
    rec_count = 0;

    //LNK_LST
    head = get_node();
    current = head;

}

MainWindow::~MainWindow()
{
    //free(head);
    //free(current);
    delete ui;
}

void MainWindow::on_pushButton_clicked()
{
    rec_count++;
    current->id = rec_count;
    //current->label = "MyLabel";
    current->x = posX;
    current->y = posY;
    current->width = 80;
    current->height = 50;
    //current->color = default_color_brush.toStdString();

    ui->label->setText("x:"+QString::number(posX)+" y:"+QString::number(posY)+" ["+QString::number(current->id)+"]");
    QColor color;
    color.setNamedColor("#"+default_color_brush);
    QBrush blueBrush(color);
    color.setNamedColor("#"+default_color_pen);
    QPen blackPen(color);
    blackPen.setWidth(3);
    rectangle = scene->addRect(posX, posY,myWidth,myHeight,blackPen, blueBrush);
    rectangle->setFlag(QGraphicsItem::ItemIsMovable);

    current->next = get_node();
    current = current->next;
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
    color.setNamedColor("#"+default_color_brush);
    QBrush blueBrush(color);
    color.setNamedColor("#"+default_color_pen);
    QPen blackPen(color);
    blackPen.setWidth(3);
    rectangle = scene->addRect( e->pos().x()-10, e->pos().y()-18, myWidth, myHeight, blackPen, blueBrush );
    rectangle->setFlag(QGraphicsItem::ItemIsMovable);
    ui->label->setText("Click => x:"+QString::number(e->pos().x()) + "  y:"+QString::number(e->pos().y()));

    rec_count++;
    current->id = rec_count;
    //current->label = "MyLabel";
    current->x = e->pos().x();
    current->y = e->pos().y();
    current->width = 80;
    current->height = 50;
    //current->color = default_color_brush.toStdString();

    current->next = get_node();
    current = current->next;

}

void MainWindow::on_xml_reader_clicked()
{
    QString niwnew_read = "/Users/new482/Documents/sdqi_workspace/XDraw/sdqi.xml";
    QString cop_read = "/Users/coploftbas/Documents/XDraw/sdqi.xml";
    QFile xmlFile(niwnew_read);
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

    qreal my_id, my_x, my_y, my_width, my_height = -1;
    QString my_color = NULL;

    while (xml.readNextStartElement())
    {
        if (xml.name() == "id")
        {
            my_id = xml.readElementText().toDouble();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+my_id);

        }else if (xml.name() == "label")
        {
            //to = readNextText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+xml.readElementText());

        }else if (xml.name() == "x")
        {
            my_x = xml.readElementText().toDouble();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+my_x);

        }else if (xml.name() == "y")
        {
            my_y = xml.readElementText().toDouble();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+my_y);

        }else if (xml.name() == "width")
        {
            my_width = xml.readElementText().toDouble();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+my_width);

        }else if (xml.name() == "height")
        {
            my_height = xml.readElementText().toDouble();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+my_height);

        }else if (xml.name() == "color")
         {
            my_color = xml.readElementText();
            ui->label->setText("get inside the if case ==>  tagname:"+xml.name().toString()+" value:"+my_color);
        }
     }

    if ( !(my_id==-1 || my_x==-1 || my_y == -1 || my_width == -1 || my_height == -1 || my_color==NULL) ){
        drawRectangle(my_id,my_x,my_y,my_width,my_height,my_color);
    }
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

    current = head;

    xmlWriter.writeStartElement("project");
    xmlWriter.writeStartElement("rectangles");
    for(int i=0;i<rec_count;i++){
        xmlWriter.writeStartElement("rectangle");
            xmlWriter.writeTextElement("id",QString::number(current->id));
            xmlWriter.writeTextElement("label","Mylabel"+QString::number(current->id));
            xmlWriter.writeTextElement("x",QString::number(current->x));
            xmlWriter.writeTextElement("y",QString::number(current->y));
            xmlWriter.writeTextElement("width",QString::number(current->width));
            xmlWriter.writeTextElement("height",QString::number(current->height));
            xmlWriter.writeTextElement("color","A4A4A4");
        xmlWriter.writeEndElement();
        current=current->next;
    }
    xmlWriter.writeEndElement();
    xmlWriter.writeEndElement();

    file.close();
}

void MainWindow::drawRectangle(qreal id, qreal x, qreal y, qreal w, qreal h, QString c)
{
    QColor color;
    color.setNamedColor("#"+c);
    QBrush blueBrush(color);
    QPen blackPen(Qt::black);
    blackPen.setWidth(3);

    if(id<=rec_count){
        //UPDATE OLD RECT

    }else{
        //DRAW NEW RECT
        rectangle = scene->addRect(x,y,w,h,blackPen,blueBrush);
        rectangle->setFlag(QGraphicsItem::ItemIsMovable);

        rec_count++;
        current->id = rec_count;
        //current->label = "MyLabel";
        current->x = x;
        current->y = y;
        current->width = 80;
        current->height = 50;
        //current->color = default_color_brush.toStdString();

        current->next = get_node();
        current = current->next;
    }


}
