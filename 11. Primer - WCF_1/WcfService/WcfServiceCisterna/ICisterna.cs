using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfServiceCisterna
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "ICisterna" in both code and config file together.
    [ServiceContract]
    public interface ICisterna
    {
        [OperationContract]
        void DodajMaterijal(Materijal m);
        [OperationContract]
        void Isipaj(decimal Zapremina);
        [OperationContract]
        Materijal PreuzmiMaterijal();
        [OperationContract]
        List<string> PreuzmiIzmene();
    }

    [DataContract]
    public class Materijal
    {
        [DataMember]
        public String Naziv { get; set; }
        [DataMember]
        public decimal Masa { get; set; }
        [DataMember]
        public decimal Gustina { get; set; }
        [DataMember]
        public decimal Zapremina { get; set; }
    }
}
