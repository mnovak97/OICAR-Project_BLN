using DAL;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Services.Controllers
{
    public class TransactionController : ApiController
    {
        [Route("api/transactions/{id}")]
        public Transaction Get(int id)
        {
            return DAL.DAL.GetTransactions().FirstOrDefault(x => id == x.IdTransaction);
        }

        [Route("api/transactions/add")]
        public Transaction Post(Transaction transaction)
        {
            try
            {
                DAL.DAL.AddTransaction(transaction);
                return Get(transaction.IdTransaction);
            }
            catch (Exception)
            {
                return null;
            }
        }

    }
}
