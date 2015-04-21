#include "mainwindow.h"
#include "myrectangle.h"
#include "ui_mainwindow.h"

qreal posX;
qreal posY;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    scene = new QGraphicsScene(this);
    ui->graphicsView->setScene(scene);

    //QBrush blueBrush(Qt::gray);
    //QPen blackPen(Qt::black);

    //blackPen.setWidth(6);

    //rectangle = scene->addRect(10,10,50,50,blackPen, blueBrush);
    //rectangle->setFlag(QGraphicsItem::ItemIsMovable);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_pushButton_clicked()
{

    QBrush blueBrush(Qt::gray);
    QPen blackPen(Qt::black);
    blackPen.setWidth(6);
    rectangle = scene->addRect(posX, posY,80,50,blackPen, blueBrush);
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

    rectangle = scene->addRect( e->pos().x(), e->pos().y(), 80, 50, blackPen, blueBrush );
    rectangle->setFlag(QGraphicsItem::ItemIsMovable);

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
