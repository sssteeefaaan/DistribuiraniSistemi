using System;
using System.Drawing;
using System.Windows.Forms;
using WindowsFormsAppExactTime.ServiceReferenceExactTime;

namespace WindowsFormsAppExactTime
{
    public partial class FormExactTime : Form
    {
        ServiceExactTimeClient proxy;
        public FormExactTime()
        {
            InitializeComponent();
            proxy = new ServiceExactTimeClient();
        }

        private void FormExactTime_Load(object sender, EventArgs e)
        {
            buttonExactTime.Location = new Point((Width - buttonExactTime.Width) / 2, (Height - buttonExactTime.Height) / 2);
            labelExactTime.Location = new Point((Width - labelExactTime.Width) / 2, buttonExactTime.Location.Y - labelExactTime.Height - 5);
        }

        private void buttonExactTime_Click(object sender, EventArgs e)
        {
            labelExactTime.Text = proxy.GetExactTime().ToString();
            labelExactTime.Location = new Point((Width - labelExactTime.Width) / 2, buttonExactTime.Location.Y - labelExactTime.Height - 5);
        }

        private void FormExactTime_ResizeEnd(object sender, EventArgs e)
        {
            buttonExactTime.Location = new Point((Width - buttonExactTime.Width) / 2, (Height - buttonExactTime.Height) / 2);
            labelExactTime.Location = new Point((Width - labelExactTime.Width) / 2, buttonExactTime.Location.Y - labelExactTime.Height - 5);
        }
    }
}
