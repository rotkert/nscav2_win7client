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

#include <boost/algorithm/string/classification.hpp>
#include <boost/algorithm/string.hpp>
#include <sstream>
#include <string>
#include <vector>
#include <algorithm> 
#include <functional> 
#include <cctype>
#include <locale>

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
		std::string tmpLog = log.toUtf8().constData(); 
		std::vector<std::string> strs1;
		boost::split(strs1, tmpLog, boost::is_any_of("|"));
		std::string lastPart = strs1.back();
		
		std::vector<std::string> strs2;
		boost::split(strs2, lastPart, boost::is_any_of("="));
		std::string filename = strs2.back();
		
		std::stringstream ss;
		ss << "/var/www/html/"<< filename <<".html";
		std::string filepathStr = ss.str();
		filepathStr.erase( std::remove_if( filepathStr.begin(), filepathStr.end(), ::isspace ), filepathStr.end() );
		
        QString filepath = QString::fromUtf8(filepathStr.c_str());
		
		QFile file(filepath);
		
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
