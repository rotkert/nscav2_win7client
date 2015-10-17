#include "pdh.h"

double getCpuUsage()
{
	PDH_HQUERY cpuQuery;
	PDH_HCOUNTER cpuTotal;
	LPDWORD lpdwType = 0;
	PDH_FMT_COUNTERVALUE counterVal;
	char name[60] = "\\Informacje o procesorze(*)\\Czas procesora (%)";

	PdhOpenQuery(NULL, NULL, &cpuQuery);
	PdhAddCounter(cpuQuery, name, NULL, &cpuTotal);

	PdhCollectQueryData(cpuQuery);
	Sleep(100);
	PdhCollectQueryData(cpuQuery);

	PdhGetFormattedCounterValue(cpuTotal, PDH_FMT_DOUBLE, lpdwType, &counterVal);
	return counterVal.doubleValue;
}