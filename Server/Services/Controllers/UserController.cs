using DAL;
using Services.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Services.Controllers
{
    public class UserController : ApiController
    {

        [Route("api/users/register")]
        public UserModel Post(UserModel model)
        {
            try
            {
                User user = model.GetUser();
                DAL.DAL.AddUser(user);
                return Get(user.Email);
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/users/login")]
        public UserModel Post(LoginModel model)
        {
            try
            {
                var user = DAL.DAL.AuthorizeUser(model.Email, model.Password);
                return UserModel.FromUser(user);
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/users/update")]
        public UserModel Update(UserModel model)
        {
            try
            {
                var user = model.GetUser();
                DAL.DAL.UpdateUser(user);
                return Get(model.IdUser);
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/users/{email}")]
        public UserModel Get(string email)
        {
            try
            {
                var user = DAL.DAL.GetUsers().FirstOrDefault(x => x.Email == email);
                return UserModel.FromUser(user);
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/users/{id:int}")]
        public UserModel Get(int id)
        {
            try
            {
                var user = DAL.DAL.GetUsers().FirstOrDefault(x => x.IdUser == id);
                return UserModel.FromUser(user);
            }
            catch (Exception)
            {
                return null;
            }
        }
    }
}
