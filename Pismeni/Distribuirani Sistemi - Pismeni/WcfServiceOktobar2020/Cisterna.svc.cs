using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfServiceOktobar2020
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Cisterna" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Cisterna.svc or Cisterna.svc.cs at the Solution Explorer and start debugging.
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.Single)]
    public class Cisterna : ICisterna
    {
        public /*static*/ Materijal Materijal { get; set; } = new Materijal() { Zapremina = 0, Masa = 0, Gustina = 0, Naziv = "Smeša" };
        public /*static readonly*/ int Kapacitet { get; } = 100;
        public /*static*/ List<string> Istorija { get; set; } = new List<string>();
        public /*static*/ List<ICisternaCallback> Callbacks { get; set; } = new List<ICisternaCallback>();

        public Cisterna()
        {
            
        }
        public void DodajMaterijal(Materijal m)
        {
            ICisternaCallback cb = OperationContext.Current.GetCallbackChannel<ICisternaCallback>();
            if (!Callbacks.Contains(cb))
                Callbacks.Add(cb);

            if (Materijal.Zapremina + m.Zapremina <= Kapacitet)
            {
                Materijal.Zapremina += m.Zapremina;
                Materijal.Masa += m.Gustina * m.Zapremina * 1000;
                Materijal.Gustina = Materijal.Masa / (Materijal.Zapremina * 1000);

                Istorija.Add($"[{DateTime.Now}] Dodat je materijal '{m.Naziv}' zapremine: {m.Zapremina}l i gustine: {m.Gustina} kg/m^3!");
            }

            if (Materijal.Zapremina == Kapacitet)
                Callbacks.ForEach(c => c.NotifyStanje(true));
        }

        public void IsipajMaterijal(decimal zapremina)
        {
            ICisternaCallback cb = OperationContext.Current.GetCallbackChannel<ICisternaCallback>();
            if (!Callbacks.Contains(cb))
                Callbacks.Add(cb);

            if (Materijal.Zapremina - zapremina <= 0)
            {
                Materijal.Zapremina -= zapremina;
                Materijal.Masa -= Materijal.Gustina * zapremina * 1000;

                Istorija.Add($"[{DateTime.Now}] Isipan je materijal iz cisterne zapremine: {zapremina}l!");
            }

            if (Materijal.Zapremina == 0)
                Callbacks.ForEach(c => c.NotifyStanje(false));
        }

        public List<string> Promene()
        {
            ICisternaCallback c = OperationContext.Current.GetCallbackChannel<ICisternaCallback>();
            if (!Callbacks.Contains(c))
                Callbacks.Add(c);

            return Istorija;
        }

        public Materijal TrenutnoStanje()
        {
            ICisternaCallback c = OperationContext.Current.GetCallbackChannel<ICisternaCallback>();
            if (!Callbacks.Contains(c))
                Callbacks.Add(c);

            return Materijal;
        }
    }
}
