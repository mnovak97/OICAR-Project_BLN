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

        [Route("api/offers/user/{userId}")]
        public List<Offer> GetForUserId(int userId)
        {
            try
            {
                return DAL.DAL.GetOffers().Where(x => userId == x.EmployeeIdUser).ToList();
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/offers/add")]
        public Offer Post(Offer offer)
        {
            try
            {
                offer.IsAccepted = false;
                DAL.DAL.AddOffer(offer);
                return DAL.DAL.GetOffers().LastOrDefault(x => x.EmployeeIdUser == offer.EmployeeIdUser);
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/offers/accept")]
        public List<Offer> Update(Offer offer)
        {
            try
            {
                DAL.DAL.AcceptOffer(offer);
                return Get(offer.ListingIdListing);
            }
            catch (Exception)
            {
                return null;
            }
        }
    }
}
