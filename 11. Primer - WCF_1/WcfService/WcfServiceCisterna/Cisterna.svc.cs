using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfServiceCisterna
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Cisterna" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Cisterna.svc or Cisterna.svc.cs at the Solution Explorer and start debugging.
    // [ServiceBehavior(InstanceContextMode =InstanceContextMode.PerCall)]
    public class Cisterna : ICisterna
    {
        public void DodajMaterijal(Materijal m)
        {
            Repository.Instance.Materijal.Masa += m.Masa;
            Repository.Instance.Materijal.Zapremina += m.Masa / m.Gustina * 1000;
            Repository.Instance.Materijal.Gustina = Repository.Instance.Materijal.Masa / Repository.Instance.Materijal.Zapremina;

            Repository.Instance.Promene.Add("[" + DateTime.Now.ToShortDateString() + " " + DateTime.Now.ToShortTimeString() + "] Dodat materijal '" + m.Naziv + "' mase: " + m.Masa.ToString() + "kg i gustine: " + m.Gustina.ToString() + "kg/m^3");
        }
        public void Isipaj(decimal zapremina) 
        {
            if (Repository.Instance.Materijal.Zapremina - zapremina >= 0)
            {
                Repository.Instance.Materijal.Zapremina -= zapremina;
                Repository.Instance.Materijal.Masa = Repository.Instance.Materijal.Zapremina * Repository.Instance.Materijal.Gustina;
                Repository.Instance.Promene.Add("[" + DateTime.Now.ToShortDateString() + " " + DateTime.Now.ToShortTimeString() + "] Uklonjena zapremina " + zapremina.ToString() + "l");
            }
            else
                Repository.Instance.Materijal.Zapremina = 0;
        }
        public List<string> PreuzmiIzmene()
        {
            return Repository.Instance.Promene;
        }

        public Materijal PreuzmiMaterijal()
        {
            return Repository.Instance.Materijal;
        }
    }
}
