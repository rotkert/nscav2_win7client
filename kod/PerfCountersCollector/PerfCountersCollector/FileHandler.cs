using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PerfCountersCollector
{
    class FileHandler
    {
        private string filePath;

        public FileHandler()
        {
            filePath = Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + "\\Nscav2_client\\counters.txt";
        }

        public bool saveCounters(List<PerfCounter> perfCounters)
        {
            bool isSaved = true; 

            try
            {
                StringBuilder sb = new StringBuilder();

                foreach (PerfCounter perfCounter in perfCounters)
                {
                    System.Diagnostics.PerformanceCounter counter = perfCounter.getCounter();
                    String isAboveSign = perfCounter.isAbove() ? "1" : "0";
                    string counterRow = string.Format("{0},{1},{2},{3},{4},{5}", counter.CategoryName, counter.InstanceName, counter.CounterName, perfCounter.getQueueSize(), perfCounter.getCritivalValue(), isAboveSign);
                    sb.AppendLine(counterRow);
                }

                System.IO.File.WriteAllText(filePath, sb.ToString());
            }
            catch(System.IO.DirectoryNotFoundException)
            {
                isSaved = false;
            }
            catch(System.IO.IOException)
            {
                isSaved = false;
            }

            return isSaved;
        }

        public List<PerfCounter> loadCounters()
        {
            List<PerfCounter> perfCounters = new List<PerfCounter>();

            try
            {
                System.IO.StreamReader sr = new System.IO.StreamReader(System.IO.File.OpenRead(filePath));

                while(!sr.EndOfStream)
                {
                    string counterRow = sr.ReadLine();
                    string[] valuesTable = counterRow.Split(',');

                    bool isAbove = Int32.Parse(valuesTable[5]) == 1 ? true : false;  
                    PerfCounter perfCounter = new PerfCounter(valuesTable[0], valuesTable[1], valuesTable[2], Int32.Parse(valuesTable[3]), float.Parse(valuesTable[4]), isAbove);
                    perfCounters.Add(perfCounter);
                }
            }
            catch (System.IO.DirectoryNotFoundException)
            {
                // Nothing to do
            }
            catch (System.IO.FileNotFoundException)
            {
                // Nothing to do
            }

            return perfCounters;
        }
    }
}
