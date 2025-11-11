using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace backend.Models
{
    [Table("sales_orders")]
    public class SalesOrder
    {
        [Key] public int Id { get; set; }

        public int? CustomerId { get; set; }

        [ForeignKey("CustomerId")]
        public Customer? Customer { get; set; }

        public DateTime OrderDate { get; set; } = DateTime.Now;

        public decimal TotalAmount { get; set; } = 0;

        [StringLength(20)]
        public string Status { get; set; } = "pending";

        public ICollection<SalesOrderItem>? Items { get; set; }
    }
}
