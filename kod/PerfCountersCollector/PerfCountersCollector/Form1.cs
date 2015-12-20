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

        public Form1()
        {
            InitializeComponent();
            counters = new List<PerfCounter>();
            infoSender = new Sender();
            
            System.Diagnostics.PerformanceCounterCategory[] categories = System.Diagnostics.PerformanceCounterCategory.GetCategories();
            for (int i = 0; i < categories.Length; i++)
            {
                comboBox1.Items.Add(categories[i].CategoryName);
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            List<string> criticalCounters = new List<string>();

            for (int i = 0; i < counters.Count; i++)
            {
                PerfCounter counter = counters[i];

                if (counter.Check())
                {
                    criticalCounters.Add(counter.getCounterName());
                }

                dataGridView1.Rows[i].Cells[2].Value = counter.getLastValue();
                dataGridView1.Rows[i].Cells[3].Value = counter.getLastValue();
            }

            bool isSent = true;
            if (criticalCounters.Count > 0)
            {
                isSent = infoSender.sendInfo("Counters: " + string.Join(", ", criticalCounters.ToArray()) + " have exceeded crtical value.");
            }

            if (isSent == false)
            {
                stopCounters();
            }
        }

        private void stopCounters()
        {
            timer1.Enabled = false;
            button3.Enabled = true;
            numericUpDown2.Enabled = true;
            MessageBox.Show("Nie udało się wysłać informacji o sytuacji krytycznej");
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            string[] instanceNames;
            
            var category = new System.Diagnostics.PerformanceCounterCategory(comboBox1.SelectedItem.ToString());
            
            comboBox2.Items.Clear();
            comboBox3.Items.Clear();
            comboBox2.Text = "";
            comboBox3.Text = "";
            comboBox2.Enabled = true;
            comboBox3.Enabled = false;

            try
            {
                instanceNames = category.GetInstanceNames();
                if (instanceNames.Length == 0)
                {
                    comboBox2.Enabled = false;
                    comboBox3.Enabled = true;

                    System.Collections.ArrayList counters = new System.Collections.ArrayList();
                    counters.AddRange(category.GetCounters());

                    foreach (System.Diagnostics.PerformanceCounter counter in counters)
                    {
                        comboBox3.Items.Add(counter.CounterName);
                    }
                }
                else
                {
                    for (int i = 0; i < instanceNames.Length; i++)
                    {
                        comboBox2.Items.Add(instanceNames[i]);
                    }
                }
            }
            catch (System.Exception ex)
            {
                MessageBox.Show("Unable to list the counters for this category:\n" + ex.Message);
            }
            
        }

        private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            comboBox3.Items.Clear();
            comboBox3.Text= "";
            comboBox3.Enabled = true;

            var category = new System.Diagnostics.PerformanceCounterCategory(comboBox1.SelectedItem.ToString());

            System.Collections.ArrayList counters = new System.Collections.ArrayList();
            counters.AddRange(category.GetCounters(comboBox2.Text.ToString()));

            foreach (System.Diagnostics.PerformanceCounter counter in counters)
            {
                comboBox3.Items.Add(counter.CounterName);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            String category = comboBox1.Text.ToString();
            String instance = comboBox2.Text.ToString();
            String name = comboBox3.Text.ToString();
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
                    PerfCounter newCounter = new PerfCounter(category, name, instance, 2, 2);
                    counters.Add(newCounter);
                    comboBox1.Text = "";
                    comboBox2.Text = "";
                    comboBox3.Text = "";
                    numericUpDown1.Value = 0;

                    comboBox2.Enabled = false;
                    comboBox3.Enabled = false;

                    var index = dataGridView1.Rows.Add();
                    dataGridView1.Rows[index].Cells[1].Value = newCounter.getCounterName();
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

        private void button2_Click(object sender, EventArgs e)
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

        private void button3_Click(object sender, EventArgs e)
        {
            infoSender.setPort(Convert.ToInt32(numericUpDown1.Value));
            numericUpDown2.Enabled = false;
            button3.Enabled = false;
            timer1.Enabled = true;
        }

        
    }
}
