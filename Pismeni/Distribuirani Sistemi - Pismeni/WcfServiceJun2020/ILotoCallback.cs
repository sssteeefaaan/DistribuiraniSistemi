using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace WcfServiceJun2020
{
    public interface ILotoCallback
    {
        [OperationContract(IsOneWay = true)]
        void ProsledjivanjeBroja(int broj);
        [OperationContract(IsOneWay = true)]
        void KrajIzvlacenja(Rezultat rezultat);
        [OperationContract(IsOneWay = true)]
        void ProsledjivanjeCestitke(Cestitka cestitka);
    }
}
