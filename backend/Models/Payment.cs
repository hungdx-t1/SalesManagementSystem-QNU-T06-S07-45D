using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace backend.Models
{
    [Table("payments")]
    public class Payment
    {
        [Key] public int Id { get; set; }

        public int OrderId { get; set; }

        [ForeignKey("OrderId")]
        public SalesOrder Order { get; set; }

        [Required]
        public decimal Amount { get; set; }

        [StringLength(20)]
        public string PaymentMethod { get; set; } = "cash";

        public DateTime PaidAt { get; set; } = DateTime.Now;
    }
}
