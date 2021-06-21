using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace WCFChat
{
    public interface IChatCallback
    {
        [OperationContract(IsOneWay = true)]
        void Receive(Message m);
        [OperationContract(IsOneWay = true)]
        void Error(string error);
    }
}
