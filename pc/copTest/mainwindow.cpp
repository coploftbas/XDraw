#include "mainwindow.h"
#include "myrectangle.h"
#include "ui_mainwindow.h"
#include <string>

using namespace std;

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
