using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Web;

namespace Services.Utils
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

        public static string ComputeHMAC_SHA256(string data, string salt)
        {
            using (var hmac = new HMACSHA256(Convert.FromBase64String(salt)))
            {
                return Convert.ToBase64String(hmac.ComputeHash(Encoding.UTF8.GetBytes(data)));
            }
        }
    }
}