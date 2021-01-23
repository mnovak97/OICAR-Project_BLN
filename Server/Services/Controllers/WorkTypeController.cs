using DAL;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Services.Controllers
{
    public class WorkTypeController : ApiController
    {
        [Route("api/worktype/all")]
        public List<WorkType> Get()
        {
            var workTypes = DAL.DAL.GetWorkTypes();
            return workTypes;
        }
    }
}
