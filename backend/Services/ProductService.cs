using backend.Models;
using backend.Repositories;

namespace backend.Services
{
    public class ProductService : IProductService
    {
        private readonly IGenericRepository<Product> _repository;

        public ProductService(IGenericRepository<Product> repository)
        {
            _repository = repository;
        }

        public async Task<IEnumerable<Product>> GetAllAsync() => await _repository.GetAllAsync();
        public async Task<Product?> GetByIdAsync(int id) => await _repository.GetByIdAsync(id);
        public async Task AddAsync(Product product) => await _repository.AddAsync(product);
        public async Task UpdateAsync(Product product) => await _repository.UpdateAsync(product);
        public async Task DeleteAsync(int id) => await _repository.DeleteAsync(id);
    }
}
