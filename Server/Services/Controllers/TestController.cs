using DAL;
using Services.Models;
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
        [Route("api/test/users")]
        public string Post(UserModel model)
        {
            try
            {
                User user = model.GetUser();
                return DAL.DAL.AddUser(user) + "";
            }
            catch (Exception e)
            {
                return e.Message;
            }
        }

        [Route("api/test/users")]
        public List<User> Get()
        {
            var users = DAL.DAL.GetUsers();
            return users;
        }
    }
}
