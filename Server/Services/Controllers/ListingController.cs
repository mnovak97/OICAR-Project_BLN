using DAL;
using Services.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data.Entity.Spatial;

namespace Services.Controllers
{
    public class ListingController : ApiController
    {
        [Route("api/listings/{id}")]
        public List<ListingModel> Get(int idUser)
        {
            try
            {
                var listings = DAL.DAL.GetListings().Where(x => x.EmployerIdUser == idUser);
                return listings.Select(x => ListingModel.FromListing(x)).ToList();
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/listings/{email}")]
        public List<ListingModel> Get(string email)
        {
            try
            {
                var listings = DAL.DAL.GetListings().Where(x => email == x.Employer.Email);
                return listings.Select(x => ListingModel.FromListing(x)).ToList();
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/listings/add")]
        public ListingModel Post(ListingModel listingModel)
        {
            try
            {
                Listing listing = listingModel.GetListing();
                listing.Location = new Location()
                {
                    IdLocation = 0,
                    Title = "",
                    Coordinates = DbGeography.FromText(string.Format("POINT({0} {1})", listingModel.Longitude, listingModel.Latitude), 4326)
                };

                DAL.DAL.AddListing(listing);
                return ListingModel.FromListing(DAL.DAL.GetListings().LastOrDefault());
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/listings/all")]
        public List<ListingModel> Get()
        {
            try
            {
                var listings = DAL.DAL.GetListings();
                return listings.Select(x => ListingModel.FromListing(x)).ToList();
            }
            catch (Exception)
            {
                return null;
            }
        }
    }
}
