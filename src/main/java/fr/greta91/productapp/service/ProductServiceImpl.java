package fr.greta91.productapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.greta91.productapp.entity.Product;
import fr.greta91.productapp.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository repo;
	@Override
	public List<Product> getAll() {
		return repo.findAll();
	}
	@Override
	public void remove(int idProduct) {
		repo.deleteById(idProduct);
	}
	@Override
	public Product save(Product product) {
		return repo.save(product);
	}
}
