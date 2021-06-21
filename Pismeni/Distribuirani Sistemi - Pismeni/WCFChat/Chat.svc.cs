using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WCFChat
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Chat" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Chat.svc or Chat.svc.cs at the Solution Explorer and start debugging.
    [ServiceBehavior(InstanceContextMode =InstanceContextMode.Single)]
    public class Chat : IChat
    {
        public static Dictionary<string, User> Users { get; set; } = new Dictionary<string, User>();
        public static Random rand = new Random();
        public Chat()
        {
            
        }

        public void Register(string username)
        {
            if (Users.ContainsKey(username))
                OperationContext.Current.GetCallbackChannel<IChatCallback>().Error("Username taken!");
            else
            {
                User newUser = new User(username);
                newUser.Callback = OperationContext.Current.GetCallbackChannel<IChatCallback>();
                Users.Add(username, newUser);
            }
        }

        public void Send(Message m)
        {
            if (!Users.ContainsKey(m.Sender))
            {
                OperationContext.Current.GetCallbackChannel<IChatCallback>().Error("Sender isn't registered!");
                return;
            }
            if (!Users.ContainsKey(m.Receiver))
            {
                OperationContext.Current.GetCallbackChannel<IChatCallback>().Error("Receiver isn't registered!");
                return;
            }

            m.ID = rand.Next(Int16.MaxValue, Int32.MaxValue).ToString();
            m.Sent = DateTime.Now;

            Users[m.Sender].Sent.Add(m);
            Users[m.Receiver].Received.Add(m);

            Users[m.Receiver].Callback.Receive(m);
        }

        public List<Message> ReadNew(string username)
        {
            List<Message> ret = new List<Message>();
            if (Users.ContainsKey(username))
            {
                Users[username].Received.ForEach(m =>
                {
                    if (!m.Read)
                    {
                        m.Received = DateTime.Now;
                        ret.Add(m);
                    }
                });
            }
            else
                OperationContext.Current.GetCallbackChannel<IChatCallback>().Error("User isn't registered!");

            return ret;
        }

        public void Read(Message m)
        {
            if (Users.ContainsKey(m.Receiver))
            {
                Users[m.Receiver].Received.ForEach(msg =>
                {
                    if (msg.ID == m.ID)
                    {
                        msg.Received = DateTime.Now;
                        msg.Read = true;
                    }
                });
            }
            else
                OperationContext.Current.GetCallbackChannel<IChatCallback>().Error("User isn't registered!");
        }
    }
}
