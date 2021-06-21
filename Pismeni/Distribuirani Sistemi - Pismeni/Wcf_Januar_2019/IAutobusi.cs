using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Wcf_Januar_2019
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IAutobusi" in both code and config file together.
    [ServiceContract]
    public interface IAutobusi
    {
        [OperationContract]
        void Registracija(Linija novaLinija);
        [OperationContract]
        List<Linija> VratiLinijeKojeProlaze(string stanica);
        [OperationContract]
        List<Linija> VratiLinijeSaPodrutom(string stanica, string odrediste);
        [OperationContract]
        List<Stanica> VratiStanice(string sifraLinije, string nazivStanice);
    }

    [DataContract]
    public class Linija
    {
        [DataMember]
        public string Sifra { get; set; }
        [DataMember]
        public List<Stanica> Lokacije { get; set; }

        public Linija(string sifra, List<Stanica> lokacije = null)
        {
            Sifra = sifra;
            Lokacije = lokacije != null ? lokacije : new List<Stanica>();
        }
    }

    [DataContract]
    public class Stanica : IComparable<Stanica>
    {
        [DataMember]
        public string Naziv { get; set; }
        [DataMember]
        public DateTime? VremePolaska { get; set; }

        public Stanica(string naziv = "", DateTime? vremePolaska = null)
        {
            Naziv = naziv;
            VremePolaska = vremePolaska;
        }

        public int CompareTo(Stanica other)
        {
            if (VremePolaska == other.VremePolaska)
                return Naziv.CompareTo(other.Naziv);
            return (int)VremePolaska?.CompareTo((DateTime)other.VremePolaska);
        }
    }
}
