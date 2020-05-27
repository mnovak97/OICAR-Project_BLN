using DAL;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Xml;

namespace Services.Controllers
{
    public class TestController : ApiController
    {
        [Route("api/Users")]
        public string Post(User user)
        {
            try
            {
                return DAL.DAL.AddUser(user) + "";
            }
            catch (Exception e)
            {
                return e.Message;
            }
        }

        [Route("api/Users")]
        public List<User> Get()
        {
            var users = DAL.DAL.GetUsers();
            return users;
        }
    }
}
