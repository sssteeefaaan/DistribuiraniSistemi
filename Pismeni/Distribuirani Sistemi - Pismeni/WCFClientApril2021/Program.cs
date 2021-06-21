using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using WCFClientApril2021.ServiceReferenceRegistracijaVozila;

namespace WCFClientApril2021
{
    public class Program : WCFClientApril2021.ServiceReferenceRegistracijaVozila.IRegVozilaCallback
    {
        static void Main(string[] args)
        {
            Program p = new Program();
            RegVozilaClient proxy = new RegVozilaClient(new InstanceContext(p));

            Console.WriteLine("Unesite podatke za registraciju (JMBG, Ime, Prezime):");
            Owner o = new Owner()
            {
                JMBG = Console.ReadLine(),
                Ime = Console.ReadLine(),
                Prezime = Console.ReadLine()
            };

            Console.WriteLine("Unesite podatke o vozilu (Marka, Model, Boja):");
            Vehicle v = new Vehicle()
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
                        v = new Vehicle()
                        {
                            Marka = Console.ReadLine(),
                            Model = Console.ReadLine(),
                            Boja = Console.ReadLine()
                        };
                        proxy.Register(v, o);

                        break;
                    case "2":
                        proxy.GetAllVehicles(o.JMBG);
                        break;
                    case "3":
                        Console.WriteLine("Unesite željeni model vozila:");
                        proxy.GetOwners(Console.ReadLine(), o.JMBG);
                        break;
                    case "4":
                        proxy.GetOwnerVehicles(o);
                        break;
                    case "5":
                        lup = false;
                        break;
                    default:
                        break;
                }
            }
        }

        public void GetAllVehiclesResponse(List<Vehicle> vehicles)
        {
            Console.WriteLine("------------------------------Get all vehicles callback------------------------------");
            vehicles.ForEach(v => Console.WriteLine($"Vehicle\n\tModel: {v.Model}\n\tMarka: {v.Marka}\n\tBoja: {v.Boja}\n"));
            Console.WriteLine("-------------------------------------------------------------------------------------");
        }

        public void GetOwnersResponse(List<Owner> owners)
        {
            Console.WriteLine("------------------------------Get owners callback------------------------------");
            owners.ForEach(o => Console.WriteLine($"Owner\n\tJMBG: {o.JMBG}\n\tIme: {o.Ime}\n\tPrezime: {o.Prezime}\n"));
            Console.WriteLine("-------------------------------------------------------------------------------");
        }


        public void GetOwnerVehiclesResponse(List<Vehicle> vehicles)
        {
            Console.WriteLine("------------------------------Get owner vehicles callback------------------------------");
            vehicles.ForEach(v => Console.WriteLine($"Vehicle\n\tModel: {v.Model}\n\tMarka: {v.Marka}\n\tBoja: {v.Boja}\n"));
            Console.WriteLine("---------------------------------------------------------------------------------------");
        }
    }
}
