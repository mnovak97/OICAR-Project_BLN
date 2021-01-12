using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Web;

namespace DAL.Utils
{
    public class Hmac
    {
        private const int SaltSize = 32;

        public static string GenerateSalt()
        {
            using (var rng = new RNGCryptoServiceProvider())
            {
                var randomNumber = new byte[SaltSize];
                rng.GetBytes(randomNumber);
                return Convert.ToBase64String(randomNumber);
            }
        }

        public static string ComputeSHA256(string data, string salt)
        {
            using (var sha256 = SHA256.Create())
            {
                string saltedPassword = string.Format("{0}{1}", salt, data);
                byte[] saltedPasswordAsBytes = Encoding.UTF8.GetBytes(saltedPassword);
                byte[] hash = sha256.ComputeHash(saltedPasswordAsBytes);
                return Convert.ToBase64String(hash);
            }
        }
    }
}