using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfServiceJun2020
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "ILoto" in both code and config file together.
    [ServiceContract(CallbackContract = typeof(ILotoCallback), SessionMode = SessionMode.Required)]
    public interface ILoto
    {
        [OperationContract]
        int DodajKombinaciju(string nick, Kombinacija kombinacija);
        [OperationContract]
        int UkloniKombinaciju(string nick, long kombinacijaID);
        [OperationContract]
        void KreniIzvlacenje(string sifra, int value);
    }
    [DataContract]
    public class Kombinacija
    {
        [DataMember]
        public List<int> Vrednosti { get; set; }
        [DataMember]
        public long ID { get; set; }

        public Kombinacija()
        {
            Vrednosti = new List<int>();
            ID = 0;
        }
    }
    [DataContract]
    public class Cestitka
    {
        [DataMember]
        public string Sadrzaj { get; set; }
        [DataMember]
        public Kombinacija Pobednicka { get; set; }

        public Cestitka()
        {
            Sadrzaj = "Čestitamo, Vaša kombinacija je pobednička!";
            Pobednicka = new Kombinacija();
        }
    }
    [DataContract]
    public class Rezultat
    {
        [DataMember]
        public int Petice { get; set; }
        [DataMember]
        public int Sestice { get; set; }
        [DataMember]
        public int Sedmice { get; set; }

        public Rezultat()
        {
            Petice = Sestice = Sedmice = 0;
        }
    }
    public class Korisnik
    {
        public string Nickname { get; set; }
        public Cestitka Cestitka { get; set; }
        public List<Kombinacija> Kombinacije { get; set; }
        public ILotoCallback Callback { get; set; }

        public Korisnik()
        {
            Nickname = "";
            Cestitka = null;
            Kombinacije = new List<Kombinacija>();
            Callback = null;
        }
    }
}
