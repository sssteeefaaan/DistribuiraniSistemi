using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Wcf_Januar_2019_Client.SR;

namespace Wcf_Januar_2019_Client
{
    class Program
    {
        static void Main(string[] args)
        {
            AutobusiClient endpoint = new AutobusiClient();

            string option,
                stanicaNaziv,
                odrediste,
                sifraLinije;
            Linija linija = null;
            int sat, minut;
            bool loop = true;
            while (loop)
            {
                Console.WriteLine("Glavni menu");
                Console.WriteLine("\t1 - Registruj Liniju");
                Console.WriteLine("\t2 - Vrati linije sa stanicom");
                Console.WriteLine("\t3 - Vrati linije sa podrutom");
                Console.WriteLine("\t4 - Vrati predstojece stanice za liniju i stanicu");
                Console.WriteLine("\t5 - Exit");

                option = Console.ReadLine();
                switch (option)
                {
                    case ("1"):
                        Console.WriteLine("Izabrana opcija 'Registruj liniju'");

                        Console.WriteLine("Unesite sifru linije:");
                        sifraLinije = Console.ReadLine().Trim();

                        linija = new Linija()
                        {
                            Sifra = sifraLinije,
                            Lokacije = new List<Stanica>()
                        };

                        while (true)
                        {
                            Console.WriteLine("Unesite naziv lokacije ili 'Kraj' za zavrsetak unosa");
                            stanicaNaziv = Console.ReadLine().Trim();
                            if (stanicaNaziv.ToLower() == "kraj")
                                break;

                            Console.WriteLine("Unesite sat prolaska");
                            sat = int.Parse(Console.ReadLine());
                            Console.WriteLine("Unesite minut prolaska");
                            minut = int.Parse(Console.ReadLine());
                            linija.Lokacije.Add(new Stanica()
                            {
                                Naziv = stanicaNaziv,
                                VremePolaska = new DateTime(2021, 07, 06, sat, minut, 0)
                            });
                        }

                        endpoint.Registracija(linija);
                        
                        break;
                    case ("2"):
                        Console.WriteLine("Izabrana opcija 'Vrati linije sa stanicom'");
                        Console.WriteLine("Unesite naziv stanice:");

                        stanicaNaziv = Console.ReadLine().Trim();
                        Console.WriteLine("Lista svih linija koje prolaze kroz stanicu:");
                        endpoint
                            .VratiLinijeKojeProlaze(stanicaNaziv)
                            .ForEach(lokacija => Console.WriteLine(lokacija.Sifra));

                        break;
                    case ("3"):
                        Console.WriteLine("Izabrana opcija 'Vrati linije sa podrutom'");
                        Console.WriteLine("Unesite naziv stanice:");

                        stanicaNaziv = Console.ReadLine().Trim();
                        odrediste = Console.ReadLine().Trim();

                        Console.WriteLine("Lista svih lokacija koje prolaze podrutom: ");
                        endpoint
                            .VratiLinijeSaPodrutom(stanicaNaziv, odrediste)
                            .ForEach(lokacija => Console.WriteLine(lokacija.Sifra));
                        break;
                    case ("4"):
                        Console.WriteLine("Izabrana opcija 'Vrati predstojece stanice za liniju i stanicu'");

                        Console.WriteLine("Unesite sifru linije:");
                        sifraLinije = Console.ReadLine().Trim();

                        Console.WriteLine("Unesite naziv stanice:");
                        stanicaNaziv = Console.ReadLine().Trim();

                        Console.WriteLine("Predstojece stanice:");
                        endpoint
                            .VratiStanice(sifraLinije, stanicaNaziv)
                            .ForEach(stanica => Console.WriteLine(stanica.Naziv + " u " + ((DateTime)stanica.VremePolaska).ToShortTimeString()));

                        break;
                    case ("5"):
                        Console.WriteLine("Izabrana opcija 'Kraj'");
                        loop = false;
                        break;
                    default:
                        Console.WriteLine("Unesite broj pridruzen opciji!");
                        break;
                }
            }

            endpoint.Close();
        }
    }
}
