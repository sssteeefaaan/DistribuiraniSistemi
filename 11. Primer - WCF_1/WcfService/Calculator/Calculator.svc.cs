using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Calculator
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Calculator" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Calculator.svc or Calculator.svc.cs at the Solution Explorer and start debugging.
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.PerSession)]
    public class Calculator : ICalculator
    {
        private static Rezultat Akumulator { get; set; } = new Rezultat();
        private static Dictionary<string, ICalculatorCallback> callbacks = new Dictionary<string, ICalculatorCallback>();

        //protected ICalculatorCallback Callback
        //{
        //    get
        //    {
        //        return OperationContext.Current.GetCallbackChannel<ICalculatorCallback>();
        //    }
        //}

        public Calculator()
        {}

        public void Obrisi()
        {
            Akumulator.Value = 0;
            Akumulator.History = "";
            foreach (KeyValuePair<string, ICalculatorCallback> entry in callbacks)
                entry.Value.Rezultat(Akumulator);
        }

        public void Oduzmi(decimal number)
        {
            Akumulator.Value -= number;
            Akumulator.History = Akumulator.History != "" ? Akumulator.History + " - " + number.ToString() : number.ToString();
            foreach (KeyValuePair<string, ICalculatorCallback> entry in callbacks)
                entry.Value.Rezultat(Akumulator);
        }

        public void Podeli(decimal number)
        {
            if (number != 0)
            {
                Akumulator.Value /= number;
                Akumulator.History = Akumulator.History != "" ? Akumulator.History + " / " + number.ToString() : number.ToString();
                foreach (KeyValuePair<string, ICalculatorCallback> entry in callbacks)
                    entry.Value.Rezultat(Akumulator);
            }
            else
            {
                Akumulator.Value = 0;
                Akumulator.History = "Error";
                foreach (KeyValuePair<string, ICalculatorCallback> entry in callbacks)
                    entry.Value.Rezultat(Akumulator);
            }
        }

        public void Pomnozi(decimal number)
        {
            Akumulator.Value *= number;
            Akumulator.History = Akumulator.History != "" ? Akumulator.History + " * " + number.ToString() : number.ToString();
            foreach (KeyValuePair<string, ICalculatorCallback> entry in callbacks)
                entry.Value.Rezultat(Akumulator);
        }

        public void Saberi(decimal number)
        {
            Akumulator.Value += number;
            Akumulator.History = Akumulator.History != "" ? Akumulator.History + " + " + number.ToString() : number.ToString();
            foreach (KeyValuePair<string, ICalculatorCallback> entry in callbacks)
                entry.Value.Rezultat(Akumulator);
        }

        public void Register(string ID)
        {
            callbacks.Add(ID, OperationContext.Current.GetCallbackChannel<ICalculatorCallback>());
        }
    }

    [DataContract]
    public class Rezultat
    {
        [DataMember]
        public decimal Value { get; set; }
        [DataMember]
        public string History { get; set; }

        public Rezultat()
        {
            Value = 0;
            History = "";
        }
    }
}
