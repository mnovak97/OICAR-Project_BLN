using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;
using DAL;

namespace Services.Models
{
    [DataContract]
    public class UserModel
    {

        [DataMember]
        public int IdUser { get; set; }
        [DataMember]
        public string FirstName { get; set; }
        [DataMember]
        public string LastName { get; set; }
        [DataMember]
        public string Email { get; set; }
        [DataMember]
        public string PhoneNumber { get; set; }
        [DataMember]
        public string PasswordHash { get; set; }
        [DataMember]
        public string PasswordSalt { get; set; }
        [DataMember]
        public double Balance { get; set; }
        [DataMember]
        public bool IsEmployer { get; set; }
        [DataMember]
        public string IBAN { get; set; }


        public User GetUser()
        {
            User user;

            if (IsEmployer)
            {
                user = new Employer();
            }
            else
            {
                user = new Employee();
                ((Employee)user).IBAN = IBAN;
            }

            user.IdUser = IdUser;
            user.FirstName = FirstName;
            user.LastName = LastName;
            user.Email = Email;
            user.PhoneNumber = PhoneNumber;
            user.PasswordHash = PasswordHash;
            user.PasswordSalt = PasswordSalt;
            user.Balance = Balance;

            return user;
        }
    }
}