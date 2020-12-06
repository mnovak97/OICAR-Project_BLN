using DAL;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Services.Controllers
{
    public class ListingController : ApiController
    {
        [Route("api/listings/{id}")]
        public Listing Get(int id)
        {
            var listings = DAL.DAL.GetListings();
            return listings.FirstOrDefault(x => x.IdListing == id);
        }

        [Route("api/listings/{email}")]
        public List<Listing> Get(string email)
        {
            var listings = DAL.DAL.GetListings().Where(x => email == x.Employer.Email);
            return listings.ToList();
        }
    }
}
