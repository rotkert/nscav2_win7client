/**
 * @file  DiagnosticsConsumer.h
 * @brief  Definition of the Class INZ_project::Base::DiagnosticsConsumer
 * @date  14-11-2015 16:38:35
 * @author Mikołaj Kamiński
 */

#include <string>
#include <QThread>
#include "DataConsumerRegister.h"
#include "AdditionalThread.h"

namespace INZ_project {
namespace Base {

DATA_CONSUMER(DiagnosticsConsumer, DiagnosticsConsumer)
{
public:
    DiagnosticsConsumer();

    virtual ~DiagnosticsConsumer();

    virtual void close();

    void consumeDataPortion(const ReadPortion *portion, QObject* toConfirm,
            QString confirmMethod);

protected:
    virtual int initImpl(const QString& additionalData,
            const QString& providerId, const QString& providerType);

private:

    int parseOptions(const QString& options);
    int run();
	void writeToIcinga(std::string pluginOutput);
	void writeToDir(std::string reportName, std::string reportText);

    TcpStandardModule::AdditionalThread m_thread;

    QString m_pipeFile;
    int m_pipeDesc;
};

} //namespace Base
} //namespace INZ_project
