using DAL;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Services.Controllers
{
    public class OfferController : ApiController
    {

        [Route("api/offers/{listingId}")]
        public List<Offer> Get(int listingId)
        {
            try
            {
                return DAL.DAL.GetOffers().Where(x => listingId == x.ListingIdListing).ToList();
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/offers/add")]
        public List<Offer> Post(Offer offer)
        {
            try
            {
                DAL.DAL.AddOffer(offer);
                return Get(offer.EmployeeIdUser);
            }
            catch (Exception)
            {
                return null;
            }
        }
    }
}
