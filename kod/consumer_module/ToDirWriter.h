/**
 * @file  ToDirWriter.h
 * @brief  Definition of the Class INZ_project::Base::ToDirWriter
 * @date  28-09-2015 22:38:35
 * @author Mikolaj Kaminski
 */

#include <QThread>
#include "DataConsumerRegister.h"
#include "AdditionalThread.h"

namespace INZ_project {
namespace Base {

DATA_CONSUMER(ToDirWriter, ToDirWriter)
{
public:
    ToDirWriter();

    virtual ~ToDirWriter();

    virtual void close();

    void consumeDataPortion(const ReadPortion *portion, QObject* toConfirm,
            QString confirmMethod);

protected:
    virtual int initImpl(const QString& additionalData,
            const QString& providerId, const QString& providerType);

private:

    int run();

    TcpStandardModule::AdditionalThread m_thread;
};

} //namespace Base
} //namespace INZ_project