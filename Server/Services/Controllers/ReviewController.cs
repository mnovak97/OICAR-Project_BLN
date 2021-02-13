using DAL;
using Services.Models;
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

        [Route("api/reviews/{userId}")]
        public List<ReviewModel> Get(int userId)
        {
            try
            {
                return DAL.DAL.GetReviews()
                .Where(x => userId == x.UserReviewed.IdUser)
                .Select(x => ReviewModel.FromReview(x))
                .ToList();
            }
            catch (Exception)
            {
                return null;
            }
        }

        [Route("api/reviews/add")]
        public ReviewModel Post(ReviewModel model)
        {
            try
            {
                DAL.DAL.AddReview(model.GetReview());
                return Get(model.UserReviewedId).LastOrDefault();
            }
            catch (Exception)
            {
                return null;
            }
        }
    }
}
