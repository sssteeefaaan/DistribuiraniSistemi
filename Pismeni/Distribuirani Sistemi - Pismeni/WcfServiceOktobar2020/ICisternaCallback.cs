using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace WcfServiceOktobar2020
{
    public interface ICisternaCallback
    {
        [OperationContract(IsOneWay = true)]
        void NotifyStanje(bool puna);
    }
}
