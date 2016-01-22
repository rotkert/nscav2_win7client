using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PerfCountersCollector
{
    public partial class Form1 : Form
    {
        private List<PerfCounter> counters;
        private Sender  infoSender;
        private FileHandler fileHandler;
        private bool isSendMode;

        public Form1()
        {
            InitializeComponent();
            infoSender = new Sender();
            fileHandler = new FileHandler();
            isSendMode = false;

            counters = fileHandler.loadCounters();
            foreach(PerfCounter counter in counters)
            {
                addCounterToDataView(counter.getName(), counter.getQueueSize(), counter.getCritivalValue(), counter.isAbove());
            }
            
            System.Diagnostics.PerformanceCounterCategory[] categories = System.Diagnostics.PerformanceCounterCategory.GetCategories();
            for (int i = 0; i < categories.Length; i++)
            {
                categoryCb.Items.Add(categories[i].CategoryName);
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            List<string> criticalCounters = new List<string>();
            for (int i = 0; i < counters.Count; i++)
            {
                PerfCounter counter = counters[i];

                if (counter.check() && isSendMode)
                {
                    criticalCounters.Add(counter.getCategory() + "=1");
                }
                else
                {
                    dataGridView1.Rows[i].Cells[3].Value = counter.getLastValue();
                    dataGridView1.Rows[i].Cells[4].Value = counter.getAvg();
                }

            }
            
            if (criticalCounters.Count > 0)
            {
                sendReportRequest(string.Join(" ", criticalCounters.ToArray()));
                clearCountersValues();
            }
        }

        private void sendReportRequest(String performanceData)
        {
            bool isSent = infoSender.sendInfo(performanceData);

            if(!isSent)
            {
                stopSending("Nie można było się połączyć z serwerem");
            }
        }

        private void stopSending(String message)
        {
            isSendMode = false;
            startBtn.Enabled = true;
            stopBtn.Enabled = false;
            numericUpDown2.Enabled = true;
            textBox1.Enabled = true;
            sendCustomBtn.Enabled = false;
            infoLabel.Text = message;
        }

        private void clearCountersValues()
        {
            for (int i = 0; i < counters.Count; i++)
            {
                PerfCounter counter = counters[i];

                counter.reset();
                dataGridView1.Rows[i].Cells[2].Value = counter.getLastValue();
                dataGridView1.Rows[i].Cells[3].Value = counter.getAvg();
            }
        }

        private void categoryCb_SelectedIndexChanged(object sender, EventArgs e)
        {
            string[] instanceNames;
            
            var category = new System.Diagnostics.PerformanceCounterCategory(categoryCb.SelectedItem.ToString());
            
            instanceCb.Items.Clear();
            nameCb.Items.Clear();
            instanceCb.Text = "";
            nameCb.Text = "";
            instanceCb.Enabled = true;
            nameCb.Enabled = false;

            try
            {
                instanceNames = category.GetInstanceNames();
                if (instanceNames.Length == 0)
                {
                    instanceCb.Enabled = false;
                    nameCb.Enabled = true;

                    System.Collections.ArrayList counters = new System.Collections.ArrayList();
                    counters.AddRange(category.GetCounters());

                    foreach (System.Diagnostics.PerformanceCounter counter in counters)
                    {
                        nameCb.Items.Add(counter.CounterName);
                    }
                }
                else
                {
                    for (int i = 0; i < instanceNames.Length; i++)
                    {
                        instanceCb.Items.Add(instanceNames[i]);
                    }
                }
            }
            catch (System.Exception ex)
            {
                MessageBox.Show("Unable to list the counters for this category:\n" + ex.Message);
            }
            
        }

        private void instanceCb_SelectedIndexChanged(object sender, EventArgs e)
        {
            nameCb.Items.Clear();
            nameCb.Text= "";
            nameCb.Enabled = true;

            var category = new System.Diagnostics.PerformanceCounterCategory(categoryCb.SelectedItem.ToString());

            System.Collections.ArrayList counters = new System.Collections.ArrayList();
            counters.AddRange(category.GetCounters(instanceCb.Text.ToString()));

            foreach (System.Diagnostics.PerformanceCounter counter in counters)
            {
                nameCb.Items.Add(counter.CounterName);
            }
        }

        private void addCounterBtn_Click(object sender, EventArgs e)
        {
            String category = categoryCb.Text.ToString();
            String instance = instanceCb.Text.ToString();
            String name = nameCb.Text.ToString();
            decimal criticalValue = numericUpDown1.Value;
            decimal sampleAmount = numericUpDown3.Value;
            bool isAbove = isAboveRadio.Checked;

            if (String.IsNullOrEmpty(category) || String.IsNullOrEmpty(name) || criticalValue == 0)
            {
                MessageBox.Show("Proszę wybrać wszystkie wartości");
                return;
            }
            else
            {
                try
                {
                    PerfCounter newCounter = new PerfCounter(category, instance, name, (int)sampleAmount, (float)criticalValue, isAbove);
                    counters.Add(newCounter);
                    categoryCb.Text = "";
                    instanceCb.Text = "";
                    nameCb.Text = "";
                    numericUpDown1.Value = 0;

                    instanceCb.Enabled = false;
                    nameCb.Enabled = false;

                    addCounterToDataView(newCounter.getName(), newCounter.getQueueSize(), newCounter.getCritivalValue(), isAbove);
                }
                catch(System.InvalidOperationException ex)
                {
                    MessageBox.Show("Nie udało się dodać licznika: " + ex.Message);
                }
            }

        }

        private void addCounterToDataView(string name, int queueSize, float criticalValue, bool isAbove)
        {
            String isAboveString = isAbove ? "Większy" : "Mniejszy";
            var index = dataGridView1.Rows.Add();
            dataGridView1.Rows[index].Cells[1].Value = name;
            dataGridView1.Rows[index].Cells[2].Value = queueSize;
            dataGridView1.Rows[index].Cells[3].Value = 0;
            dataGridView1.Rows[index].Cells[4].Value = 0;
            dataGridView1.Rows[index].Cells[5].Value = criticalValue;
            dataGridView1.Rows[index].Cells[6].Value = isAboveString;
        }

        private void deleteBtn_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("Usunąć zaznaczone liczniki?", "Confirm", MessageBoxButtons.YesNo) == DialogResult.Yes)
            {
                for (int i = dataGridView1.Rows.Count - 1; i >= 0; i--)
                {
                    DataGridViewRow row = dataGridView1.Rows[i]; 
                    object value = row.Cells[0].Value;
                    if (value != null && value.Equals("yes"))
                    {
                        counters.RemoveAt(row.Index);
                        dataGridView1.Rows.RemoveAt(row.Index);
                    }
                }
            }
        }

        private void startBtn_Click(object sender, EventArgs e)
        {
            infoSender.setAddress(textBox1.Text);
            infoSender.setPort(Convert.ToInt32(numericUpDown2.Value));
            textBox1.Enabled = false;
            numericUpDown2.Enabled = false;
            startBtn.Enabled = false;
            stopBtn.Enabled = true;
            sendCustomBtn.Enabled = true;
            isSendMode = true;
            infoLabel.Text = "";
        }

        private void sendCustomBtn_Click(object sender, EventArgs e)
        {
            sendReportRequest("User=1");
        }

        private void stopBtn_Click(object sender, EventArgs e)
        {
            stopSending("");
        }

        private void numericUpDown2_ValueChanged(object sender, EventArgs e)
        {

        }

        private void saveBtn_Click(object sender, EventArgs e)
        {
            if (fileHandler.saveCounters(counters))
            {
                MessageBox.Show("Liczniki zostały zapisane");
            }
            else
            {
                MessageBox.Show("Nie udało się zapisać liczników", "Błąd", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {

        }

    }
}
