using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WcfServiceCisterna
{
    public class Repository
    {
        public static Repository Instance
        {
            get
            {
                lock (_lock)
                {
                    if (_instance == null)
                        _instance = new Repository();
                    return _instance;
                }
            }
        }
        private static object _lock = new object();
        private static Repository _instance;
        public Materijal Materijal { get; set; }
        public List<String> Promene { get; set; }

        private Repository()
        {
            Materijal = new Materijal()
            {
                Masa = 0,
                Gustina = 0,
                Zapremina = 0,
                Naziv = "Smeša"
            };
            Promene = new List<String>();
        }
    }
}