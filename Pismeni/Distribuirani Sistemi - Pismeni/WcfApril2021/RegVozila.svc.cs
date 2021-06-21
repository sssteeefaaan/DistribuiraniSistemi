using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfApril2021
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "RegVozila" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select RegVozila.svc or RegVozila.svc.cs at the Solution Explorer and start debugging.
   [ServiceBehavior(InstanceContextMode =InstanceContextMode.Single)]
    public class RegVozila : IRegVozila
    {
        public Dictionary<string, RegisteredOwner> Owners { get; set; }
        public RegVozila()
        {
            Owners = new Dictionary<string, RegisteredOwner>();
        }
        public void GetAllVehicles(string user)
        {
            if (!Owners.ContainsKey(user))
                return;

            List<Vehicle> vehicles = new List<Vehicle>();
            foreach (KeyValuePair<string, RegisteredOwner> entry in Owners)
                entry.Value.Vehicles.ForEach(v => vehicles.Add(v));

            Owners[user].Callback.GetAllVehiclesResponse(vehicles);
        }

        public void GetOwners(string model, string user)
        {
            if (!Owners.ContainsKey(user))
                return;

            List<Owner> owners = new List<Owner>();
            foreach (KeyValuePair<string, RegisteredOwner> entry in Owners)
            {
                foreach(Vehicle v in entry.Value.Vehicles)
                {
                    if (v.Model == model)
                    {
                        owners.Add(entry.Value);
                        // Mora da se naznači da se dešava nasleđivanje da bi radilo ovako
                        // Ide [KnownType(Izvedena)] kao dekorator osnovne IDFK why, ali eto

                        // u suprotnom mora da se napravi novi objekat
                        // owners.Add(new Owner() {
                        //    JMBG = entry.Value.JMBG,
                        //    Ime = entry.Value.Ime,
                        //    Prezime = entry.Value.Prezime
                        //});
                        break;
                    }
                }
            }

            Owners[user].Callback.GetOwnersResponse(owners);
        }

        public void GetOwnerVehicles(Owner o)
        {
            if (!Owners.ContainsKey(o.JMBG))
                return;

            Owners[o.JMBG].Callback.GetOwnerVehiclesResponse(Owners[o.JMBG].Vehicles);
        }

        public void Register(Vehicle v, Owner o)
        {
            if (!Owners.ContainsKey(o.JMBG))
            {
                Owners.Add(o.JMBG, new RegisteredOwner()
                {
                    JMBG = o.JMBG,
                    Ime = o.Ime,
                    Prezime = o.Prezime,
                    Vehicles = new List<Vehicle>() { v },
                    Callback = OperationContext.Current.GetCallbackChannel<IRegistrationCallback>()
                });
            }
            else if(!Owners[o.JMBG].Vehicles.Contains(v))
                Owners[o.JMBG].Vehicles.Add(v);
        }
    }
}
