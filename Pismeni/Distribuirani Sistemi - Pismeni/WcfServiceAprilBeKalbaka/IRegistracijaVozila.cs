using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfServiceAprilBeKalbaka
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IRegistracijaVozila" in both code and config file together.
    [ServiceContract]
    public interface IRegistracijaVozila
    {
        [OperationContract]
        void Register(Vozilo vo, Vlasnik vl);
        [OperationContract]
        List<Vlasnik> GetVlasniciModela(string model);
        [OperationContract]
        List<Vozilo> GetVozilaForVlasnik(Vlasnik v);
        [OperationContract]
        List<Vozilo> GetSvaVozila();
    }

    [DataContract]
    public class Vozilo
    {
        [DataMember]
        public string Model { get; set; }
        [DataMember]
        public string Marka { get; set; }
        [DataMember]
        public string Boja { get; set; }

        public Vozilo()
        {
            Model = Marka = Boja = "Unknown";
        }
    }

    [DataContract]
    [KnownType(typeof(RegistrovanVlasnik))]
    public class Vlasnik
    {
        [DataMember]
        public string JMBG { get; set; }
        [DataMember]
        public string Ime { get; set; }
        [DataMember]
        public string Prezime { get; set; }

        public Vlasnik()
        {
            JMBG = Ime = Prezime = "Unknown";
        }
    }

    [DataContract]
    public class RegistrovanVlasnik : Vlasnik
    {
        [IgnoreDataMember]
        public List<Vozilo> Vozila { get; set; }

        public RegistrovanVlasnik()
            :base()
        {
            Vozila = new List<Vozilo>();
        }
    }
}
