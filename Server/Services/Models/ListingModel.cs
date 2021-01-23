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

        public Listing GetListing()
        {
            Listing listing = new Listing()
            {
                Title = Title,
                Description = Description,
                EmployerIdUser = EmployerIdUser,
                ToolsRequired = ToolsRequired,
                WorkCategoryId = WorkCategoryId,
                WorkTypeId = WorkTypeId
            };
            return listing;
        }
    }
}