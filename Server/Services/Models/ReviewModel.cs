using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;
using DAL;

namespace Services.Models

{
    [DataContract]
    public class ReviewModel
    {
        [DataMember]
        public int IdReview { get; set; }
        [DataMember]
        public int Grade { get; set; }
        [DataMember]
        public string Comment { get; set; }
        [DataMember]
        public int UserReviewerId { get; set; }
        [DataMember]
        public int UserReviewedId { get; set; }

        public static ReviewModel FromReview(Review review)
        {
            var model = new ReviewModel()
            {
                IdReview = review.IdReview,
                Grade = review.Grade,
                Comment = review.Comment,
                UserReviewedId = review.UserReviewed.IdUser,
                UserReviewerId = review.UserReviewer.IdUser
            };
            return model;
        }

        public Review GetReview()
        {
            var users = DAL.DAL.GetUsers();

            var review = new Review()
            {
                IdReview = IdReview,
                Grade = Grade,
                Comment = Comment,
                UserIdReviewed = UserReviewedId,
                UserIdReviewer  = UserReviewerId,
            };
            return review;
        }
    }
}