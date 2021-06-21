using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using WCFChatClient.SR;
using System.ServiceModel;

namespace WCFChatClient
{
    public partial class Form1 : Form, IChatCallback
    {
        private ChatClient proxy;
        public Form1()
        {
            InitializeComponent();
            proxy = new ChatClient(new InstanceContext(this));
        }

        private void buttonSend_Click(object sender, EventArgs e)
        {
            if (textBoxText.Text != "" &&
                textBoxText.Text != "" &&
                textBoxSender.Text != "")
            {
                SR.Message newMessage = new SR.Message();
                newMessage.Sender = textBoxSender.Text;
                newMessage.Text = textBoxText.Text;
                newMessage.Receiver = textBoxReceiver.Text;
                //newMessage.Sent = DateTime.Now;

                textBoxMessages.Text += "You: " + newMessage.Text;
                textBoxMessages.Text += Environment.NewLine;
                textBoxMessages.Text += DateTime.Now.ToShortTimeString();
                textBoxMessages.Text += Environment.NewLine;

                textBoxText.Text = "";

                proxy.Send(newMessage);
            }
        }

        public void Error(string error)
        {
            MessageBox.Show(this, 
                error,
                "Greška",
                MessageBoxButtons.OK,
                MessageBoxIcon.Warning);
        }

        public void Receive(SR.Message m)
        {
            textBoxMessages.Text += m.Sender + ": " + m.Text;
            textBoxMessages.Text += Environment.NewLine;
            textBoxMessages.Text += m.Sent?.ToShortTimeString();
            textBoxMessages.Text += Environment.NewLine;

            proxy.Read(m);
        }

        private void buttonRegister_Click(object sender, EventArgs e)
        {
            if (textBoxSender.Text != "")
            {
                proxy.Register(textBoxSender.Text);
            }
        }

        private void buttonReadNew_Click(object sender, EventArgs e)
        {
            if (textBoxSender.Text != "")
            {
                proxy.ReadNew(textBoxSender.Text).ForEach(msg =>
                {
                    textBoxMessages.Text += msg.Sender + ": " + msg.Text;
                    textBoxMessages.Text += Environment.NewLine;
                    textBoxMessages.Text += msg.Sent?.ToShortTimeString();
                    textBoxMessages.Text += Environment.NewLine;
                });
            }
        }

        private void textBoxText_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
                buttonSend.PerformClick();
        }
    }
}
