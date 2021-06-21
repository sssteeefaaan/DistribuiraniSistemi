using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WCFChat
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IChat" in both code and config file together.
    [ServiceContract(CallbackContract = typeof(IChatCallback), SessionMode = SessionMode.Required)]
    public interface IChat
    {
        [OperationContract(IsOneWay = true)]
        void Register(string user);

        [OperationContract(IsOneWay = true)]
        void Send(Message m);
        [OperationContract(IsOneWay = true)]
        void Read(Message m);

        [OperationContract(IsOneWay = false)]
        List<Message> ReadNew(string nickname);

    }

    [DataContract]
    public class Message
    {
        [DataMember]
        public string ID { get; set; }
        [DataMember]
        public string Sender { get; set; }
        [DataMember]
        public string Receiver { get; set; }
        [DataMember]
        public string Text { get; set; }
        [DataMember]
        public bool Read { get; set; }
        [DataMember]
        public DateTime? Sent { get; set; }
        [DataMember]
        public DateTime? Received { get; set; }

        public Message()
        { 
        }
    }

    public class User
    {
        public List<Message> Sent { get; set; }
        public List<Message> Received { get; set; }
        public string Nickname { get; set; }
        public IChatCallback Callback { get; set; }

        public User(string nickname)
        {
            Nickname = nickname;
            Sent = new List<Message>();
            Received = new List<Message>();
            Callback = null;
        }
    }
}
