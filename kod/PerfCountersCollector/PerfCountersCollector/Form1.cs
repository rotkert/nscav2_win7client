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
        private bool isSendMode;

        public Form1()
        {
            InitializeComponent();
            counters = new List<PerfCounter>();
            infoSender = new Sender();
            isSendMode = false;
            
            System.Diagnostics.PerformanceCounterCategory[] categories = System.Diagnostics.PerformanceCounterCategory.GetCategories();
            for (int i = 0; i < categories.Length; i++)
            {
                categoryCb.Items.Add(categories[i].CategoryName);
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            for (int i = 0; i < counters.Count; i++)
            {
                PerfCounter counter = counters[i];

                if (counter.Check() && isSendMode)
                {
                    sendReportRequest(counter.getCategory());
                    clearCountersValues();
                    break;
                }
                else
                {
                    dataGridView1.Rows[i].Cells[2].Value = counter.getLastValue();
                    dataGridView1.Rows[i].Cells[3].Value = counter.getAvg();
                }
            }
        }

        private void sendReportRequest(String categoryName)
        {
            bool isSent = infoSender.sendInfo(categoryName);

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

            if (String.IsNullOrEmpty(category) || String.IsNullOrEmpty(name) || criticalValue == 0)
            {
                MessageBox.Show("Proszę wybrać wszystkie wartości");
                return;
            }
            else
            {
                try
                {
                    PerfCounter newCounter = new PerfCounter(category, name, instance, 2, (float)criticalValue);
                    counters.Add(newCounter);
                    categoryCb.Text = "";
                    instanceCb.Text = "";
                    nameCb.Text = "";
                    numericUpDown1.Value = 0;

                    instanceCb.Enabled = false;
                    nameCb.Enabled = false;

                    var index = dataGridView1.Rows.Add();
                    dataGridView1.Rows[index].Cells[1].Value = newCounter.getName();
                    dataGridView1.Rows[index].Cells[2].Value = 0;
                    dataGridView1.Rows[index].Cells[3].Value = 0;
                    dataGridView1.Rows[index].Cells[4].Value = criticalValue;
                }
                catch(System.InvalidOperationException ex)
                {
                    MessageBox.Show("Nie udało się dodać licznika: " + ex.Message);
                }
            }

        }

        private void deleteBtn_Click(object sender, EventArgs e)
        {
            
            foreach (DataGridViewRow row in dataGridView1.Rows)
            {
                object value = row.Cells[0].Value;
                if (value != null && value.Equals("yes"))
                {
                    if (MessageBox.Show("Usunąć zaznaczone liczniki?", "Confirm", MessageBoxButtons.YesNo) == DialogResult.Yes)
                    {
                        counters.RemoveAt(row.Index);
                        dataGridView1.Rows.RemoveAt(row.Index);
                    }
                }
            }
        }

        private void startBtn_Click(object sender, EventArgs e)
        {
            infoSender.setPort(Convert.ToInt32(numericUpDown2.Value));
            numericUpDown2.Enabled = false;
            startBtn.Enabled = false;
            stopBtn.Enabled = true;
            sendCustomBtn.Enabled = true;
            isSendMode = true;
            infoLabel.Text = "";
        }

        private void sendCustomBtn_Click(object sender, EventArgs e)
        {
            sendReportRequest("User");
        }

        private void stopBtn_Click(object sender, EventArgs e)
        {
            stopSending("");
        }

    
        
    }
}
