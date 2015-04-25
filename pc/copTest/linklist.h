#ifndef LINKLIST
#define LINKLIST
#include <QtGui>
#include <QtCore>
#include <iostream>
#include <cstring>

typedef struct node
{
    qreal id;
    std::string label;// = "MyLabel";
    qreal x;
    qreal y;
    qreal width;
    qreal height;
    std::string color;// = "A4A4A4";
    struct node *next;
}LNK_LST;

#endif // LINKLIST
