using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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

        public static int AddUser(User user)
        {
            using (var db = new ModelContainer())
            {
                db.Users.Add(user);
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
                var listings = db.Listings?.ToList();
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
                var reviews = db.Reviews?.ToList();
                return reviews;
            }
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
    }
}
