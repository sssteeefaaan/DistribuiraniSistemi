using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using WindowsFormsCisterna.ServiceReference1;

namespace WindowsFormsCisterna
{
    public partial class Form1 : Form
    {
        public CisternaClient Cisterna { get; set; }
        public Form1()
        {
            InitializeComponent();
            Cisterna = new CisternaClient();
        }

        private void buttonDodaj_Click(object sender, EventArgs e)
        {
            Materijal m = new Materijal();
            m.Naziv = textBox1.Text;
            m.Masa = decimal.Parse(textBoxDodajMasa.Text);
            m.Gustina = decimal.Parse(textBoxDodajGustina.Text);
            Cisterna.DodajMaterijal(m);
        }

        private void buttonIsipaj_Click(object sender, EventArgs e)
        {
            Cisterna.Isipaj(decimal.Parse(textBoxisipajZapremina.Text));
        }

        private void buttonUcitaj_Click(object sender, EventArgs e)
        {
            Materijal m = Cisterna.PreuzmiMaterijal();
            textBoxMasa.Text = m.Masa.ToString();
            textBoxZapremina.Text = m.Zapremina.ToString();
            textBoxGustina.Text = m.Gustina.ToString();
        }

        private void buttonUcitajPromene_Click(object sender, EventArgs e)
        {
            richTextBoxIstorija.Text = "";
            List<string> Promene = Cisterna.PreuzmiIzmene();
            Promene.ForEach(p => { richTextBoxIstorija.Text += p + Environment.NewLine; });
        }
    }
}
