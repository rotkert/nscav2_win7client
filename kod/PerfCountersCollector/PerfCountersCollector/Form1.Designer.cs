﻿namespace PerfCountersCollector
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.performanceCounter1 = new System.Diagnostics.PerformanceCounter();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.categoryCb = new System.Windows.Forms.ComboBox();
            this.instanceCb = new System.Windows.Forms.ComboBox();
            this.nameCb = new System.Windows.Forms.ComboBox();
            this.addCounterBtn = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.isBelowRadio = new System.Windows.Forms.RadioButton();
            this.isAboveRadio = new System.Windows.Forms.RadioButton();
            this.numericUpDown3 = new System.Windows.Forms.NumericUpDown();
            this.label7 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.numericUpDown1 = new System.Windows.Forms.NumericUpDown();
            this.deleteBtn = new System.Windows.Forms.Button();
            this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.infoLabel = new System.Windows.Forms.Label();
            this.sendCustomBtn = new System.Windows.Forms.Button();
            this.stopBtn = new System.Windows.Forms.Button();
            this.startBtn = new System.Windows.Forms.Button();
            this.label5 = new System.Windows.Forms.Label();
            this.numericUpDown2 = new System.Windows.Forms.NumericUpDown();
            this.saveBtn = new System.Windows.Forms.Button();
            this.perfCounterBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.check = new System.Windows.Forms.DataGridViewCheckBoxColumn();
            this.name = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.queueSize = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.value = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.avgValue = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.criticalValue = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Type = new System.Windows.Forms.DataGridViewTextBoxColumn();
            ((System.ComponentModel.ISupportInitialize)(this.performanceCounter1)).BeginInit();
            this.groupBox1.SuspendLayout();
            this.groupBox3.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown3)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.groupBox2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.perfCounterBindingSource)).BeginInit();
            this.SuspendLayout();
            // 
            // performanceCounter1
            // 
            this.performanceCounter1.CategoryName = "Procesor";
            this.performanceCounter1.CounterName = "Czas procesora (%)";
            this.performanceCounter1.InstanceName = "_Total";
            this.performanceCounter1.MachineName = "Miko-Komputer";
            // 
            // timer1
            // 
            this.timer1.Enabled = true;
            this.timer1.Interval = 1000;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // categoryCb
            // 
            this.categoryCb.FormattingEnabled = true;
            this.categoryCb.Location = new System.Drawing.Point(65, 22);
            this.categoryCb.Name = "categoryCb";
            this.categoryCb.Size = new System.Drawing.Size(311, 21);
            this.categoryCb.Sorted = true;
            this.categoryCb.TabIndex = 0;
            this.categoryCb.SelectedIndexChanged += new System.EventHandler(this.categoryCb_SelectedIndexChanged);
            // 
            // instanceCb
            // 
            this.instanceCb.Enabled = false;
            this.instanceCb.FormattingEnabled = true;
            this.instanceCb.Location = new System.Drawing.Point(65, 49);
            this.instanceCb.Name = "instanceCb";
            this.instanceCb.Size = new System.Drawing.Size(310, 21);
            this.instanceCb.Sorted = true;
            this.instanceCb.TabIndex = 1;
            this.instanceCb.SelectedIndexChanged += new System.EventHandler(this.instanceCb_SelectedIndexChanged);
            // 
            // nameCb
            // 
            this.nameCb.Enabled = false;
            this.nameCb.FormattingEnabled = true;
            this.nameCb.Location = new System.Drawing.Point(64, 76);
            this.nameCb.Name = "nameCb";
            this.nameCb.Size = new System.Drawing.Size(311, 21);
            this.nameCb.TabIndex = 2;
            // 
            // addCounterBtn
            // 
            this.addCounterBtn.Location = new System.Drawing.Point(300, 159);
            this.addCounterBtn.Name = "addCounterBtn";
            this.addCounterBtn.Size = new System.Drawing.Size(75, 23);
            this.addCounterBtn.TabIndex = 3;
            this.addCounterBtn.Text = "Dodaj";
            this.addCounterBtn.UseVisualStyleBackColor = true;
            this.addCounterBtn.Click += new System.EventHandler(this.addCounterBtn_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(8, 28);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(46, 13);
            this.label1.TabIndex = 5;
            this.label1.Text = "Kategria";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(8, 52);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(50, 13);
            this.label2.TabIndex = 6;
            this.label2.Text = "Instancja";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(8, 79);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(40, 13);
            this.label3.TabIndex = 7;
            this.label3.Text = "Nazwa";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.groupBox3);
            this.groupBox1.Controls.Add(this.numericUpDown3);
            this.groupBox1.Controls.Add(this.label7);
            this.groupBox1.Controls.Add(this.label4);
            this.groupBox1.Controls.Add(this.numericUpDown1);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.addCounterBtn);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Controls.Add(this.nameCb);
            this.groupBox1.Controls.Add(this.categoryCb);
            this.groupBox1.Controls.Add(this.instanceCb);
            this.groupBox1.Location = new System.Drawing.Point(12, 12);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(415, 188);
            this.groupBox1.TabIndex = 8;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Nowy licznik";
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.isBelowRadio);
            this.groupBox3.Controls.Add(this.isAboveRadio);
            this.groupBox3.Location = new System.Drawing.Point(11, 130);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(165, 39);
            this.groupBox3.TabIndex = 15;
            this.groupBox3.TabStop = false;
            // 
            // isBelowRadio
            // 
            this.isBelowRadio.AutoSize = true;
            this.isBelowRadio.Location = new System.Drawing.Point(90, 11);
            this.isBelowRadio.Name = "isBelowRadio";
            this.isBelowRadio.Size = new System.Drawing.Size(64, 17);
            this.isBelowRadio.TabIndex = 1;
            this.isBelowRadio.Text = "mniejszy";
            this.isBelowRadio.UseVisualStyleBackColor = true;
            // 
            // isAboveRadio
            // 
            this.isAboveRadio.AutoSize = true;
            this.isAboveRadio.Checked = true;
            this.isAboveRadio.Location = new System.Drawing.Point(6, 11);
            this.isAboveRadio.Name = "isAboveRadio";
            this.isAboveRadio.Size = new System.Drawing.Size(62, 17);
            this.isAboveRadio.TabIndex = 0;
            this.isAboveRadio.TabStop = true;
            this.isAboveRadio.Text = "większy";
            this.isAboveRadio.UseVisualStyleBackColor = true;
            this.isAboveRadio.CheckedChanged += new System.EventHandler(this.radioButton1_CheckedChanged);
            // 
            // numericUpDown3
            // 
            this.numericUpDown3.Location = new System.Drawing.Point(300, 103);
            this.numericUpDown3.Name = "numericUpDown3";
            this.numericUpDown3.Size = new System.Drawing.Size(76, 20);
            this.numericUpDown3.TabIndex = 14;
            this.numericUpDown3.ThousandsSeparator = true;
            this.numericUpDown3.Value = new decimal(new int[] {
            10,
            0,
            0,
            0});
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(213, 106);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(74, 13);
            this.label7.TabIndex = 13;
            this.label7.Text = "Liczba próbek";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(8, 106);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(29, 13);
            this.label4.TabIndex = 12;
            this.label4.Text = "Próg";
            // 
            // numericUpDown1
            // 
            this.numericUpDown1.DecimalPlaces = 1;
            this.numericUpDown1.Location = new System.Drawing.Point(64, 104);
            this.numericUpDown1.Maximum = new decimal(new int[] {
            100000000,
            0,
            0,
            0});
            this.numericUpDown1.Name = "numericUpDown1";
            this.numericUpDown1.Size = new System.Drawing.Size(78, 20);
            this.numericUpDown1.TabIndex = 8;
            // 
            // deleteBtn
            // 
            this.deleteBtn.Location = new System.Drawing.Point(12, 385);
            this.deleteBtn.Name = "deleteBtn";
            this.deleteBtn.Size = new System.Drawing.Size(75, 23);
            this.deleteBtn.TabIndex = 10;
            this.deleteBtn.Text = "Usuń";
            this.deleteBtn.UseVisualStyleBackColor = true;
            this.deleteBtn.Click += new System.EventHandler(this.deleteBtn_Click);
            // 
            // dataGridView1
            // 
            this.dataGridView1.AllowUserToAddRows = false;
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.check,
            this.name,
            this.queueSize,
            this.value,
            this.avgValue,
            this.criticalValue,
            this.Type});
            this.dataGridView1.Location = new System.Drawing.Point(12, 229);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.Size = new System.Drawing.Size(668, 150);
            this.dataGridView1.TabIndex = 11;
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.textBox1);
            this.groupBox2.Controls.Add(this.label6);
            this.groupBox2.Controls.Add(this.infoLabel);
            this.groupBox2.Controls.Add(this.sendCustomBtn);
            this.groupBox2.Controls.Add(this.stopBtn);
            this.groupBox2.Controls.Add(this.startBtn);
            this.groupBox2.Controls.Add(this.label5);
            this.groupBox2.Controls.Add(this.numericUpDown2);
            this.groupBox2.Location = new System.Drawing.Point(465, 12);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(200, 188);
            this.groupBox2.TabIndex = 13;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Połączenie";
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(74, 37);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(120, 20);
            this.textBox1.TabIndex = 18;
            this.textBox1.Text = "127.0.0.1";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(19, 37);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(34, 13);
            this.label6.TabIndex = 17;
            this.label6.Text = "Adres";
            // 
            // infoLabel
            // 
            this.infoLabel.AutoSize = true;
            this.infoLabel.ForeColor = System.Drawing.Color.Red;
            this.infoLabel.Location = new System.Drawing.Point(19, 130);
            this.infoLabel.Name = "infoLabel";
            this.infoLabel.Size = new System.Drawing.Size(0, 13);
            this.infoLabel.TabIndex = 16;
            // 
            // sendCustomBtn
            // 
            this.sendCustomBtn.Enabled = false;
            this.sendCustomBtn.Location = new System.Drawing.Point(19, 159);
            this.sendCustomBtn.Name = "sendCustomBtn";
            this.sendCustomBtn.Size = new System.Drawing.Size(120, 23);
            this.sendCustomBtn.TabIndex = 14;
            this.sendCustomBtn.Text = "Wykonaj raport ";
            this.sendCustomBtn.UseVisualStyleBackColor = true;
            this.sendCustomBtn.Click += new System.EventHandler(this.sendCustomBtn_Click);
            // 
            // stopBtn
            // 
            this.stopBtn.Enabled = false;
            this.stopBtn.Location = new System.Drawing.Point(119, 101);
            this.stopBtn.Name = "stopBtn";
            this.stopBtn.Size = new System.Drawing.Size(75, 23);
            this.stopBtn.TabIndex = 15;
            this.stopBtn.Text = "Stop";
            this.stopBtn.UseVisualStyleBackColor = true;
            this.stopBtn.Click += new System.EventHandler(this.stopBtn_Click);
            // 
            // startBtn
            // 
            this.startBtn.Location = new System.Drawing.Point(19, 101);
            this.startBtn.Name = "startBtn";
            this.startBtn.Size = new System.Drawing.Size(75, 23);
            this.startBtn.TabIndex = 2;
            this.startBtn.Text = "Start";
            this.startBtn.UseVisualStyleBackColor = true;
            this.startBtn.Click += new System.EventHandler(this.startBtn_Click);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(19, 67);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(26, 13);
            this.label5.TabIndex = 1;
            this.label5.Text = "Port";
            // 
            // numericUpDown2
            // 
            this.numericUpDown2.Location = new System.Drawing.Point(74, 65);
            this.numericUpDown2.Maximum = new decimal(new int[] {
            1000000,
            0,
            0,
            0});
            this.numericUpDown2.Name = "numericUpDown2";
            this.numericUpDown2.Size = new System.Drawing.Size(120, 20);
            this.numericUpDown2.TabIndex = 0;
            this.numericUpDown2.Value = new decimal(new int[] {
            4545,
            0,
            0,
            0});
            this.numericUpDown2.ValueChanged += new System.EventHandler(this.numericUpDown2_ValueChanged);
            // 
            // saveBtn
            // 
            this.saveBtn.Location = new System.Drawing.Point(113, 385);
            this.saveBtn.Name = "saveBtn";
            this.saveBtn.Size = new System.Drawing.Size(104, 23);
            this.saveBtn.TabIndex = 19;
            this.saveBtn.Text = "Zapisz liczniki";
            this.saveBtn.UseVisualStyleBackColor = true;
            this.saveBtn.Click += new System.EventHandler(this.saveBtn_Click);
            // 
            // perfCounterBindingSource
            // 
            this.perfCounterBindingSource.DataSource = typeof(PerfCountersCollector.PerfCounter);
            // 
            // check
            // 
            this.check.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.AllCellsExceptHeader;
            this.check.HeaderText = "";
            this.check.Name = "check";
            this.check.TrueValue = "yes";
            this.check.Width = 5;
            // 
            // name
            // 
            this.name.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.name.FillWeight = 60F;
            this.name.HeaderText = "Nazwa";
            this.name.Name = "name";
            this.name.ReadOnly = true;
            // 
            // queueSize
            // 
            this.queueSize.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.queueSize.FillWeight = 8F;
            this.queueSize.HeaderText = "Próbki";
            this.queueSize.Name = "queueSize";
            this.queueSize.ReadOnly = true;
            // 
            // value
            // 
            this.value.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.value.FillWeight = 10F;
            this.value.HeaderText = "Wartość";
            this.value.Name = "value";
            this.value.ReadOnly = true;
            // 
            // avgValue
            // 
            this.avgValue.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.avgValue.FillWeight = 10F;
            this.avgValue.HeaderText = "Średnia";
            this.avgValue.Name = "avgValue";
            this.avgValue.ReadOnly = true;
            // 
            // criticalValue
            // 
            this.criticalValue.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.criticalValue.FillWeight = 10F;
            this.criticalValue.HeaderText = "Próg";
            this.criticalValue.Name = "criticalValue";
            this.criticalValue.ReadOnly = true;
            // 
            // Type
            // 
            this.Type.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.Type.FillWeight = 10F;
            this.Type.HeaderText = "Typ";
            this.Type.Name = "Type";
            this.Type.ReadOnly = true;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(692, 469);
            this.Controls.Add(this.saveBtn);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.deleteBtn);
            this.Controls.Add(this.groupBox1);
            this.Name = "Form1";
            this.Text = "Diagnostyka";
            ((System.ComponentModel.ISupportInitialize)(this.performanceCounter1)).EndInit();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown3)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.perfCounterBindingSource)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Diagnostics.PerformanceCounter performanceCounter1;
        public System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.ComboBox categoryCb;
        private System.Windows.Forms.ComboBox instanceCb;
        private System.Windows.Forms.ComboBox nameCb;
        private System.Windows.Forms.Button addCounterBtn;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.BindingSource perfCounterBindingSource;
        private System.Windows.Forms.Button deleteBtn;
        private System.ComponentModel.BackgroundWorker backgroundWorker1;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.NumericUpDown numericUpDown1;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Button startBtn;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.NumericUpDown numericUpDown2;
        private System.Windows.Forms.Button sendCustomBtn;
        private System.Windows.Forms.Button stopBtn;
        private System.Windows.Forms.Label infoLabel;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.NumericUpDown numericUpDown3;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Button saveBtn;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.RadioButton isBelowRadio;
        private System.Windows.Forms.RadioButton isAboveRadio;
        private System.Windows.Forms.DataGridViewCheckBoxColumn check;
        private System.Windows.Forms.DataGridViewTextBoxColumn name;
        private System.Windows.Forms.DataGridViewTextBoxColumn queueSize;
        private System.Windows.Forms.DataGridViewTextBoxColumn value;
        private System.Windows.Forms.DataGridViewTextBoxColumn avgValue;
        private System.Windows.Forms.DataGridViewTextBoxColumn criticalValue;
        private System.Windows.Forms.DataGridViewTextBoxColumn Type;
    }
}

