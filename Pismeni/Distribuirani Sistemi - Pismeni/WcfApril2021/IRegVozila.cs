using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfApril2021
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IRegVozila" in both code and config file together.
    [ServiceContract(CallbackContract = typeof(IRegistrationCallback), SessionMode =SessionMode.Required)]
    public interface IRegVozila
    {
        [OperationContract(IsOneWay = true)]
        void Register(Vehicle v, Owner o);
        [OperationContract(IsOneWay = true)]
        void GetOwnerVehicles(Owner o);
        [OperationContract(IsOneWay = true)]
        void GetOwners(string model, string user);
        [OperationContract(IsOneWay = true)]
        void GetAllVehicles(string user);
    }

    [DataContract]
    [KnownType(typeof(RegisteredOwner))]
    public class Owner
    {
        [DataMember]
        public string Ime { get; set; }
        [DataMember]
        public string Prezime { get; set; }
        [DataMember]
        public string JMBG { get; set; }
        public Owner()
        {
            JMBG = Prezime = Ime = "UNKNOWN";
        }
    }
    [DataContract]
    public class Vehicle
    {
        [DataMember]
        public string Marka { get; set; }
        [DataMember]
        public string Model { get; set; }
        [DataMember]
        public string Boja { get; set; }

        public Vehicle()
        {
           Marka = Model = Boja = "UNKNOWN";
        }
    }

    [DataContract]
    public class RegisteredOwner : Owner
    {
        [IgnoreDataMember]
        public List<Vehicle> Vehicles { get; set; }
        [IgnoreDataMember]
        public IRegistrationCallback Callback { get; set; }

        public RegisteredOwner()
            : base()
        {
            Vehicles = new List<Vehicle>();
        }
    }
}
