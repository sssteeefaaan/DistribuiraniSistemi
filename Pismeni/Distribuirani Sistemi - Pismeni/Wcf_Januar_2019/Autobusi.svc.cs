using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Wcf_Januar_2019
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Autobusi" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Autobusi.svc or Autobusi.svc.cs at the Solution Explorer and start debugging.
    public class Autobusi : IAutobusi
    {
        public static Dictionary<string, Linija> Linije { get; set; } = new Dictionary<string, Linija>();
        public Autobusi()
        {
            
        }

        public void Registracija(Linija novaLinija)
        {
            if (!Linije.ContainsKey(novaLinija.Sifra))
            {
                novaLinija.Lokacije.Sort();
                Linije.Add(novaLinija.Sifra, novaLinija);
            }
        }

        public List<Linija> VratiLinijeKojeProlaze(string stanica)
        {
            List<Linija> ret = new List<Linija>();
            foreach (KeyValuePair<string, Linija> linija in Linije)
            {
                foreach (Stanica s in linija.Value.Lokacije)
                {
                    if (s.Naziv == stanica)
                    {
                        ret.Add(linija.Value);
                        break;
                    }
                }
            }

            return ret;
        }

        public List<Linija> VratiLinijeSaPodrutom(string stanica, string odrediste)
        {
            List<Linija> ret = new List<Linija>();
            foreach (KeyValuePair<string, Linija> linija in Linije)
            {
                for (int i = 0; i < linija.Value.Lokacije.Count; i++)
                {
                    if (linija.Value.Lokacije.ElementAt(i).Naziv == stanica)
                    {
                        for (int j = i + 1; j < linija.Value.Lokacije.Count; j++)
                        {
                            if (linija.Value.Lokacije.ElementAt(i).Naziv == odrediste)
                            {
                                ret.Add(linija.Value);
                                j = i = linija.Value.Lokacije.Count;
                            }
                        }
                    }
                }
            }

            return ret;
        }

        public List<Stanica> VratiStanice(string sifraLinije, string nazivStanice)
        {
            List<Stanica> stanice = new List<Stanica>();
            if (Linije.ContainsKey(sifraLinije))
            {
                int i = 0;
                while (Linije[sifraLinije].Lokacije.ElementAt(i++).Naziv != nazivStanice) ;
                while(i< Linije[sifraLinije].Lokacije.Count)
                    stanice.Add(Linije[sifraLinije].Lokacije.ElementAt(i++));
            }

            return stanice;
        }
    }
}
