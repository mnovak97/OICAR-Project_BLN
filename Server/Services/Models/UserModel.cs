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
        [DataMember]
        public int Grade { get; set; }


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

        public static UserModel FromUser(User user)
        {
            var model = new UserModel()
            {
                IdUser = user.IdUser,
                FirstName = user.FirstName,
                LastName = user.LastName,
                Email = user.Email,
                PhoneNumber = user.PhoneNumber,
                Balance = user.Balance
            };
            var reviews = DAL.DAL.GetReviews().Where(x => x.UserReviewed.IdUser == user.IdUser);
            model.Grade = reviews.Count() > 0
                ? reviews.Sum(x => x.Grade) / reviews.Count()
                : 0;

            if (user is Employer)
            {
                model.IsEmployer = true;
            }
            else
            {
                model.IsEmployer = false;
                model.IBAN = (user as Employee).IBAN;
            }

            return model;
        }
    }
}