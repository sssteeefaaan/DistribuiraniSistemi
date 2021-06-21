using FormCalculator.ServiceReference1;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FormCalculator
{
    public partial class Form1 : Form, ICalculatorCallback
    {
        private CalculatorClient Calculator { get; set; }
        private string _opMode = "";
        private bool _clear = true;
        public Form1()
        {
            InitializeComponent();
            Calculator = new CalculatorClient(new InstanceContext(this));
            Calculator.Register(DateTime.Now.ToString());
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            textBoxHistory.Text = "";
            textBoxResultat.Text = "0";
        }

        public void Rezultat(Rezultat result)
        {
            textBoxHistory.Text = result.History;
            textBoxResultat.Text = result.Value.ToString();
        }

        private void button0_Click(object sender, EventArgs e)
        {
            if (_clear)
                textBoxResultat.Text = "0";
            else if (textBoxResultat.Text != "0")
                textBoxResultat.Text += "0";
        }
        private void button1_Click(object sender, EventArgs e)
        {
            if (_clear || textBoxResultat.Text == "0")
            { 
                _clear = false;
                textBoxResultat.Text = "1";
            }
            else
                textBoxResultat.Text += "1";
        }
        private void button2_Click(object sender, EventArgs e)
        {
            if (_clear || textBoxResultat.Text == "0")
            {
                _clear = false;
                textBoxResultat.Text = "2";
            }
            else
                textBoxResultat.Text += "2";
        }
        private void button3_Click(object sender, EventArgs e)
        {
            if (_clear || textBoxResultat.Text == "0")
            {
                _clear = false;
                textBoxResultat.Text = "3";
            }
            else
                textBoxResultat.Text += "3";
        }
        private void button4_Click(object sender, EventArgs e)
        {
            if (_clear || textBoxResultat.Text == "0")
            {
                _clear = false;
                textBoxResultat.Text = "4";
            }
            else
                textBoxResultat.Text += "4";
        }
        private void button5_Click(object sender, EventArgs e)
        {
            if (_clear || textBoxResultat.Text == "0")
            {
                _clear = false;
                textBoxResultat.Text = "5";
            }
            else
                textBoxResultat.Text += "5";
        }
        private void button6_Click(object sender, EventArgs e)
        {
            if (_clear || textBoxResultat.Text == "0")
            {
                _clear = false;
                textBoxResultat.Text = "6";
            }
            else
                textBoxResultat.Text += "6";
        }
        private void button7_Click(object sender, EventArgs e)
        {
            if (_clear || textBoxResultat.Text == "0")
            {
                _clear = false;
                textBoxResultat.Text = "7";
            }
            else
                textBoxResultat.Text += "7";
        }
        private void button8_Click(object sender, EventArgs e)
        {
            if (_clear || textBoxResultat.Text == "0")
            {
                _clear = false;
                textBoxResultat.Text = "8";
            }
            else
                textBoxResultat.Text += "8";
        }
        private void button9_Click(object sender, EventArgs e)
        {
            if (_clear || textBoxResultat.Text == "0")
            {
                _clear = false;
                textBoxResultat.Text = "9";
            }
            else
                textBoxResultat.Text += "9";
        }
        private void buttonClear_Click(object sender, EventArgs e)
        {
            Clear();
        }
        private void buttonDot_Click(object sender, EventArgs e)
        {
            if (_clear)
                Clear();
            else if (!textBoxResultat.Text.Contains("."))
                textBoxResultat.Text += ".";
        }

        private void buttonSign_Click(object sender, EventArgs e)
        {
            if (textBoxResultat.Text[0] != '0')
            {
                if (textBoxResultat.Text[0] != '-')// && textBoxResultat.Text[0] != '0')
                    textBoxResultat.Text = "-" + textBoxResultat.Text;
                else
                    textBoxResultat.Text = textBoxResultat.Text.Substring(1, textBoxResultat.Text.Length - 1);
                _clear = false;
            }
        }

        private void buttonPlus_Click(object sender, EventArgs e)
        {
            _opMode = "+";
            if (!_clear)
            {
                _clear = true;
                Calculator.Saberi(decimal.Parse(textBoxResultat.Text));
            }
        }

        private void buttonMinus_Click(object sender, EventArgs e)
        {
            _opMode = "-";
            if (!_clear)
            {
                _clear = true;
                Calculator.Oduzmi(decimal.Parse(textBoxResultat.Text));
            }
        }

        private void buttonTimes_Click(object sender, EventArgs e)
        {
            _opMode = "*";
            if (!_clear)
            {
                _clear = true;
                Calculator.Pomnozi(decimal.Parse(textBoxResultat.Text));
            }
        }

        private void buttonDivide_Click(object sender, EventArgs e)
        {
            _opMode = "/";
            if (!_clear)
            {
                _clear = true;
                Calculator.Podeli(decimal.Parse(textBoxResultat.Text));
            }
        }

        private void buttonEquals_Click(object sender, EventArgs e)
        {
            switch (_opMode)
            {
                case "+":
                    _clear = true;
                    Calculator.Saberi(decimal.Parse(textBoxResultat.Text));
                    break;
                case "-":
                    _clear = true;
                    Calculator.Oduzmi(decimal.Parse(textBoxResultat.Text));
                    break;
                case "*":
                    _clear = true;
                    Calculator.Pomnozi(decimal.Parse(textBoxResultat.Text));
                    break;
                case "/":
                    _clear = true;
                    Calculator.Podeli(decimal.Parse(textBoxResultat.Text));
                    break;
            }
        }
        private void Clear()
        {
            _clear = false;
            Calculator.Obrisi();
        }
    }
}
