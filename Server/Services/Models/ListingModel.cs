using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;
using DAL;

namespace Services.Models
{
    [DataContract]
    public class ListingModel
    {
        [DataMember]
        public int IdListing { get; set; }
        [DataMember]
        public string Title { get; set; }
        [DataMember]
        public string Description { get; set; }
        [DataMember]
        public int EmployerIdUser { get; set; }
        [DataMember]
        public bool ToolsRequired { get; set; }
        [DataMember]
        public int WorkCategoryId { get; set; }
        [DataMember]
        public int WorkTypeId { get; set; }
        [DataMember]
        public double Longitude { get; set; }
        [DataMember]
        public double Latitude { get; set; }
        [DataMember]
        public string Address { get; set; }
        [DataMember]
        public bool IsListed { get; set; }
        [DataMember]
        public bool IsEmployerReviewed { get; set; }
        [DataMember]
        public bool IsEmployeeReviewed { get; set; }

        public Listing GetListing()
        {
            Listing listing = new Listing()
            {
                Title = Title,
                Description = Description,
                EmployerIdUser = EmployerIdUser,
                ToolsRequired = ToolsRequired,
                WorkCategoryId = WorkCategoryId,
                WorkTypeId = WorkTypeId,
                IsListed = IsListed
            };
            return listing;
        }

        public static ListingModel FromListing(Listing listing)
        {
            ListingModel model = new ListingModel()
            {
                IdListing = listing.IdListing,
                Title = listing.Title,
                Description = listing.Description,
                EmployerIdUser = listing.EmployerIdUser,
                ToolsRequired = listing.ToolsRequired,
                WorkCategoryId = listing.WorkCategoryId,
                WorkTypeId = listing.WorkTypeId,
                IsListed = listing.IsListed,
                Longitude = listing.Location?.Coordinates.Longitude ?? 0,
                Latitude = listing.Location?.Coordinates.Latitude ?? 0,
                Address = listing.Location?.Title
            };
            if (listing.Offers != null && listing.Offers.Count > 0)
            {
                var acceptedOffer = listing.Offers.FirstOrDefault(x => x.IsAccepted);
                if (acceptedOffer != null)
                {
                    var employeeId = acceptedOffer.EmployeeIdUser;
                    var employerId = listing.EmployerIdUser;

                    var reviews = DAL.DAL.GetReviews();

                    model.IsEmployerReviewed = reviews.Any(x => x.UserIdReviewed == employeeId && x.UserIdReviewer == employerId);
                    model.IsEmployeeReviewed = reviews.Any(x => x.UserIdReviewed == employerId && x.UserIdReviewer == employeeId);
                }
            }
            return model;
        }
    }
}