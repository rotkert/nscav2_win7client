/**
 * @file  ToDirWriter.cpp
 * @brief Implementation of the Class INZ_project::Base::ToDirWriter
 * @date  28-09-2015 21:21:11
 * @author Mikolaj Kaminski
 */

#include "ToDirWriter.h"
#include "LogEntry.h"
#include <boost/bind.hpp>
#include <QObject>
#include <QMetaObject>
#include <QFile>
#include <QIODevice>
#include <QTextStream>
#include <iostream>
#include "ReadPortion.h"
#include "DataFile.h"

namespace INZ_project {
namespace Base {

ToDirWriter::ToDirWriter()
        : m_thread(boost::bind(&ToDirWriter::run, this))
{

}

ToDirWriter::~ToDirWriter()
{

}

void ToDirWriter::close()
{
    m_thread.endThread();
}

void ToDirWriter::consumeDataPortion(const ReadPortion *portion,
        QObject* toConfirm, QString confirmMethod)
{
    foreach(QString log, portion->getLogs()) {
        QString filename = "/home/inz/Desktop/tmp.html";
		QFile file(filename);
		
		if(file.open(QIODevice::ReadWrite));
		{
			QTextStream stream(&file);
			stream << log << endl;
		}
    }

    QMetaObject::invokeMethod(toConfirm,
            confirmMethod.toStdString().c_str(), Qt::QueuedConnection,
                        Q_ARG(const ReadPortion*, portion));
}

int ToDirWriter::initImpl(const QString& additionalData,
        const QString& providerId, const QString& providerType)
{
    m_thread.startThread();
    this->moveToThread(&m_thread);
    return 0;
}

int ToDirWriter::run()
{
    LOG_ENTRY(MyLogger::INFO, "ToDirWriter started");
    return m_thread.exec();
}

} //namespace Base
} //namespace INZ_project
