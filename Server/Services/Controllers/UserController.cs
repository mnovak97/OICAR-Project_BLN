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
        public User Post(UserModel model)
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
        public User Post(LoginModel user)
        {
            try
            {
                return DAL.DAL.AuthorizeUser(user.Email, user.Password);
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/users/profile/{email}")]
        public User Get(string email)
        {
            try
            {
                return DAL.DAL.GetUsers().FirstOrDefault(x => x.Email == email);
                //return DAL.DAL.AddUser(user) + "";
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/users/profile/{id}")]
        public User Get(int id)
        {
            try
            {
                var user = DAL.DAL.GetUsers().FirstOrDefault(x => x.IdUser == id);
                return user;
            }
            catch (Exception)
            {
                return null;
            }
        }
    }
}
