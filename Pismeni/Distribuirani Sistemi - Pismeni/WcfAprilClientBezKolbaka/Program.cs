using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WcfAprilClientBezKolbaka.SRRegistracijaVozila;

namespace WcfAprilClientBezKolbaka
{
    class Program
    {
        static void Main(string[] args)
        {
            RegistracijaVozilaClient proxy = new RegistracijaVozilaClient();

            Console.WriteLine("Unesite podatke za registraciju (JMBG, Ime, Prezime):");
            Vlasnik o = new Vlasnik()
            {
                JMBG = Console.ReadLine(),
                Ime = Console.ReadLine(),
                Prezime = Console.ReadLine()
            };

            Console.WriteLine("Unesite podatke o vozilu (Marka, Model, Boja):");
            Vozilo v = new Vozilo()
            {
                Marka = Console.ReadLine(),
                Model = Console.ReadLine(),
                Boja = Console.ReadLine()
            };

            proxy.Register(v, o);

            string input;
            bool lup = true;
            while (lup)
            {
                Console.WriteLine("Glavni meni");
                Console.WriteLine("1 - Registrujte novo vozilo");
                Console.WriteLine("2 - Vratite sva vozila");
                Console.WriteLine("3 - Vratite korisnike sa željenim modelom vozila");
                Console.WriteLine("4 - Vratite vozila prosleđenog korisnika");
                Console.WriteLine("5 - Kraj");

                switch (input = Console.ReadLine())
                {
                    case "1":
                        Console.WriteLine("Unesite podatke o novom vozilu (Marka, Model, Boja):");
                        v = new Vozilo()
                        {
                            Marka = Console.ReadLine(),
                            Model = Console.ReadLine(),
                            Boja = Console.ReadLine()
                        };
                        proxy.Register(v, o);

                        break;
                    case "2":
                        proxy.GetSvaVozila()
                            .ForEach(vozilo => Console.WriteLine($"Vozilo:\n" +
                            $"\tMarka: {vozilo.Marka}\n" +
                            $"\tModel: {vozilo.Model}\n" +
                            $"\tBoja: {vozilo.Boja}\n"));
                        break;
                    case "3":
                        Console.WriteLine("Unesite željeni model vozila:");
                        proxy.GetVlasniciModela(Console.ReadLine())
                            .ForEach(vlasnik => Console.WriteLine($"Vlasnik:\n" +
                            $"\tJMBG: {vlasnik.JMBG}\n" +
                            $"\tIme: {vlasnik.Ime}\n" +
                            $"\tPrezime: {vlasnik.Prezime}\n"));
                        break;
                    case "4":
                        proxy.GetVozilaForVlasnik(o)
                            .ForEach(vozilo => Console.WriteLine($"Vozilo:\n" +
                            $"\tMarka: {vozilo.Marka}\n" +
                            $"\tModel: {vozilo.Model}\n" +
                            $"\tBoja: {vozilo.Boja}\n"));
                        break;
                    case "5":
                        lup = false;
                        break;
                    default:
                        break;
                }
            }
        }

    }
}
