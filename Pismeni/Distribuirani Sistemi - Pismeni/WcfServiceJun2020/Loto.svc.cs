using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfServiceJun2020
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Loto" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Loto.svc or Loto.svc.cs at the Solution Explorer and start debugging.
    [ServiceBehavior(InstanceContextMode =InstanceContextMode.PerSession)]
    public class Loto : ILoto
    {
        public static Dictionary<string, Korisnik> Ucesnici { get; set; } = new Dictionary<string, Korisnik>();
        public static Rezultat Rezultat { get; set; } = new Rezultat();
        public static Kombinacija Izvlacenje { get; set; } = new Kombinacija();

        public Loto()
        { }
        public int DodajKombinaciju(string nick, Kombinacija kombinacija)
        {
            if (Izvlacenje.Vrednosti.Count == 7)
                return -2; // Kraj lutrije

            if (Ucesnici.ContainsKey(nick))
            {
                if (Ucesnici[nick].Kombinacije.FindIndex(k => k.ID == kombinacija.ID) == -1)
                    Ucesnici[nick].Kombinacije.Add(kombinacija);
                else
                    return -1; // Postoji isti id
            }
            else
                Ucesnici.Add(nick, new Korisnik()
                {
                    Nickname = nick,
                    Kombinacije = new List<Kombinacija>() { kombinacija },
                    Cestitka = null,
                    Callback = OperationContext.Current.GetCallbackChannel<ILotoCallback>()
                });

            return 0; // Uspešno dodavanje
        }

        public void KreniIzvlacenje(string sifra, int value)
        {
            if (sifra == "ADMINISTRATOR")
            {
                if (Izvlacenje.Vrednosti.Count != 7)
                {
                    Random rand = new Random();
                    int number = value;//rand.Next(1, 40);
                    foreach (KeyValuePair<string, Korisnik> entry in Ucesnici)
                        entry.Value.Callback.ProsledjivanjeBroja(number);
                    Izvlacenje.Vrednosti.Add(number);
                }
                else
                {
                    foreach (KeyValuePair<string, Korisnik> entry in Ucesnici)
                    {
                        foreach (Kombinacija k in entry.Value.Kombinacije)
                        {
                            int pogodjenih = 0;
                            for (int i = 0; i < 7; i++)
                                if (Izvlacenje.Vrednosti.Contains(k.Vrednosti[i]))
                                    pogodjenih++;
                            switch (pogodjenih)
                            {
                                case 5:
                                    Rezultat.Petice++;
                                    break;
                                case 6:
                                    Rezultat.Sestice++;
                                    break;
                                case 7:
                                    Rezultat.Sedmice++;
                                    entry.Value.Cestitka = new Cestitka()
                                    {
                                        Pobednicka = k,
                                        Sadrzaj = $"Čestitamo {entry.Value.Nickname} Vaša kombinacija {k.ID} je pobednička!"
                                    };
                                    break;
                            }
                        }
                    }
                    foreach (KeyValuePair<string, Korisnik> entry in Ucesnici)
                    {
                        entry.Value.Callback.KrajIzvlacenja(Rezultat);
                        if (entry.Value.Cestitka != null)
                            entry.Value.Callback.ProsledjivanjeCestitke(entry.Value.Cestitka);
                    }
                }
            }
        }

        public int UkloniKombinaciju(string nick, long kombinacijaID)
        {
            if (Izvlacenje.Vrednosti.Count == 7)
                return -2; // Kraj lutrije

            if (!Ucesnici.ContainsKey(nick))
                return -1; // Korsinik sa tim nick-om ne postoji!
            else if (Ucesnici[nick].Kombinacije.Remove(Ucesnici[nick].Kombinacije.Find(k => k.ID == kombinacijaID)))
                return 0; // Uspešno uklonjena kombinacija
            else
                return -3; // Ne postoji kombinacija sa tim ID-om
        }
    }
}
