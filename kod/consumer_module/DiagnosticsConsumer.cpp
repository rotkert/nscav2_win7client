/**
 * @file  DiagnosticsConsumer.cpp
 * @brief Implementation of the Class INZ_project::Base::DiagnosticsConsumer
 * @date  14-11-2015 16:21:11
 * @author Mikołaj Kamiński
 */

#include "DiagnosticsConsumer.h"
#include "LogEntry.h"
#include <boost/bind.hpp>
#include <QObject>
#include <QMetaObject>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include "ReadPortion.h"
#include "MyNscaMain.h"

#include <QFile>
#include <QIODevice>
#include <QTextStream>
#include <iostream>
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

DiagnosticsConsumer::DiagnosticsConsumer()
        : m_thread(boost::bind(&DiagnosticsConsumer::run, this)),
                m_pipeFile("/var/run/icinga2/cmd/icinga2.cmd")
{

}

DiagnosticsConsumer::~DiagnosticsConsumer()
{

}

void DiagnosticsConsumer::close()
{
    m_thread.endThread();
    ::close(m_pipeDesc);
}

void DiagnosticsConsumer::consumeDataPortion(const ReadPortion *portion,
        QObject* toConfirm, QString confirmMethod)
{
    LOG_ENTRY(MyLogger::INFO,
            " Writing portion of "<<portion->getLogs().size()<<" logs.");
			
    foreach(QString log, portion->getLogs()) {
		std::vector<std::string> strs;
        std::string tmpString = log.toUtf8().constData();
		std::string delimiter = "_DiagSep_";
		
		size_t pos = 0;
		while ((pos = tmpString.find(delimiter)) != std::string::npos) {
			strs.push_back(tmpString.substr(0, pos));
			tmpString.erase(0, pos + delimiter.length());
		}
		
		LOG_ENTRY(MyLogger::INFO, " Size: "<<strs.size());
		
		std::string logEnd = tmpString;
		std::string reportName = strs[2];
		std::string reportText = strs[1];
		std::string logBegin = strs[0];
		
		LOG_ENTRY(MyLogger::INFO, " Log: " << log);
		LOG_ENTRY(MyLogger::INFO, " Log beginning: " << QString::fromUtf8(logBegin.c_str()));
		LOG_ENTRY(MyLogger::INFO, " Report name: " << QString::fromUtf8(reportName.c_str()));
		LOG_ENTRY(MyLogger::INFO, " Log ending: " << QString::fromUtf8(logEnd.c_str()));
		
		std::stringstream ss;
		ss << logBegin << logEnd;
		std::string pluginOutput = ss.str();
		
		writeToDir(reportName, reportText);
		writeToIcinga(pluginOutput);
    }

    QMetaObject::invokeMethod(toConfirm, confirmMethod.toStdString().c_str(),
            Qt::QueuedConnection, Q_ARG(const ReadPortion*, portion));
}

void DiagnosticsConsumer::writeToDir(std::string reportName, std::string reportText) {
	std::stringstream ss;
	ss << "/var/www/html/reports/"<< reportName <<".html";
	std::string filepathStr = ss.str();
	filepathStr.erase( std::remove_if( filepathStr.begin(), filepathStr.end(), ::isspace ), filepathStr.end() );
	
	QString filepath = QString::fromUtf8(filepathStr.c_str());
	QFile file(filepath);
	QString reportTextQStr = QString::fromUtf8(reportText.c_str());
	
	if(file.open(QIODevice::ReadWrite)) {
		QTextStream stream(&file);
		stream << reportTextQStr << endl;
		file.close();
	}
	else {
		LOG_ENTRY(MyLogger::FATAL, "Unable to open " << filepath <<".html");
        MyNscaMain::shutDown();
        return;
	}
}

void DiagnosticsConsumer::writeToIcinga(std::string pluginOutput) {
	QString log = QString::fromUtf8(pluginOutput.c_str());

	if (log[log.size()] == '\n') {
            log = log.left(log.size() - 1);
        }
        std::string logStd = log.toStdString();
        const char *buf = logStd.c_str();

        for (int toWrite = log.size(); toWrite > 0;) {
            int written = write(m_pipeDesc, buf + log.size() - toWrite,
                    toWrite);
            if (written < 0) {
                LOG_ENTRY(MyLogger::FATAL, "Unable to write to "<<m_pipeFile);
                MyNscaMain::shutDown();
                return;
            } else {
                toWrite -= written;
            }
        }
}

int DiagnosticsConsumer::initImpl(const QString& additionalData,
        const QString& consumerId, const QString& consumerType)
{

    if (parseOptions(additionalData) != 0) {
        LOG_ENTRY(MyLogger::FATAL, "Unable to parse given options.");
        return -1;
    }

    m_thread.startThread();
    this->moveToThread(&m_thread);
    return 0;
}

int DiagnosticsConsumer::parseOptions(const QString& options)
{
    static const QString pipeString = "PIPE:";
    int pos, nlPos;

    if ((pos = options.indexOf(pipeString)) != -1) {
        nlPos = options.indexOf("\n", pos);
        QString path = options.mid(pos + pipeString.size(),
                nlPos - pos - pipeString.size());
        m_pipeFile = path.trimmed();
        LOG_ENTRY(MyLogger::INFO, "Command file path provided: "<<m_pipeFile);
    } else {
        LOG_ENTRY(MyLogger::INFO,
                "Command file path has not been provided. Default: "<<m_pipeFile<<" used.");
    }

    return 0;
}

int DiagnosticsConsumer::run()
{
    m_pipeDesc = open(m_pipeFile.toStdString().c_str(), O_WRONLY);
    if (m_pipeDesc < 0) {
        LOG_ENTRY(MyLogger::FATAL, "Unable to open file "<<m_pipeFile);
        MyNscaMain::shutDown();
    }

    LOG_ENTRY(MyLogger::INFO, "Diagnostics Consumer Started");
    return m_thread.exec();
}

} //namespace Base
} //namespace INZ_project
