//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace DAL
{
    using System;
    using System.Collections.Generic;
    using System.Runtime.Serialization;

    [DataContract]
    public partial class Location
    {
        [DataMember]
        public int IdLocation { get; set; }
        [DataMember]
        public string Title { get; set; }
        [DataMember]
        public System.Data.Entity.Spatial.DbGeography Coordinates { get; set; }
    
        public virtual Listing Listing { get; set; }
    }
}
