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
        public User Post(User user)
        {
            try
            {
                DAL.DAL.AddUser(user);
                return Get(user.Email);
            }
            catch (Exception e)
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
            catch (Exception e)
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
            catch (Exception e)
            {
                return null;
            }
        }

        [Route("api/users/{id}")]
        public List<User> Get(int id)
        {
            var users = DAL.DAL.GetUsers();
            return users;
        }
    }
}
