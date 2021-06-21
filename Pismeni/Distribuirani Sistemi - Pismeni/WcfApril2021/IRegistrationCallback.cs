using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace WcfApril2021
{
    public interface IRegistrationCallback
    {
        [OperationContract(IsOneWay = true)]
        void GetOwnerVehiclesResponse(List<Vehicle> vehicles);
        [OperationContract(IsOneWay = true)]
        void GetOwnersResponse(List<Owner> owners);
        [OperationContract(IsOneWay = true)]
        void GetAllVehiclesResponse(List<Vehicle> vehicles);
    }
}
