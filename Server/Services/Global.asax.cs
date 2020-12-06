using DAL;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Text;
using System.Web.Http;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;
using System.Security.Cryptography;
using System.Data.Entity;

namespace Services
{
    public class WebApiApplication : System.Web.HttpApplication
    {
        public static List<User> Users;

        protected void Application_Start()
        {
            AreaRegistration.RegisterAllAreas();
            GlobalConfiguration.Configure(WebApiConfig.Register);
            FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
            RouteConfig.RegisterRoutes(RouteTable.Routes);
            BundleConfig.RegisterBundles(BundleTable.Bundles);

            // JSON ONLY
            GlobalConfiguration.Configuration.Formatters.JsonFormatter.SerializerSettings.ReferenceLoopHandling = Newtonsoft.Json.ReferenceLoopHandling.Ignore;
            GlobalConfiguration.Configuration.Formatters.Remove(GlobalConfiguration.Configuration.Formatters.XmlFormatter);

            // add test record
            /*var salt = DAL.Utils.Hmac.GenerateSalt();
            DAL.DAL.AddUser(new User()
            {
                IdUser = 0,
                FirstName = "Nameless",
                LastName = "Kirk",
                PhoneNumber = "0987654321",
                Email = "nameless@mail.com",
                PasswordSalt = salt,
                PasswordHash = DAL.Utils.Hmac.ComputeHMAC_SHA256("PaSswOrD", salt),
                Balance = 0.0
            });*/
        }
    }
}
