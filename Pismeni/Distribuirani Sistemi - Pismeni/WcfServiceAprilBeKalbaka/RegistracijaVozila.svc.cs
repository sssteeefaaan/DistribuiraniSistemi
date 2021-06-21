using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfServiceAprilBeKalbaka
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "RegistracijaVozila" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select RegistracijaVozila.svc or RegistracijaVozila.svc.cs at the Solution Explorer and start debugging.
    public class RegistracijaVozila : IRegistracijaVozila
    {
        public static Dictionary<string, RegistrovanVlasnik> Korisnici { get; set; } = new Dictionary<string, RegistrovanVlasnik>();

        public RegistracijaVozila()
        { }

        public List<Vozilo> GetSvaVozila()
        {
            List<Vozilo> ret = new List<Vozilo>();
            foreach (KeyValuePair<string, RegistrovanVlasnik> entry in Korisnici)
                foreach (Vozilo v in entry.Value.Vozila)
                    ret.Add(v);

            return ret;
        }

        public List<Vlasnik> GetVlasniciModela(string model)
        {
            List<Vlasnik> ret = new List<Vlasnik>();

            foreach (KeyValuePair<string, RegistrovanVlasnik> entry in Korisnici)
            {
                foreach (Vozilo v in entry.Value.Vozila)
                {
                    if (model == v.Model)
                    {
                        ret.Add(entry.Value);
                        break;
                    }
                }
            }

            return ret;
        }

        public List<Vozilo> GetVozilaForVlasnik(Vlasnik vlasnik)
        {
            List<Vozilo> ret = new List<Vozilo>();

            foreach (KeyValuePair<string, RegistrovanVlasnik> entry in Korisnici)
                if(entry.Key == vlasnik.JMBG)
                    foreach (Vozilo v in entry.Value.Vozila)
                        ret.Add(v);

            return ret;
        }

        public void Register(Vozilo vo, Vlasnik vl)
        {
            if (Korisnici.ContainsKey(vl.JMBG))
                Korisnici[vl.JMBG].Vozila.Add(vo);
            else
                Korisnici.Add(vl.JMBG, new RegistrovanVlasnik()
                {
                    JMBG = vl.JMBG,
                    Ime = vl.Ime,
                    Prezime = vl.Prezime,
                    Vozila = new List<Vozilo>() { vo }
                });
        }
    }
}
