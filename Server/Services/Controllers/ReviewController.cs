using DAL;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Services.Controllers
{
    public class ReviewController : ApiController
    {

        [Route("api/reviews/{listingId}")]
        public List<Review> Get(int userId)
        {
            return DAL.DAL.GetReviews().Where(x => userId == x.UserReviewed.IdUser).ToList();
        }

        [Route("api/reviews/add")]
        public List<Review> Post(Review review)
        {
            try
            {
                DAL.DAL.AddReview(review);
                return Get(review.UserReviewed.IdUser);
            }
            catch (Exception e)
            {
                return null;
            }
        }
    }
}
