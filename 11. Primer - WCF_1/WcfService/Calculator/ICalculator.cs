using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Calculator
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "ICalculator" in both code and config file together.
    [ServiceContract(CallbackContract = typeof(ICalculatorCallback), SessionMode = SessionMode.Required)]
    public interface ICalculator
    {
        [OperationContract(IsOneWay = true)]
        void Saberi(decimal number);
        [OperationContract(IsOneWay = true)]
        void Oduzmi(decimal number);
        [OperationContract(IsOneWay = true)]
        void Pomnozi(decimal number);
        [OperationContract(IsOneWay = true)]
        void Podeli(decimal number);
        [OperationContract(IsOneWay = true)]
        void Obrisi();
        [OperationContract(IsOneWay = true)]
        void Register(string ID);
    }
}
