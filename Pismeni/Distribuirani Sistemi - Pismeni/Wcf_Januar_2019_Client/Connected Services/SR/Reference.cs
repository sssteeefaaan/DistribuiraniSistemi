//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Wcf_Januar_2019_Client.SR {
    using System.Runtime.Serialization;
    using System;
    
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Runtime.Serialization", "4.0.0.0")]
    [System.Runtime.Serialization.DataContractAttribute(Name="Linija", Namespace="http://schemas.datacontract.org/2004/07/Wcf_Januar_2019")]
    [System.SerializableAttribute()]
    public partial class Linija : object, System.Runtime.Serialization.IExtensibleDataObject, System.ComponentModel.INotifyPropertyChanged {
        
        [System.NonSerializedAttribute()]
        private System.Runtime.Serialization.ExtensionDataObject extensionDataField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Stanica> LokacijeField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private string SifraField;
        
        [global::System.ComponentModel.BrowsableAttribute(false)]
        public System.Runtime.Serialization.ExtensionDataObject ExtensionData {
            get {
                return this.extensionDataField;
            }
            set {
                this.extensionDataField = value;
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Stanica> Lokacije {
            get {
                return this.LokacijeField;
            }
            set {
                if ((object.ReferenceEquals(this.LokacijeField, value) != true)) {
                    this.LokacijeField = value;
                    this.RaisePropertyChanged("Lokacije");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public string Sifra {
            get {
                return this.SifraField;
            }
            set {
                if ((object.ReferenceEquals(this.SifraField, value) != true)) {
                    this.SifraField = value;
                    this.RaisePropertyChanged("Sifra");
                }
            }
        }
        
        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged(string propertyName) {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null)) {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Runtime.Serialization", "4.0.0.0")]
    [System.Runtime.Serialization.DataContractAttribute(Name="Stanica", Namespace="http://schemas.datacontract.org/2004/07/Wcf_Januar_2019")]
    [System.SerializableAttribute()]
    public partial class Stanica : object, System.Runtime.Serialization.IExtensibleDataObject, System.ComponentModel.INotifyPropertyChanged {
        
        [System.NonSerializedAttribute()]
        private System.Runtime.Serialization.ExtensionDataObject extensionDataField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private string NazivField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private System.Nullable<System.DateTime> VremePolaskaField;
        
        [global::System.ComponentModel.BrowsableAttribute(false)]
        public System.Runtime.Serialization.ExtensionDataObject ExtensionData {
            get {
                return this.extensionDataField;
            }
            set {
                this.extensionDataField = value;
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public string Naziv {
            get {
                return this.NazivField;
            }
            set {
                if ((object.ReferenceEquals(this.NazivField, value) != true)) {
                    this.NazivField = value;
                    this.RaisePropertyChanged("Naziv");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public System.Nullable<System.DateTime> VremePolaska {
            get {
                return this.VremePolaskaField;
            }
            set {
                if ((this.VremePolaskaField.Equals(value) != true)) {
                    this.VremePolaskaField = value;
                    this.RaisePropertyChanged("VremePolaska");
                }
            }
        }
        
        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged(string propertyName) {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null)) {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(ConfigurationName="SR.IAutobusi")]
    public interface IAutobusi {
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IAutobusi/Registracija", ReplyAction="http://tempuri.org/IAutobusi/RegistracijaResponse")]
        void Registracija(Wcf_Januar_2019_Client.SR.Linija novaLinija);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IAutobusi/Registracija", ReplyAction="http://tempuri.org/IAutobusi/RegistracijaResponse")]
        System.Threading.Tasks.Task RegistracijaAsync(Wcf_Januar_2019_Client.SR.Linija novaLinija);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IAutobusi/VratiLinijeKojeProlaze", ReplyAction="http://tempuri.org/IAutobusi/VratiLinijeKojeProlazeResponse")]
        System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Linija> VratiLinijeKojeProlaze(string stanica);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IAutobusi/VratiLinijeKojeProlaze", ReplyAction="http://tempuri.org/IAutobusi/VratiLinijeKojeProlazeResponse")]
        System.Threading.Tasks.Task<System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Linija>> VratiLinijeKojeProlazeAsync(string stanica);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IAutobusi/VratiLinijeSaPodrutom", ReplyAction="http://tempuri.org/IAutobusi/VratiLinijeSaPodrutomResponse")]
        System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Linija> VratiLinijeSaPodrutom(string stanica, string odrediste);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IAutobusi/VratiLinijeSaPodrutom", ReplyAction="http://tempuri.org/IAutobusi/VratiLinijeSaPodrutomResponse")]
        System.Threading.Tasks.Task<System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Linija>> VratiLinijeSaPodrutomAsync(string stanica, string odrediste);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IAutobusi/VratiStanice", ReplyAction="http://tempuri.org/IAutobusi/VratiStaniceResponse")]
        System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Stanica> VratiStanice(string sifraLinije, string nazivStanice);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IAutobusi/VratiStanice", ReplyAction="http://tempuri.org/IAutobusi/VratiStaniceResponse")]
        System.Threading.Tasks.Task<System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Stanica>> VratiStaniceAsync(string sifraLinije, string nazivStanice);
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface IAutobusiChannel : Wcf_Januar_2019_Client.SR.IAutobusi, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class AutobusiClient : System.ServiceModel.ClientBase<Wcf_Januar_2019_Client.SR.IAutobusi>, Wcf_Januar_2019_Client.SR.IAutobusi {
        
        public AutobusiClient() {
        }
        
        public AutobusiClient(string endpointConfigurationName) : 
                base(endpointConfigurationName) {
        }
        
        public AutobusiClient(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public AutobusiClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public AutobusiClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress) {
        }
        
        public void Registracija(Wcf_Januar_2019_Client.SR.Linija novaLinija) {
            base.Channel.Registracija(novaLinija);
        }
        
        public System.Threading.Tasks.Task RegistracijaAsync(Wcf_Januar_2019_Client.SR.Linija novaLinija) {
            return base.Channel.RegistracijaAsync(novaLinija);
        }
        
        public System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Linija> VratiLinijeKojeProlaze(string stanica) {
            return base.Channel.VratiLinijeKojeProlaze(stanica);
        }
        
        public System.Threading.Tasks.Task<System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Linija>> VratiLinijeKojeProlazeAsync(string stanica) {
            return base.Channel.VratiLinijeKojeProlazeAsync(stanica);
        }
        
        public System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Linija> VratiLinijeSaPodrutom(string stanica, string odrediste) {
            return base.Channel.VratiLinijeSaPodrutom(stanica, odrediste);
        }
        
        public System.Threading.Tasks.Task<System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Linija>> VratiLinijeSaPodrutomAsync(string stanica, string odrediste) {
            return base.Channel.VratiLinijeSaPodrutomAsync(stanica, odrediste);
        }
        
        public System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Stanica> VratiStanice(string sifraLinije, string nazivStanice) {
            return base.Channel.VratiStanice(sifraLinije, nazivStanice);
        }
        
        public System.Threading.Tasks.Task<System.Collections.Generic.List<Wcf_Januar_2019_Client.SR.Stanica>> VratiStaniceAsync(string sifraLinije, string nazivStanice) {
            return base.Channel.VratiStaniceAsync(sifraLinije, nazivStanice);
        }
    }
}
