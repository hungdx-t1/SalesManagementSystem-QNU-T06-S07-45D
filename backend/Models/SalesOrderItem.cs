using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace backend.Models
{
    [Table("sales_order_items")]
    public class SalesOrderItem
    {
        [Key] public int Id { get; set; }

        public int OrderId { get; set; }

        [ForeignKey("OrderId")]
        public SalesOrder Order { get; set; }

        public int ProductId { get; set; }

        [ForeignKey("ProductId")]
        public Product Product { get; set; }

        [Required]
        public int Quantity { get; set; }

        [Required]
        public decimal UnitPrice { get; set; }
    }
}
