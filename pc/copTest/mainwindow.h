#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QtGui>
#include <QtCore>
#include <QGraphicsScene>
#include <QGraphicsView>
#include <QGraphicsRectItem>
#include <QColorDialog>
#include <QMouseEvent>
#include <QFile>
#include <QXmlStreamReader>
#include <QFileDialog>
#include <QObject>

/*
#include "sshremoteprocess.h"
#include "sshconnection.h"
*/

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private slots:
    void on_pushButton_clicked();
    void on_pushButton_2_clicked();
    void mousePressEvent(QMouseEvent *e);
    void on_xml_reader_clicked();
    void processProject();
    void processRectangles();
    void processRectangle();
    void drawRectangle(qreal id, qreal x, qreal y, qreal w, qreal h, QString c);
    void on_writeButton_clicked();
/*
    void connected();
    void onConnectionError(QSsh::SshError);
    void onChannelStarted();
    void readyReadStandardOutput();
*/

private:
    Ui::MainWindow *ui;
    QGraphicsScene *scene;
    QGraphicsRectItem *rectangle;
    QPoint startPos;
    QPoint endPos;
    bool inDrawing;
    QVector<QLine> lines;
    qreal posX;
    qreal posY;
    qreal myHeight=50;
    qreal myWidth=80;
    QXmlStreamReader xml;

    /*
    QSsh::SshRemoteProcess::Ptr remoteProc;
    QSsh::SshConnection *connection;
    */

};

#endif // MAINWINDOW_H

/*
void MainWindow::connected()
{
    qDebug() << "MainWindow: CONNECTED!";
    const QByteArray comman("ls");
    remoteProc = connection->createRemoteProcess(comman);

    if(remoteProc){
        connect(remoteProc.data(), SIGNAL(started()), SLOT(onChannelStarted()));
        connect(remoteProc.data(), SIGNAL(readyReadStandardOutput()), SLOT(readyReadStandardOutput()));

        remoteProc->start();
    }
}

void MainWindow::onConnectionError(QSsh::SshError)
{
    qDebug() << "Com: Connection error" << connection->errorString();
}

void MainWindow::onChannelStarted(){
    qDebug() << "COM: Channel Started";
}

void MainWindow::readyReadStandardOutput()
{
    qDebug() << "OUTPUT:" << remoteProc->readAll();

}
*/
