using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace Calculator
{
    public interface ICalculatorCallback
    {
        [OperationContract(IsOneWay = true)]
        void Rezultat(Rezultat result);
    }
}
