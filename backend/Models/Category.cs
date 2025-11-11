using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace backend.Models
{
    [Table("categories")]
    public class Category
    {
        [Key] public int Id { get; set; }

        [Required, StringLength(100)]
        public string Name { get; set; }
    }
}
