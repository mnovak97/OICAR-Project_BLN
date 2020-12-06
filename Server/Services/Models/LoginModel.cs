using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace Services.Models
{
    [DataContract]
    public class LoginModel
    {
        [DataMember]
        public string Email { get; set; }
        [DataMember]
        public string Password { get; set; }

        public override string ToString() => Email + ":" + Password;
    }
}