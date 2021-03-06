﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity;

namespace DAL
{
    public class DAL
    {
        public static List<User> GetUsers()
        {
            using (var db = new ModelContainer())
            {
                var users = db.Users?.ToList();
                return users;
            }
        }

        public static User AuthorizeUser(string email, string password)
        {
            return GetUsers()?.FirstOrDefault(x => x.Email == email && x.PasswordHash == Utils.Hmac.ComputeSHA256(password, x.PasswordSalt));
        }

        public static int AddUser(User user)
        {
            using (var db = new ModelContainer())
            {
                db.Users.Add(user);
                return db.SaveChanges();
            }
        }

        public static int UpdateUser(User user)
        {
            using (var db = new ModelContainer())
            {
                var ogUser = db.Users.Find(user.IdUser);
                if (ogUser == null) return 0;

                user.PasswordHash = ogUser.PasswordHash;
                user.PasswordSalt = ogUser.PasswordSalt;
                user.Balance = ogUser.Balance;
                user.IdUser = ogUser.IdUser;

                db.Entry(ogUser).CurrentValues.SetValues(user);

                return db.SaveChanges();
            }
        }

        public static List<WorkType> GetWorkTypes()
        {
            using (var db = new ModelContainer())
            {
                var types = db.WorkTypes?.ToList();
                return types;
            }
        }

        public static int AddWorkType(WorkType type)
        {
            using (var db = new ModelContainer())
            {
                db.WorkTypes.Add(type);
                return db.SaveChanges();
            }
        }

        public static List<WorkCategory> GetWorkCategories()
        {
            using (var db = new ModelContainer())
            {
                var categories = db.WorkCategories?.ToList();
                return categories;
            }
        }

        public static int AddWorkCategory(WorkCategory category)
        {
            using (var db = new ModelContainer())
            {
                db.WorkCategories.Add(category);
                return db.SaveChanges();
            }
        }

        public static List<Listing> GetListings()
        {
            using (var db = new ModelContainer())
            {
                var listings = db.Listings.Include(l => l.Location).Include(l => l.Employer).Include(l => l.Offers).Include(l => l.Reviews).ToList();
                return listings;
            }
        }

        public static int AddListing(Listing listing)
        {
            using (var db = new ModelContainer())
            {
                db.Listings.Add(listing);
                return db.SaveChanges();
            }
        }

        public static List<Location> GetLocations()
        {
            using (var db = new ModelContainer())
            {
                var location = db.Locations?.ToList();
                return location;
            }
        }

        public static int AddLocation(Location location)
        {
            using (var db = new ModelContainer())
            {
                db.Locations.Add(location);
                return db.SaveChanges();
            }
        }

        public static List<Review> GetReviews()
        {
            using (var db = new ModelContainer())
            {
                var reviews = db.Reviews?.Include(m => m.UserReviewer).Include(m => m.UserReviewed).ToList();
                return reviews;
            }
        }

        public static List<Review> GetReviewsForListing(int listingId)
        {
            return GetListings().FirstOrDefault(x => listingId == x.IdListing)?.Reviews.ToList();
        }

        public static int AddReview(Review review)
        {
            using (var db = new ModelContainer())
            {
                db.Reviews.Add(review);
                return db.SaveChanges();
            }
        }

        public static List<Transaction> GetTransactions()
        {
            using (var db = new ModelContainer())
            {
                var transactions = db.Transactions?.ToList();
                return transactions;
            }
        }

        public static int AddTransaction(Transaction transaction)
        {
            using (var db = new ModelContainer())
            {
                db.Transactions.Add(transaction);
                return db.SaveChanges();
            }
        }

        public static List<Offer> GetOffers()
        {
            using (var db = new ModelContainer())
            {
                var offers = db.Offers?.ToList();
                return offers;
            }
        }

        public static int AddOffer(Offer offer)
        {
            using (var db = new ModelContainer())
            {
                db.Offers.Add(offer);
                return db.SaveChanges();
            }
        }

        public static int AcceptOffer(Offer offer)
        {
            using (var db = new ModelContainer())
            {
                var listing = db.Listings.Find(offer.ListingIdListing);
                foreach (var offerIt in listing.Offers)
                {
                    if (offerIt.IdOffer == offer.IdOffer)
                    {
                        offerIt.IsAccepted = true;
                        db.Entry(offerIt).State = EntityState.Modified;
                        continue;
                    }

                    if (offerIt.IsAccepted)
                    {
                        offerIt.IsAccepted = false;
                        db.Entry(offerIt).State = EntityState.Modified;
                    }
                }

                listing.IsListed = false;
                db.Entry(listing).State = EntityState.Modified;
                return db.SaveChanges();
            }
        }
    }
}
