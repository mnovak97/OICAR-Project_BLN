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

            // ADD ENUM VALUES
            if (DAL.DAL.GetWorkCategories().Count == 0)
            {
                DAL.DAL.AddWorkCategory(new WorkCategory() { Title = "Household" });
                DAL.DAL.AddWorkCategory(new WorkCategory() { Title = "Gardening" });
                DAL.DAL.AddWorkCategory(new WorkCategory() { Title = "Technology" });
                DAL.DAL.AddWorkCategory(new WorkCategory() { Title = "Machinery" });
                DAL.DAL.AddWorkCategory(new WorkCategory() { Title = "Pets" });
            }

            if (DAL.DAL.GetWorkTypes().Count == 0)
            {
                DAL.DAL.AddWorkType(new WorkType() { Title = "Addition" });
                DAL.DAL.AddWorkType(new WorkType() { Title = "Replacement" });
                DAL.DAL.AddWorkType(new WorkType() { Title = "Maintenance" });
                DAL.DAL.AddWorkType(new WorkType() { Title = "Repair" });
            }

            // ADD TEST RECORD
            if (DAL.DAL.GetUsers().Count == 0)
            {
                var salt = DAL.Utils.Hmac.GenerateSalt();
                DAL.DAL.AddUser(new Employer()
                {
                    IdUser = 0,
                    FirstName = "Nameless",
                    LastName = "Kirk",
                    PhoneNumber = "0987654321",
                    Email = "nameless@mail.com",
                    PasswordSalt = salt,
                    PasswordHash = DAL.Utils.Hmac.ComputeSHA256("password", salt),
                    Balance = 0.0
                });
                salt = DAL.Utils.Hmac.GenerateSalt();
                DAL.DAL.AddUser(new Employee()
                {
                    IdUser = 0,
                    FirstName = "Test",
                    LastName = "Employee",
                    PhoneNumber = "0987654321",
                    Email = "emp@mail.hr",
                    PasswordSalt = salt,
                    PasswordHash = DAL.Utils.Hmac.ComputeSHA256("password", salt),
                    Balance = 0.0,
                    IBAN = ""
                });
            }
        }
    }
}
