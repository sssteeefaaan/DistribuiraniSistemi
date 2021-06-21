using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfServiceOktobar2020
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "ICisterna" in both code and config file together.
    [ServiceContract(CallbackContract = typeof(ICisternaCallback), SessionMode = SessionMode.Required)]
    public interface ICisterna
    {
        [OperationContract(IsOneWay = true)]
        void DodajMaterijal(Materijal m);
        [OperationContract(IsOneWay = true)]
        void IsipajMaterijal(decimal zapremina);
        [OperationContract(IsOneWay = false)]
        Materijal TrenutnoStanje();
        [OperationContract(IsOneWay = false)]
        List<string> Promene();
    }

    [DataContract]
    public class Materijal
    {
        [DataMember]
        public string Naziv { get; set; }
        [DataMember]
        public decimal Gustina { get; set; }
        [DataMember]
        public decimal Masa { get; set; }
        [DataMember]
        public decimal Zapremina { get; set; }

        public Materijal()
        {
            Masa = Gustina = Zapremina = 0;
            Naziv = "UNKNOWN";
        }
    }
}
