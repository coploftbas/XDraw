#include "mainwindow.h"
#include "linklist.h"
#include <stdlib.h>
#include <QApplication>
#include <QProcess>
#include <libssh/libssh.h>

int main(int argc, char *argv[])
{
/*
    ssh_session my_ssh_session;
      int rc;
     // char *password;

    // Open session and set options
      my_ssh_session = ssh_new();
      if (my_ssh_session == NULL)
        exit(-1);
      ssh_options_set(my_ssh_session, SSH_OPTIONS_HOST, "ฝฝฝฝฝฝ");
      // Connect to server
      rc = ssh_connect(my_ssh_session);
      if (rc != SSH_OK)
      {
        fprintf(stderr, "Error connecting to localhost: %s\n",
                ssh_get_error(my_ssh_session));
        ssh_free(my_ssh_session);
        exit(-1);
      }

      // Verify the server's identity
      // For the source code of verify_knowhost(), check previous example
      /*
      if (verify_knownhost(my_ssh_session) < 0)
      {
        ssh_disconnect(my_ssh_session);
        ssh_free(my_ssh_session);
        exit(-1);
      }
      */


      // Authenticate ourselves
      //password = getpass("Password: ");
/*      rc = ssh_userauth_password(my_ssh_session, NULL, "ฝฝฝฝฝ");
      if (rc != SSH_AUTH_SUCCESS)
      {
        fprintf(stderr, "Error authenticating with password: %s\n",
                ssh_get_error(my_ssh_session));
        ssh_disconnect(my_ssh_session);
        ssh_free(my_ssh_session);
        exit(-1);
      }

    ssh_disconnect(my_ssh_session);
    ssh_free(my_ssh_session);
    /*
    QProcess *proc = new QProcess();

    proc->start("ssh st116391@bazooka.cs.ait.ac.th");
    proc->waitForFinished();

    QString result = proc->readAllStandardOutput();
    //ui->textBrowser->setText(result); //writing the result to my text browser
    */


    QApplication a(argc, argv);
    MainWindow w;
    w.show();


    return a.exec();
}
