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
            try
            {
                var workCategories = DAL.DAL.GetWorkCategories();
                return workCategories;
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/workcategory/{id}")]
        public WorkCategory Get(int id)
        {
            try
            {
                var workCategory = DAL.DAL.GetWorkCategories().FirstOrDefault(x => x.IdWorkCategory == id);
                return workCategory;
            }
            catch (Exception)
            {
                return null;
            }
        }
    }
}
