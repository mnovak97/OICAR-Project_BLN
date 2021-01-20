using DAL;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Services.Controllers
{
    public class WorkCategoryController : ApiController
    {
        [Route("api/workcategory/all")]
        public List<WorkCategory> Get()
        {
            var workCategories = DAL.DAL.GetWorkCategories();
            return workCategories;
        }
    }
}
