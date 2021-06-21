using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using ConsoleAppLoto2020.SRLoto;

namespace ConsoleAppLoto2020
{
    public class Program:ILotoCallback
    {
        static void Main(string[] args)
        {
            Program p = new Program();
            LotoClient proxy = new LotoClient(new InstanceContext(p));
            Random rand = new Random();

            Console.WriteLine("Unesite svoj nickname: ");
            string nick = Console.ReadLine();

            string innumb;
            if (nick == "ADMINISTRATOR")
            {
                int i = 0;
                while (i < 7)
                {
                    Console.WriteLine("ADMINISTRATOR Menu:");
                    Console.WriteLine("\t1 - Izvucite novi broj");

                    if (Console.ReadLine() == "1")
                    {
                        i++;
                        proxy.KreniIzvlacenje(nick, int.Parse(Console.ReadLine()));//rand.Next(1, 40));
                    }
                }
                proxy.KreniIzvlacenje(nick, 0);
            }
            else
            {
                bool lup = true;
                Kombinacija k;
                while (lup)
                {
                    Console.WriteLine("Glavni meni");
                    Console.WriteLine("\t1 - Uplatite novu kombinaciju");
                    Console.WriteLine("\t2 - Ukloni kombinaciju");
                    Console.WriteLine("\t3 - Kraj");

                    switch (Console.ReadLine())
                    {
                        case "1":
                            k = new Kombinacija();
                            k.Vrednosti = new List<int>();
                            Console.WriteLine("Unesite 7 različitih brojeva [1-40]");

                            for (int i = 0; i < 7; i++)
                                k.Vrednosti.Add(int.Parse(Console.ReadLine()));

                            k.ID = DateTime.Now.Ticks;
                           switch(proxy.DodajKombinaciju(nick, k))
                            {
                                case 0:
                                    Console.WriteLine("Uspešno dodavanje kombinacije!");
                                    Console.WriteLine($"ID kombinacije: {k.ID}");
                                    break;
                                case -1:
                                    Console.WriteLine("Neuspešno dodavanje kombinacije!");
                                    break;
                                case -2:
                                    Console.WriteLine("Lutrija je završena!");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "2":

                            Console.WriteLine("Unesite ID kombinacije koju želite ukloniti:");

                            switch(proxy.UkloniKombinaciju(nick, long.Parse(Console.ReadLine())))
                            {
                                case 0:
                                    Console.WriteLine("Uspešno uklanjanje kombinacije!");
                                    break;
                                case -1:
                                    Console.WriteLine($"Korisnik sa nick-om {nick} nije registrovan!");
                                    break;
                                case -2:
                                    Console.WriteLine("Lutrija je završena!");
                                    break;
                                case -3:
                                    Console.WriteLine("Ne postoji kombinacija sa tim ID-om!");
                                    break;
                                default:
                                    break;
                            }
                                
                            break;
                        case "3":
                            lup = false;
                            break;
                        default:
                            break;
                    }
                }
            }

            proxy.Close();
        }

        public void KrajIzvlacenja(Rezultat rezultat)
        {
            Console.WriteLine("--------------------------------Izvlačenje je završeno!--------------------------------");
            Console.WriteLine("Izvučeno je:");
            Console.WriteLine("--------------------------------Petica: " + rezultat.Petice);
            Console.WriteLine("--------------------------------Šestica: " + rezultat.Sestice);
            Console.WriteLine("--------------------------------Sedmica: " + rezultat.Sedmice);
        }

        public void ProsledjivanjeBroja(int broj)
        {
            Console.WriteLine("--------------------------------Izvučen broj: " + broj);
        }

        public void ProsledjivanjeCestitke(Cestitka cestitka)
        {
            Console.WriteLine("--------------------------------ČESTITAMO!!!!!!!--------------------------------");
            Console.WriteLine(cestitka.Sadrzaj);
            cestitka.Pobednicka.Vrednosti.ForEach(v => Console.Write(v + " "));
            Console.WriteLine();
        }
    }
}
