using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace backend.Models
{
    [Table("products")]
    public class Product
    {
        [Key] public int Id { get; set; }

        [Required, StringLength(100)]
        public string Name { get; set; }

        public int? CategoryId { get; set; }

        [ForeignKey("CategoryId")]
        public Category? Category { get; set; }

        [Required]
        public decimal Price { get; set; }

        public int Stock { get; set; } = 0;

        public DateTime CreatedAt { get; set; } = DateTime.Now;
    }
}
