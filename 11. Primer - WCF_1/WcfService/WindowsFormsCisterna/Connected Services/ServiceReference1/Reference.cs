//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace WindowsFormsCisterna.ServiceReference1 {
    using System.Runtime.Serialization;
    using System;
    
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Runtime.Serialization", "4.0.0.0")]
    [System.Runtime.Serialization.DataContractAttribute(Name="Materijal", Namespace="http://schemas.datacontract.org/2004/07/WcfServiceCisterna")]
    [System.SerializableAttribute()]
    public partial class Materijal : object, System.Runtime.Serialization.IExtensibleDataObject, System.ComponentModel.INotifyPropertyChanged {
        
        [System.NonSerializedAttribute()]
        private System.Runtime.Serialization.ExtensionDataObject extensionDataField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private decimal GustinaField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private decimal MasaField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private string NazivField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private decimal ZapreminaField;
        
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
        public decimal Gustina {
            get {
                return this.GustinaField;
            }
            set {
                if ((this.GustinaField.Equals(value) != true)) {
                    this.GustinaField = value;
                    this.RaisePropertyChanged("Gustina");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public decimal Masa {
            get {
                return this.MasaField;
            }
            set {
                if ((this.MasaField.Equals(value) != true)) {
                    this.MasaField = value;
                    this.RaisePropertyChanged("Masa");
                }
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
        public decimal Zapremina {
            get {
                return this.ZapreminaField;
            }
            set {
                if ((this.ZapreminaField.Equals(value) != true)) {
                    this.ZapreminaField = value;
                    this.RaisePropertyChanged("Zapremina");
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
    [System.ServiceModel.ServiceContractAttribute(ConfigurationName="ServiceReference1.ICisterna")]
    public interface ICisterna {
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/ICisterna/DodajMaterijal", ReplyAction="http://tempuri.org/ICisterna/DodajMaterijalResponse")]
        void DodajMaterijal(WindowsFormsCisterna.ServiceReference1.Materijal m);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/ICisterna/DodajMaterijal", ReplyAction="http://tempuri.org/ICisterna/DodajMaterijalResponse")]
        System.Threading.Tasks.Task DodajMaterijalAsync(WindowsFormsCisterna.ServiceReference1.Materijal m);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/ICisterna/Isipaj", ReplyAction="http://tempuri.org/ICisterna/IsipajResponse")]
        void Isipaj(decimal Zapremina);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/ICisterna/Isipaj", ReplyAction="http://tempuri.org/ICisterna/IsipajResponse")]
        System.Threading.Tasks.Task IsipajAsync(decimal Zapremina);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/ICisterna/PreuzmiMaterijal", ReplyAction="http://tempuri.org/ICisterna/PreuzmiMaterijalResponse")]
        WindowsFormsCisterna.ServiceReference1.Materijal PreuzmiMaterijal();
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/ICisterna/PreuzmiMaterijal", ReplyAction="http://tempuri.org/ICisterna/PreuzmiMaterijalResponse")]
        System.Threading.Tasks.Task<WindowsFormsCisterna.ServiceReference1.Materijal> PreuzmiMaterijalAsync();
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/ICisterna/PreuzmiIzmene", ReplyAction="http://tempuri.org/ICisterna/PreuzmiIzmeneResponse")]
        System.Collections.Generic.List<string> PreuzmiIzmene();
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/ICisterna/PreuzmiIzmene", ReplyAction="http://tempuri.org/ICisterna/PreuzmiIzmeneResponse")]
        System.Threading.Tasks.Task<System.Collections.Generic.List<string>> PreuzmiIzmeneAsync();
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface ICisternaChannel : WindowsFormsCisterna.ServiceReference1.ICisterna, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class CisternaClient : System.ServiceModel.ClientBase<WindowsFormsCisterna.ServiceReference1.ICisterna>, WindowsFormsCisterna.ServiceReference1.ICisterna {
        
        public CisternaClient() {
        }
        
        public CisternaClient(string endpointConfigurationName) : 
                base(endpointConfigurationName) {
        }
        
        public CisternaClient(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public CisternaClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public CisternaClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress) {
        }
        
        public void DodajMaterijal(WindowsFormsCisterna.ServiceReference1.Materijal m) {
            base.Channel.DodajMaterijal(m);
        }
        
        public System.Threading.Tasks.Task DodajMaterijalAsync(WindowsFormsCisterna.ServiceReference1.Materijal m) {
            return base.Channel.DodajMaterijalAsync(m);
        }
        
        public void Isipaj(decimal Zapremina) {
            base.Channel.Isipaj(Zapremina);
        }
        
        public System.Threading.Tasks.Task IsipajAsync(decimal Zapremina) {
            return base.Channel.IsipajAsync(Zapremina);
        }
        
        public WindowsFormsCisterna.ServiceReference1.Materijal PreuzmiMaterijal() {
            return base.Channel.PreuzmiMaterijal();
        }
        
        public System.Threading.Tasks.Task<WindowsFormsCisterna.ServiceReference1.Materijal> PreuzmiMaterijalAsync() {
            return base.Channel.PreuzmiMaterijalAsync();
        }
        
        public System.Collections.Generic.List<string> PreuzmiIzmene() {
            return base.Channel.PreuzmiIzmene();
        }
        
        public System.Threading.Tasks.Task<System.Collections.Generic.List<string>> PreuzmiIzmeneAsync() {
            return base.Channel.PreuzmiIzmeneAsync();
        }
    }
}
