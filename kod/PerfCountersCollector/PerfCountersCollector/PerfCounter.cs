using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PerfCountersCollector
{
    class PerfCounter
    {
        private System.Diagnostics.PerformanceCounter counter;
        private int queueSize;
        private Queue<float> valuesQueue;
        private float criticalValue;
        private float lastValue;
        private float avg;
        private String name;
        private String category;
        private bool above;

        public PerfCounter(String category, String instance, String counter, int queueSize, float criticalValue, bool above)
        {
            this.counter = new System.Diagnostics.PerformanceCounter();

            ((System.ComponentModel.ISupportInitialize)(this.counter)).BeginInit();
            this.counter.CategoryName = category;
            this.counter.CounterName = counter;
            this.counter.InstanceName = instance;
            ((System.ComponentModel.ISupportInitialize)(this.counter)).EndInit();

            this.queueSize = queueSize;
            this.valuesQueue = new Queue<float>();
            this.lastValue = 0;
            this.avg = 0;
            this.criticalValue = criticalValue;
            string instanceStr = instance == "" ? "" : " (" + instance + ")";  
            this.name = category + ": " + counter + instanceStr;
            this.category = category;
            this.above = above;
        }

        public System.Diagnostics.PerformanceCounter getCounter()
        {
            return counter;
        }

        public String getName()
        {
            return name;
        }

        public String getCategory()
        {
            return category;
        }

        public float getCritivalValue() 
        {
            return criticalValue;
        }

        public float getLastValue()
        {
            return lastValue;
        }

        public float getAvg()
        {
            return avg;
        }

        public int getQueueSize()
        {
            return queueSize;
        }

        public bool isAbove()
        {
            return above;
        }

        public void reset()
        {
            valuesQueue.Clear();
            lastValue = 0;
            avg = 0;
        }

        public bool check()
        {
            float counterValue = counter.NextValue();
            lastValue = counterValue;
            addValue(counterValue);
            return isCritical();
        }

        private void addValue(float value)
        {
            valuesQueue.Enqueue(value);
            if (valuesQueue.Count > queueSize)
                valuesQueue.Dequeue();
        }

        private bool isCritical()
        {
            if (valuesQueue.Count < queueSize)
                return false;

            float sum = 0.0F;

            foreach (float value in valuesQueue)
            {
                sum += value;
            }

            avg = sum / queueSize;
            if (above)
            {
                return avg > criticalValue ? true : false;
            }
            else
            {
                return avg < criticalValue ? true : false;
            }
        }
    }
}
