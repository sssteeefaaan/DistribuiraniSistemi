using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.ServiceModel;
using WindowsFormsCisterna.SRCisterna;

namespace WindowsFormsCisterna
{
    public partial class Form1 : Form, ICisternaCallback
    {
        public CisternaClient Cisterna { get; set; }
        public Form1()
        {
            InitializeComponent();
            Cisterna = new CisternaClient(new InstanceContext(this));
        }

        private void buttonDodaj_Click(object sender, EventArgs e)
        {
            Materijal m = new Materijal();
            m.Naziv = textBox1.Text;
            m.Zapremina = decimal.Parse(textBoxDodajZapremina.Text);
            m.Gustina = decimal.Parse(textBoxDodajGustina.Text);
            Cisterna.DodajMaterijal(m);
        }

        private void buttonIsipaj_Click(object sender, EventArgs e)
        {
            Cisterna.IsipajMaterijal(decimal.Parse(textBoxisipajZapremina.Text));
        }

        private void buttonUcitaj_Click(object sender, EventArgs e)
        {
            Materijal m = Cisterna.TrenutnoStanje();
            textBoxMasa.Text = m.Masa.ToString();
            textBoxZapremina.Text = m.Zapremina.ToString();
            textBoxGustina.Text = m.Gustina.ToString();
        }

        private void buttonUcitajPromene_Click(object sender, EventArgs e)
        {
            richTextBoxIstorija.Text = "";
            List<string> Promene = Cisterna.Promene();
            Promene.ForEach(p => { richTextBoxIstorija.Text += p + Environment.NewLine; });
        }

        public void NotifyStanje(bool puna)
        {
            if (puna)
                MessageBox.Show(this,
                    "Cisterna je puna!",
                    "Obaveštenje",
                    MessageBoxButtons.OK,
                    MessageBoxIcon.Information);
            else
                MessageBox.Show(this,
                    "Cisterna je prazna!",
                    "Obaveštenje",
                    MessageBoxButtons.OK,
                    MessageBoxIcon.Information);
        }
    }
}
