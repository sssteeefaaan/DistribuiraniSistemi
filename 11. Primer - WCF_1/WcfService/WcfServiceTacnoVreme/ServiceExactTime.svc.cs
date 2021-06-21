using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfServiceTacnoVreme
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "ServiceExactTime" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select ServiceExactTime.svc or ServiceExactTime.svc.cs at the Solution Explorer and start debugging.
    //[ServiceContract]
    public class ServiceExactTime : IServiceExactTime
    {
        public DateTime GetExactTime()
        {
            return DateTime.Now;
        }
    }
}
