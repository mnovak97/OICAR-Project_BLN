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
                offer.IsAccepted = false;
                DAL.DAL.AddOffer(offer);
                return Get(offer.EmployeeIdUser);
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/offers/accept")]
        public Offer Update(Offer offer)
        {
            try
            {
                DAL.DAL.AcceptOffer(offer);
                return DAL.DAL.GetOffers().FirstOrDefault(x => x.IdOffer == offer.IdOffer);
            }
            catch (Exception)
            {
                return null;
            }
        }
    }
}
